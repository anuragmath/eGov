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
package org.egov.ptis.web.controller.vacancyremission;

import static org.egov.ptis.constants.PropertyTaxConstants.ARR_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_FIRSTHALF_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_FIRSTHALF_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_SECONDHALF_COLL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_SECONDHALF_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.OWNERSHIP_TYPE_VAC_LAND;
import static org.egov.ptis.constants.PropertyTaxConstants.PROPERTY_VALIDATION;
import static org.egov.ptis.constants.PropertyTaxConstants.TARGET_TAX_DUES;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.egov.commons.Installment;
import org.egov.eis.web.contract.WorkflowContainer;
import org.egov.eis.web.controller.workflow.GenericWorkFlowController;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.utils.DateUtils;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.constants.PropertyTaxConstants;
import org.egov.ptis.domain.dao.property.BasicPropertyDAO;
import org.egov.ptis.domain.entity.enums.TransactionType;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.BasicPropertyImpl;
import org.egov.ptis.domain.entity.property.Document;
import org.egov.ptis.domain.entity.property.DocumentType;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.VacancyRemission;
import org.egov.ptis.domain.service.property.PropertyService;
import org.egov.ptis.domain.service.property.VacancyRemissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RequestMapping(value = "/vacancyremission")
public class VacanyRemissionController extends GenericWorkFlowController {

    private static final String VACANCYREMISSION_FORM = "vacancyRemission-form";
    private static final String VACANCYREMISSION_SUCCESS = "vacancyRemission-success";

    @Autowired
    private BasicPropertyDAO basicPropertyDAO;

    private final PropertyTaxUtil propertyTaxUtil;

    private BasicProperty basicProperty;

    private VacancyRemission vacancyRemission;

    private final VacancyRemissionService vacancyRemissionService;
    private Boolean loggedUserIsMeesevaUser = Boolean.FALSE;
   
    @Autowired
    private PropertyService propertyService;

    @Autowired
    public VacanyRemissionController(final VacancyRemissionService vacancyRemissionService,
            final PropertyTaxUtil propertyTaxUtil) {
        this.propertyTaxUtil = propertyTaxUtil;
        this.vacancyRemissionService = vacancyRemissionService;
    }

    @ModelAttribute
    public VacancyRemission vacancyRemissionModel(@PathVariable final String assessmentNo) {
        vacancyRemission = new VacancyRemission();
        basicProperty = basicPropertyDAO.getBasicPropertyByPropertyID(assessmentNo);
        if (basicProperty != null)
            vacancyRemission.setBasicProperty((BasicPropertyImpl) basicProperty);
        return vacancyRemission;
    }

