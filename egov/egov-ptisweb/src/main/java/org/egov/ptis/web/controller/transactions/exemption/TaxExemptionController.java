/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.ptis.web.controller.transactions.exemption;

import static org.egov.ptis.constants.PropertyTaxConstants.APPLICATION_TYPE_TAX_EXEMTION;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_FIRSTHALF_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_FIRSTHALF_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_SECONDHALF_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_SECONDHALF_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.EXEMPTION;
import static org.egov.ptis.constants.PropertyTaxConstants.PROPERTY_VALIDATION;
import static org.egov.ptis.constants.PropertyTaxConstants.STATUS_WORKFLOW;
import static org.egov.ptis.constants.PropertyTaxConstants.TARGET_TAX_DUES;
import static org.egov.ptis.constants.PropertyTaxConstants.TARGET_WORKFLOW_ERROR;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.egov.commons.Installment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.eis.web.controller.workflow.GenericWorkFlowController;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.demand.PtDemandDao;
import org.egov.ptis.domain.dao.property.BasicPropertyDAO;
import org.egov.ptis.domain.entity.demand.Ptdemand;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.PropertyImpl;
import org.egov.ptis.domain.entity.property.TaxExemptionReason;
import org.egov.ptis.domain.service.exemption.TaxExemptionService;
import org.egov.ptis.domain.service.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author subhash
 */
@Controller
@RequestMapping(value = "/exemption")
public class TaxExemptionController extends GenericWorkFlowController {

    protected static final String TAX_EXEMPTION_FORM = "taxExemption-form";
    protected static final String TAX_EXEMPTION_SUCCESS = "taxExemption-success";
    @Autowired
    private BasicPropertyDAO basicPropertyDAO;
    @Autowired
    private PtDemandDao ptDemandDAO;
    @Autowired
    private PropertyTaxUtil propertyTaxUtil;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private TaxExemptionService taxExemptionService;
    private Boolean loggedUserIsMeesevaUser = Boolean.FALSE;
    private Boolean isExempted = Boolean.FALSE;
    private Boolean isAlert = Boolean.FALSE;
    private PropertyImpl property;
    @Autowired
    private SecurityUtils securityUtils;

    BasicProperty basicProperty;
    PropertyImpl propertyImpl = new PropertyImpl();
    PropertyImpl oldProperty;

    @ModelAttribute
    public Property propertyModel(@PathVariable final String assessmentNo) {
        basicProperty = basicPropertyDAO.getBasicPropertyByPropertyID(assessmentNo);
        if (null != basicProperty) {
            oldProperty = basicProperty.getActiveProperty();
            propertyImpl = (PropertyImpl) basicProperty.getActiveProperty().createPropertyclone();
        }
        return propertyImpl;
    }

    @SuppressWarnings("unchecked")
    @ModelAttribute("taxExemptionReasons")
    public List<TaxExemptionReason> getTaxExemptionReasons() {
        return taxExemptionService.getSession().createQuery("from TaxExemptionReason where isActive = true order by name").list();
    }