    @ModelAttribute("documentsList")
    public List<DocumentType> documentsList(@ModelAttribute final VacancyRemission vacancyRemission) {
        return vacancyRemissionService.getDocuments(TransactionType.VACANCYREMISSION);
    }
    @RequestMapping(value = "/create/{assessmentNo},{mode}", method = RequestMethod.GET)
    public String newForm(final Model model, @PathVariable final String assessmentNo, @PathVariable final String mode,
            @RequestParam(required = false) final String meesevaApplicationNumber, final HttpServletRequest request) {
        if (basicProperty != null) {
            final Property property = basicProperty.getActiveProperty();
            List<DocumentType> documentTypes;
            documentTypes = propertyService.getDocumentTypesForTransactionType(TransactionType.VACANCYREMISSION);
            if (property != null)
                // When called from common search
                if ("commonSearch".equalsIgnoreCase(mode)) {
                    Boolean enableVacancyRemission = Boolean.FALSE;
                    if (property.getPropertyDetail().getPropertyTypeMaster().getCode()
                            .equalsIgnoreCase(OWNERSHIP_TYPE_VAC_LAND)) {
                        model.addAttribute("errorMsg", "Vacancy Remission cannot be done for Vacant Land ");
                        return PROPERTY_VALIDATION;
                    } else if (property.getIsExemptedFromTax()) {
                        model.addAttribute("errorMsg", "This property is exempted from taxes");
                        return PROPERTY_VALIDATION;
                    } else if (basicProperty.isUnderWorkflow()) {
                        model.addAttribute("errorMsg",
                                "Could not do Vacancy Remission now, as this property is undergoing some work flow.");
                        return PROPERTY_VALIDATION;
                    } else {
                        final List<VacancyRemission> remissionList = vacancyRemissionService
                                .getAllVacancyRemissionByUpicNo(basicProperty.getUpicNo());
                        if (!remissionList.isEmpty()) {
                            final VacancyRemission vacancyRemission = remissionList.get(remissionList.size() - 1);
                            if (vacancyRemission != null)
                                if (vacancyRemission.getStatus().equalsIgnoreCase(
                                        PropertyTaxConstants.VR_STATUS_APPROVED)) {
                                    if (org.apache.commons.lang.time.DateUtils.isSameDay(
                                            vacancyRemission.getVacancyToDate(), new Date()))
                                        enableVacancyRemission = true;
                                    else if (vacancyRemission.getVacancyToDate().compareTo(new Date()) < 0)
                                        enableVacancyRemission = true;
                                } else if (vacancyRemission.getStatus().equalsIgnoreCase(
                                        PropertyTaxConstants.VR_STATUS_REJECTION_ACK_GENERATED))
                                    enableVacancyRemission = true;
                                else if (vacancyRemission.getStatus().equalsIgnoreCase(
                                        PropertyTaxConstants.VR_STATUS_WORKFLOW)) {
                                    model.addAttribute("errorMsg", "This property is under workflow");
                                    return PROPERTY_VALIDATION;
                                }
                        }
                        if (remissionList.isEmpty() || enableVacancyRemission) {
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
                            final BigDecimal currentWaterTaxDue = propertyService.getWaterTaxDues(
                                    basicProperty.getUpicNo(), request);
                            model.addAttribute("currentPropertyTax", currentPropertyTax);
                            model.addAttribute("currentPropertyTaxDue", currentPropertyTaxDue);
                            model.addAttribute("arrearPropertyTaxDue", arrearPropertyTaxDue);
                            model.addAttribute("currentWaterTaxDue", currentWaterTaxDue);
                            if (currentWaterTaxDue.add(currentPropertyTaxDue).add(arrearPropertyTaxDue).longValue() > 0) {
                                model.addAttribute("taxDuesErrorMsg",
                                        "Please clear property tax due for availing vacancy remission for your property ");
                                return TARGET_TAX_DUES;
                            }

                            prepareWorkflow(model, vacancyRemission, new WorkflowContainer());
                            model.addAttribute("stateType", vacancyRemission.getClass().getSimpleName());
                            model.addAttribute("documentTypes", documentTypes);
                            vacancyRemissionService.addModelAttributes(model, basicProperty);
                        }
                        loggedUserIsMeesevaUser = propertyService.isMeesevaUser(vacancyRemissionService
                                .getLoggedInUser());
                        if (loggedUserIsMeesevaUser) {
                            if (meesevaApplicationNumber == null)
                                throw new ApplicationRuntimeException("MEESEVA.005");
                            else
                                vacancyRemission.setMeesevaApplicationNumber(meesevaApplicationNumber);
                        }
                    }
                } else {
                	boolean hasChildPropertyUnderWorkflow = propertyTaxUtil.checkForParentUsedInBifurcation(basicProperty.getUpicNo());
                    if(hasChildPropertyUnderWorkflow){
                    	model.addAttribute("errorMsg", "Cannot proceed as this property is used in Bifurcation, which is under workflow");
                        return PROPERTY_VALIDATION;
                    }
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
                    final BigDecimal currentWaterTaxDue = propertyService.getWaterTaxDues(basicProperty.getUpicNo(),
                            request);
                    model.addAttribute("currentPropertyTax", currentPropertyTax);
                    model.addAttribute("currentPropertyTaxDue", currentPropertyTaxDue);
                    model.addAttribute("arrearPropertyTaxDue", arrearPropertyTaxDue);
                    model.addAttribute("currentWaterTaxDue", currentWaterTaxDue);
                    if (currentWaterTaxDue.add(currentPropertyTaxDue).add(arrearPropertyTaxDue).longValue() > 0) {
                        model.addAttribute("taxDuesErrorMsg",
                                "Please clear property tax due for availing vacancy remission for your property ");
                        return TARGET_TAX_DUES;
                    }
                    model.addAttribute("documentTypes", documentTypes);
                    prepareWorkflow(model, vacancyRemission, new WorkflowContainer());
                    model.addAttribute("stateType", vacancyRemission.getClass().getSimpleName());
                    vacancyRemissionService.addModelAttributes(model, basicProperty);
                }
        }
        return VACANCYREMISSION_FORM;
    }

    @RequestMapping(value = "/create/{assessmentNo},{mode}", method = RequestMethod.POST)
    public String saveVacancyRemission(@Valid @ModelAttribute final VacancyRemission vacancyRemission,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes, final Model model,
            final HttpServletRequest request, @RequestParam String workFlowAction) {

        final Boolean propertyByEmployee = Boolean.valueOf(request.getParameter("propertyByEmployee"));
        List<Document> documents = new ArrayList<>();
        loggedUserIsMeesevaUser = propertyService.isMeesevaUser(vacancyRemissionService.getLoggedInUser());
        validateDates(vacancyRemission, resultBinder, request);
        if (resultBinder.hasErrors()) {
            if (basicProperty != null) {
                prepareWorkflow(model, vacancyRemission, new WorkflowContainer());
                model.addAttribute("stateType", vacancyRemission.getClass().getSimpleName());
                vacancyRemissionService.addModelAttributes(model, basicProperty);
            }
            return VACANCYREMISSION_FORM;
        } else if ((!propertyByEmployee || loggedUserIsMeesevaUser) && null != basicProperty
                && null == propertyService.getUserPositionByZone(basicProperty, false)) {
            prepareWorkflow(model, vacancyRemission, new WorkflowContainer());
            model.addAttribute("stateType", vacancyRemission.getClass().getSimpleName());
            model.addAttribute("errorMsg", "No Senior or Junior assistants exists,Please check");
            vacancyRemissionService.addModelAttributes(model, basicProperty);
            return VACANCYREMISSION_FORM;
        } else {
            Long approvalPosition = 0l;
            String approvalComent = "";

            if (request.getParameter("approvalComent") != null)
                approvalComent = request.getParameter("approvalComent");
            if (request.getParameter("workFlowAction") != null)
                workFlowAction = request.getParameter("workFlowAction");
            if (request.getParameter("approvalPosition") != null && !request.getParameter("approvalPosition").isEmpty())
                approvalPosition = Long.valueOf(request.getParameter("approvalPosition"));
            
            if(!vacancyRemission.getDocuments().isEmpty()){
                documents.addAll(vacancyRemission.getDocuments());
                vacancyRemission.getDocuments().clear();
                vacancyRemission.getDocuments().addAll(documents);
                processAndStoreApplicationDocuments(vacancyRemission);
            }

            if (loggedUserIsMeesevaUser) {
                final HashMap<String, String> meesevaParams = new HashMap<String, String>();
                meesevaParams.put("APPLICATIONNUMBER", vacancyRemission.getMeesevaApplicationNumber());

                if (StringUtils.isBlank(vacancyRemission.getApplicationNumber())){
                    vacancyRemission.setApplicationNumber(vacancyRemission.getMeesevaApplicationNumber());
                    vacancyRemission.setSource(PropertyTaxConstants.SOURCEOFDATA_MEESEWA);
                }
                vacancyRemissionService.saveVacancyRemission(vacancyRemission, approvalPosition, approvalComent, "",
                        workFlowAction, propertyByEmployee, meesevaParams);
            } else
                vacancyRemissionService.saveVacancyRemission(vacancyRemission, approvalPosition, approvalComent, null,
                        workFlowAction, propertyByEmployee);

            final String successMsg = "Vacancy Remission Saved Successfully in the System and forwarded to : "
                    + propertyTaxUtil.getApproverUserName(vacancyRemission.getState().getOwnerPosition().getId())
                    + " with application number : " + vacancyRemission.getApplicationNumber();
            model.addAttribute("successMessage", successMsg);
        }

        if (loggedUserIsMeesevaUser)
            return "redirect:/vacancyremission/generate-meesevareceipt/"
                    + vacancyRemission.getBasicProperty().getUpicNo() + "?transactionServiceNumber="
                    + vacancyRemission.getApplicationNumber();
        else
            return VACANCYREMISSION_SUCCESS;
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

    private void validateDates(final VacancyRemission vacancyRemission, final BindingResult errors,
            final HttpServletRequest request) {

        final int noOfMonths = DateUtils.noOfMonths(vacancyRemission.getVacancyFromDate(),
                vacancyRemission.getVacancyToDate());
        if (noOfMonths < 6)
            errors.rejectValue("vacancyToDate", "vacancyToDate.incorrect");
    }
    
    protected void processAndStoreApplicationDocuments(final VacancyRemission vacancyRemission) {
        if (!vacancyRemission.getDocuments().isEmpty())
            for (final Document applicationDocument : vacancyRemission.getDocuments()) {
                if(applicationDocument.getFile() != null) {
                    applicationDocument.setType(vacancyRemissionService.getDocType(applicationDocument.getType().getName()));
                    applicationDocument.setFiles(propertyService.addToFileStore(applicationDocument.getFile()));
                }
            }
    }

}