    @RequestMapping(value = "/form/{assessmentNo}", method = RequestMethod.GET)
    public String exemptionForm(final HttpServletRequest request, final Model model,
            @RequestParam(required = false) final String meesevaApplicationNumber,
            @PathVariable("assessmentNo") final String assessmentNo) {
        isExempted = oldProperty.getIsExemptedFromTax();
        isAlert= true;
        basicProperty = basicPropertyDAO.getBasicPropertyByPropertyID(assessmentNo);
         if(basicProperty!=null){
            property = (PropertyImpl) basicProperty.getProperty();
            Ptdemand ptDemand = ptDemandDAO.getNonHistoryCurrDmdForProperty(property);
            if(ptDemand==null || (ptDemand!=null && ptDemand.getEgDemandDetails()==null)) {
                model.addAttribute("errorMsg","There is no tax for this property");
                return PROPERTY_VALIDATION;
            }      
           
            else if(basicProperty.isUnderWorkflow()) {
                model.addAttribute("wfPendingMsg","Could not do Tax exemption now, property is undergoing some work flow.");
                return TARGET_WORKFLOW_ERROR;
            }
                   
        else if(!isExempted) {
            final Map<String, BigDecimal> propertyTaxDetails = propertyService
                    .getCurrentPropertyTaxDetails(basicProperty.getActiveProperty());
            BigDecimal currentPropertyTax = BigDecimal.ZERO;
            BigDecimal currentPropertyTaxDue = BigDecimal.ZERO;
            BigDecimal arrearPropertyTaxDue = BigDecimal.ZERO;
            Map<String, Installment> installmentMap = propertyTaxUtil.getInstallmentsForCurrYear(new Date());
            Installment installmentFirstHalf = installmentMap.get(PropertyTaxConstants.CURRENTYEAR_FIRST_HALF);
            if(DateUtils.between(new Date(), installmentFirstHalf.getFromDate(), installmentFirstHalf.getToDate())){
            currentPropertyTax = propertyTaxDetails.get(CURR_FIRSTHALF_DMD_STR);
            currentPropertyTaxDue = propertyTaxDetails.get(CURR_FIRSTHALF_DMD_STR).subtract(
                    propertyTaxDetails.get(CURR_FIRSTHALF_COLL_STR));
            } else {
                currentPropertyTax = propertyTaxDetails.get(CURR_SECONDHALF_DMD_STR);
                currentPropertyTaxDue = propertyTaxDetails.get(CURR_SECONDHALF_DMD_STR).subtract(
                        propertyTaxDetails.get(CURR_SECONDHALF_COLL_STR));
            }
            arrearPropertyTaxDue = propertyTaxDetails.get(ARR_DMD_STR).subtract(
                    propertyTaxDetails.get(ARR_COLL_STR));
            final BigDecimal currentWaterTaxDue = propertyService.getWaterTaxDues(basicProperty.getUpicNo(), request);
            model.addAttribute("currentPropertyTax", currentPropertyTax);
            model.addAttribute("currentPropertyTaxDue", currentPropertyTaxDue);
            model.addAttribute("arrearPropertyTaxDue", arrearPropertyTaxDue);
            model.addAttribute("currentWaterTaxDue", currentWaterTaxDue);
            if (currentWaterTaxDue.add(currentPropertyTaxDue).add(arrearPropertyTaxDue).longValue() > 0) {
                model.addAttribute("taxDuesErrorMsg", "Above tax dues must be payed before initiating "
                        + APPLICATION_TYPE_TAX_EXEMTION);
                return TARGET_TAX_DUES;
            }
            boolean hasChildPropertyUnderWorkflow = propertyTaxUtil.checkForParentUsedInBifurcation(basicProperty.getUpicNo());
            if(hasChildPropertyUnderWorkflow){
            	model.addAttribute("errorMsg", "Cannot proceed as this property is used in Bifurcation, which is under workflow");
                return PROPERTY_VALIDATION;
            }
        }
         }
        loggedUserIsMeesevaUser = propertyService.isMeesevaUser(securityUtils.getCurrentUser());
        if (loggedUserIsMeesevaUser)
            if (meesevaApplicationNumber == null)
                throw new ApplicationRuntimeException("MEESEVA.005");
            else
                propertyImpl.setMeesevaApplicationNumber(meesevaApplicationNumber);
        model.addAttribute("stateType", propertyImpl.getClass().getSimpleName());
        model.addAttribute("isExempted", isExempted);
        model.addAttribute("isAlert",isAlert);
        taxExemptionService.addModelAttributes(model, basicProperty);
        prepareWorkflow(model, propertyImpl, new WorkflowContainer());
        return TAX_EXEMPTION_FORM;
    }

    @Transactional
    @RequestMapping(value = "/form/{assessmentNo}", method = RequestMethod.POST)
    public String exemptionFormSubmit(@ModelAttribute final Property property, final BindingResult errors,
            final RedirectAttributes redirectAttrs, final Model model, final HttpServletRequest request,
            @RequestParam String workFlowAction) {
        final Character status = STATUS_WORKFLOW;
        Long approvalPosition = 0l;
        String approvalComent = "";
        String taxExemptedReason = "";
        final Boolean propertyByEmployee = Boolean.valueOf(request.getParameter("propertyByEmployee"));
        String target = "";
        loggedUserIsMeesevaUser = propertyService.isMeesevaUser(securityUtils.getCurrentUser());
        if ((!propertyByEmployee || loggedUserIsMeesevaUser)
                && null == propertyService.getUserPositionByZone(property.getBasicProperty(), false)) {
            model.addAttribute("errorMsg", "No Senior or Junior assistants exists,Please check");
            model.addAttribute("stateType", propertyImpl.getClass().getSimpleName());
            taxExemptionService.addModelAttributes(model, basicProperty);
            prepareWorkflow(model, propertyImpl, new WorkflowContainer());
            target = TAX_EXEMPTION_FORM;
        } else {

            if (request.getParameter("taxExemptedReason") != null)
                taxExemptedReason = request.getParameter("taxExemptedReason");
            if (request.getParameter("approvalComent") != null)
                approvalComent = request.getParameter("approvalComent");
            if (request.getParameter("workFlowAction") != null)
                workFlowAction = request.getParameter("workFlowAction");
            if (request.getParameter("approvalPosition") != null && !request.getParameter("approvalPosition").isEmpty())
                approvalPosition = Long.valueOf(request.getParameter("approvalPosition"));

            if (loggedUserIsMeesevaUser) {
                final HashMap<String, String> meesevaParams = new HashMap<String, String>();
                meesevaParams.put("APPLICATIONNUMBER", ((PropertyImpl) property).getMeesevaApplicationNumber());

                if (StringUtils.isBlank(property.getApplicationNo())){
                    property.setApplicationNo(((PropertyImpl) property).getMeesevaApplicationNumber());
                    property.setSource(PropertyTaxConstants.SOURCEOFDATA_MEESEWA);
                }
                taxExemptionService.saveProperty(property, oldProperty, status, approvalComent, workFlowAction,
                        approvalPosition, taxExemptedReason, propertyByEmployee, EXEMPTION, meesevaParams);
            } else
                taxExemptionService.saveProperty(property, oldProperty, status, approvalComent, workFlowAction,
                        approvalPosition, taxExemptedReason, propertyByEmployee, EXEMPTION);

            model.addAttribute(
                    "successMessage",
                    "Property exemption data saved successfully in the system and forwarded to "
                            + propertyTaxUtil.getApproverUserName(((PropertyImpl) property).getState()
                                    .getOwnerPosition().getId()) + " with application number "
                            + property.getApplicationNo());
           if (loggedUserIsMeesevaUser)
                target = "redirect:/exemption/generate-meesevareceipt/"
                        + ((PropertyImpl) property).getBasicProperty().getUpicNo() + "?transactionServiceNumber="
                        + ((PropertyImpl) property).getApplicationNo();
            else

                target = TAX_EXEMPTION_SUCCESS;
        }
        return target;
    }

    @RequestMapping(value = "/generate-meesevareceipt/{assessmentNo}", method = RequestMethod.GET)
    public RedirectView generateMeesevaReceipt(final HttpServletRequest request, final Model model) {
        final String keyNameArray = request.getParameter("transactionServiceNumber");

        final RedirectView redirect = new RedirectView(PropertyTaxConstants.MEESEVA_REDIRECT_URL + keyNameArray, false);
        final FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null)
            outputFlashMap.put("url", request.getRequestURL());
        return redirect;
    }
}
