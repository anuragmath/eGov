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

package org.egov.ptis.domain.service.demolition;

import static java.lang.Boolean.FALSE;
import static org.egov.ptis.constants.PropertyTaxConstants.ADDITIONAL_COMMISSIONER_DESIGN;
import static org.egov.ptis.constants.PropertyTaxConstants.ADVANCE_DMD_RSN_CODE;
import static org.egov.ptis.constants.PropertyTaxConstants.APPCONFIG_CLIENT_SPECIFIC_DMD_BILL;
import static org.egov.ptis.constants.PropertyTaxConstants.ARR_BAL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.ASSISTANT_COMMISSIONER_DESIGN;
import static org.egov.ptis.constants.PropertyTaxConstants.COMMISSIONER_DESGN;
import static org.egov.ptis.constants.PropertyTaxConstants.CURRENTYEAR_FIRST_HALF;
import static org.egov.ptis.constants.PropertyTaxConstants.CURRENTYEAR_SECOND_HALF;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_BAL_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.CURR_DMD_STR;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_EDUCATIONAL_CESS;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_GENERAL_TAX;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_LIBRARY_CESS;
import static org.egov.ptis.constants.PropertyTaxConstants.DEMANDRSN_STR_VACANT_TAX;
import static org.egov.ptis.constants.PropertyTaxConstants.DEPUTY_COMMISSIONER_DESIGN;
import static org.egov.ptis.constants.PropertyTaxConstants.JUNIOR_ASSISTANT;
import static org.egov.ptis.constants.PropertyTaxConstants.NATURE_DEMOLITION;
import static org.egov.ptis.constants.PropertyTaxConstants.OWNERSHIP_TYPE_VAC_LAND;
import static org.egov.ptis.constants.PropertyTaxConstants.PROPERTY_MODIFY_REASON_FULL_DEMOLITION;
import static org.egov.ptis.constants.PropertyTaxConstants.PTMODULENAME;
import static org.egov.ptis.constants.PropertyTaxConstants.REVENUE_OFFICER_DESGN;
import static org.egov.ptis.constants.PropertyTaxConstants.SENIOR_ASSISTANT;
import static org.egov.ptis.constants.PropertyTaxConstants.STATUS_CANCELLED;
import static org.egov.ptis.constants.PropertyTaxConstants.UD_REVENUE_INSPECTOR_APPROVAL_PENDING;
import static org.egov.ptis.constants.PropertyTaxConstants.VACANTLAND_PROPERTY_CATEGORY;
import static org.egov.ptis.constants.PropertyTaxConstants.WFLOW_ACTION_STEP_APPROVE;
import static org.egov.ptis.constants.PropertyTaxConstants.WFLOW_ACTION_STEP_FORWARD;
import static org.egov.ptis.constants.PropertyTaxConstants.WFLOW_ACTION_STEP_REJECT;
import static org.egov.ptis.constants.PropertyTaxConstants.WFLOW_ACTION_STEP_SIGN;
import static org.egov.ptis.constants.PropertyTaxConstants.WF_STATE_ASSISTANT_APPROVAL_PENDING;
import static org.egov.ptis.constants.PropertyTaxConstants.WF_STATE_COMMISSIONER_APPROVAL_PENDING;
import static org.egov.ptis.constants.PropertyTaxConstants.WF_STATE_DIGITAL_SIGNATURE_PENDING;
import static org.egov.ptis.constants.PropertyTaxConstants.WF_STATE_REJECTED;
import static org.egov.ptis.constants.PropertyTaxConstants.ZONAL_COMMISSIONER_DESIGN;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.egov.commons.Installment;
import org.egov.commons.dao.InstallmentDao;
import org.egov.demand.model.EgDemandDetails;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.messaging.MessagingService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.DateUtils;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.service.SimpleWorkflowService;
import org.egov.infstr.services.PersistenceService;
import org.egov.pims.commons.Position;
import org.egov.ptis.client.bill.PTBillServiceImpl;
import org.egov.ptis.client.util.PropertyTaxUtil;
import org.egov.ptis.domain.dao.demand.PtDemandDao;
import org.egov.ptis.domain.dao.property.PropertyTypeMasterDAO;
import org.egov.ptis.domain.entity.demand.Ptdemand;
import org.egov.ptis.domain.entity.property.BasicProperty;
import org.egov.ptis.domain.entity.property.Property;
import org.egov.ptis.domain.entity.property.PropertyDetail;
import org.egov.ptis.domain.entity.property.PropertyImpl;
import org.egov.ptis.domain.entity.property.PropertyOwnerInfo;
import org.egov.ptis.domain.entity.property.PropertyTypeMaster;
import org.egov.ptis.domain.entity.property.VacantProperty;
import org.egov.ptis.domain.service.property.PropertyPersistenceService;
import org.egov.ptis.domain.service.property.PropertyService;
import org.egov.ptis.exceptions.TaxCalculatorExeption;
import org.egov.ptis.service.utils.PropertyTaxCommonUtils;
import org.hibernate.FlushMode;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class PropertyDemolitionService extends PersistenceService<PropertyImpl, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyDemolitionService.class);

    @Autowired
    private PropertyTypeMasterDAO propertyTypeMasterDAO;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyPersistenceService propertyPerService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    private PTBillServiceImpl ptBillServiceImpl;

    @Autowired
    @Qualifier("workflowService")
    private SimpleWorkflowService<PropertyImpl> propertyWorkflowService;

    @Autowired
    private PropertyTaxUtil propertyTaxUtil;

    @Autowired
    private PtDemandDao ptDemandDAO;

    @Autowired
    @Qualifier("parentMessageSource")
    private MessageSource ptisMessageSource;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private InstallmentDao installmentDao;

    @Autowired
    private ModuleService moduleDao;

    @Autowired
    private PropertyTaxCommonUtils propertyTaxCommonUtils;

    @Autowired
    private AssignmentService assignmentService;

    public PropertyDemolitionService() {
        super(PropertyImpl.class);
    }

    public PropertyDemolitionService(final Class<PropertyImpl> type) {
        super(type);
    }

    @Transactional
    public void saveProperty(final Property oldProperty, final Property newProperty, final Character status,
            final String comments,
            final String workFlowAction, final Long approverPosition, final String additionalRule) throws TaxCalculatorExeption {
        PropertyImpl propertyModel;
        final BasicProperty basicProperty = oldProperty.getBasicProperty();
        final PropertyTypeMaster propTypeMstr = propertyTypeMasterDAO.getPropertyTypeMasterByCode(OWNERSHIP_TYPE_VAC_LAND);
        propertyModel = (PropertyImpl) newProperty;
        newProperty.getPropertyDetail().setPropertyTypeMaster(propTypeMstr);
        newProperty.getBasicProperty().setPropOccupationDate(newProperty.getPropertyDetail().getDateOfCompletion());
        final String areaOfPlot = String.valueOf(propertyModel.getPropertyDetail().getSitalArea().getArea());
        propertyModel = propertyService.createProperty(propertyModel, areaOfPlot, PROPERTY_MODIFY_REASON_FULL_DEMOLITION,
                propertyModel.getPropertyDetail().getPropertyTypeMaster().getId().toString(), null, null, status, null,
                null, null, null, null, null, null, null);
        final Map<String, Installment> yearwiseInstMap = propertyTaxUtil.getInstallmentsForCurrYear(new Date());
        final Installment installmentFirstHalf = yearwiseInstMap.get(CURRENTYEAR_FIRST_HALF);
        final Installment installmentSecondHalf = yearwiseInstMap.get(CURRENTYEAR_SECOND_HALF);
        Date effectiveDate = null;
        if (DateUtils.between(new Date(), installmentFirstHalf.getFromDate(), installmentFirstHalf.getToDate()))
            effectiveDate = installmentFirstHalf.getFromDate();
        else
            effectiveDate = installmentSecondHalf.getFromDate();
        propertyModel.setBasicProperty(basicProperty);
        propertyModel.setEffectiveDate(effectiveDate);
        if (!propertyModel.getPropertyDetail().getPropertyTypeMaster().getCode().equals(OWNERSHIP_TYPE_VAC_LAND))
            propertyService.changePropertyDetail(propertyModel, new VacantProperty(), 0);
        propertyModel.getPropertyDetail().setCategoryType(VACANTLAND_PROPERTY_CATEGORY);
        basicProperty.setUnderWorkflow(Boolean.TRUE);
        propertyModel.setBasicProperty(basicProperty);
        basicProperty.addProperty(propertyModel);
        getSession().setFlushMode(FlushMode.MANUAL);
        transitionWorkFlow(propertyModel, comments, workFlowAction, approverPosition, additionalRule);
        final Installment currInstall = propertyTaxCommonUtils.getCurrentInstallment();

        final Property modProperty = propertyService.createDemand(propertyModel, effectiveDate);
        Ptdemand currPtDmd = null;
        for (final Ptdemand demand : modProperty.getPtDemandSet())
            if (demand.getIsHistory().equalsIgnoreCase("N"))
                if (demand.getEgInstallmentMaster().equals(currInstall)) {
                    currPtDmd = demand;
                    break;
                }
        Ptdemand oldCurrPtDmd = null;
        for (final Ptdemand ptDmd : oldProperty.getPtDemandSet())
            if (ptDmd.getIsHistory().equalsIgnoreCase("N"))
                if (ptDmd.getEgInstallmentMaster().equals(currInstall)) {
                    oldCurrPtDmd = ptDmd;
                    break;
                }

        Installment effectiveInstall = null;
        final Module module = moduleDao.getModuleByName(PTMODULENAME);
        effectiveInstall = installmentDao.getInsatllmentByModuleForGivenDate(module, effectiveDate);
        propertyService.addArrDmdDetToCurrentDmd(oldCurrPtDmd, currPtDmd, effectiveInstall, true);
        basicProperty.addProperty(modProperty);
        for (final Ptdemand ptDemand : modProperty.getPtDemandSet())
            propertyPerService.applyAuditing(ptDemand.getDmdCalculations());
        currPtDmd = adjustCollection(oldCurrPtDmd, currPtDmd, effectiveInstall);
        propertyPerService.update(basicProperty);
        getSession().flush();
    }

    private Ptdemand adjustCollection(final Ptdemand oldCurrPtDmd, final Ptdemand currPtDmd, final Installment effectiveInstall) {
        BigDecimal totalColl = BigDecimal.ZERO;

        for (final EgDemandDetails oldDmdDtls : oldCurrPtDmd.getEgDemandDetails())
            if (oldDmdDtls.getInstallmentStartDate().equals(effectiveInstall.getFromDate())
                    || oldDmdDtls.getInstallmentStartDate().after(effectiveInstall.getFromDate()))
                totalColl = totalColl.add(oldDmdDtls.getAmtCollected());
        if (totalColl.compareTo(BigDecimal.ZERO) > 0) {

            for (final EgDemandDetails dmdDtls : currPtDmd.getEgDemandDetails())
                if (dmdDtls.getInstallmentStartDate().equals(effectiveInstall.getFromDate())
                        || dmdDtls.getInstallmentStartDate().after(effectiveInstall.getFromDate()))
                    if (dmdDtls.getAmount().compareTo(totalColl) >= 0) {
                        dmdDtls.setAmtCollected(totalColl);
                        totalColl = BigDecimal.ZERO;
                    } else {
                        dmdDtls.setAmtCollected(dmdDtls.getAmount());
                        totalColl = totalColl.subtract(dmdDtls.getAmount());
                    }
            if (totalColl.compareTo(BigDecimal.ZERO) > 0) {
                EgDemandDetails newDtls;
                final Map<String, Installment> yearwiseInstMap = propertyTaxUtil.getInstallmentsForCurrYear(new Date());
                final Installment installment = yearwiseInstMap.get(CURRENTYEAR_SECOND_HALF);
                newDtls = ptBillServiceImpl.insertDemandDetails(ADVANCE_DMD_RSN_CODE, totalColl,
                        installment);
                currPtDmd.addEgDemandDetails(newDtls);
            }
        }
        return currPtDmd;
    }

    public void updateProperty(final Property newProperty, final String comments, final String workFlowAction,
            final Long approverPosition,
            final String additionalRule) {
        transitionWorkFlow((PropertyImpl) newProperty, comments, workFlowAction, approverPosition, additionalRule);
        propertyPerService.update(newProperty.getBasicProperty());
        getSession().flush();
    }

    private void transitionWorkFlow(final PropertyImpl property, final String approvarComments, final String workFlowAction,
            final Long approverPosition, final String additionalRule) {

        if (LOGGER.isDebugEnabled())
            LOGGER.debug("WorkFlow Transition For Demolition Started  ...");
        final User user = securityUtils.getCurrentUser();
        final DateTime currentDate = new DateTime();
        Position pos = null;
        Assignment wfInitiator = null;
        Assignment assignment = null;
        String approverDesignation = "";
        String nextAction = "";

        if (null != approverPosition && approverPosition != 0) {
            assignment = assignmentService.getAssignmentsForPosition(approverPosition, new Date())
                    .get(0);
            assignment.getEmployee().getName().concat("~")
                    .concat(assignment.getPosition().getName());
            approverDesignation = assignment.getDesignation().getName();
        }

        if (property.getId() != null)
            wfInitiator = propertyService.getWorkflowInitiator(property);
        else
            wfInitiator = propertyTaxCommonUtils.getWorkflowInitiatorAssignment(user.getId());

        if (WFLOW_ACTION_STEP_FORWARD.equalsIgnoreCase(workFlowAction)
                && (approverDesignation.equalsIgnoreCase(ASSISTANT_COMMISSIONER_DESIGN) ||
                        approverDesignation.equalsIgnoreCase(DEPUTY_COMMISSIONER_DESIGN)
                        || approverDesignation.equalsIgnoreCase(ADDITIONAL_COMMISSIONER_DESIGN)
                        || approverDesignation.equalsIgnoreCase(ZONAL_COMMISSIONER_DESIGN) ||
                        approverDesignation.equalsIgnoreCase(COMMISSIONER_DESGN)))
            if (property.getCurrentState().getNextAction().equalsIgnoreCase(WF_STATE_DIGITAL_SIGNATURE_PENDING))
                nextAction = WF_STATE_DIGITAL_SIGNATURE_PENDING;
            else {
                final String designation = approverDesignation.split(" ")[0];
                if (designation.equalsIgnoreCase(COMMISSIONER_DESGN))
                    nextAction = WF_STATE_COMMISSIONER_APPROVAL_PENDING;
                else
                    nextAction = new StringBuilder().append(designation).append(" ")
                            .append(WF_STATE_COMMISSIONER_APPROVAL_PENDING)
                            .toString();
            }

        String loggedInUserDesignation = "";
        if (property.getState() != null) {
            loggedInUserDesignation = getLoggedInUserDesignation(property.getCurrentState().getOwnerPosition().getId(),securityUtils.getCurrentUser());
        }

        if (loggedInUserDesignation.equals(JUNIOR_ASSISTANT) || loggedInUserDesignation.equals(SENIOR_ASSISTANT))
            loggedInUserDesignation = null;

        if (WFLOW_ACTION_STEP_REJECT.equalsIgnoreCase(workFlowAction)) {
            if (wfInitiator.getPosition().equals(property.getState().getOwnerPosition())) {
                property.transition(true).end().withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvarComments).withDateInfo(currentDate.toDate());
                property.setStatus(STATUS_CANCELLED);
                property.getBasicProperty().setUnderWorkflow(FALSE);
            } else {
                Assignment assignmentOnreject = getUserAssignmentOnReject(loggedInUserDesignation,property);
                if(assignmentOnreject != null) {
                    nextAction = UD_REVENUE_INSPECTOR_APPROVAL_PENDING;
                    wfInitiator = assignmentOnreject;
                } else
                    nextAction = WF_STATE_ASSISTANT_APPROVAL_PENDING;
                final String stateValue = property.getCurrentState().getValue().split(":")[0] + ":" + WF_STATE_REJECTED;
                property.transition(true)
                        .withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvarComments)
                        .withStateValue(stateValue)
                        .withDateInfo(currentDate.toDate())
                        .withOwner(wfInitiator.getPosition()).withNextAction(nextAction);
                buildSMS(property, workFlowAction);
            }
        } else {
            if (WFLOW_ACTION_STEP_APPROVE.equalsIgnoreCase(workFlowAction))
                pos = property.getCurrentState().getOwnerPosition();
            else if (null != approverPosition && approverPosition != -1 && !approverPosition.equals(Long.valueOf(0)))
                pos = positionMasterService.getPositionById(approverPosition);
            else if (WFLOW_ACTION_STEP_SIGN.equalsIgnoreCase(workFlowAction))
                pos = wfInitiator.getPosition();
            WorkFlowMatrix wfmatrix = null;
            if (null == property.getState()) {
                wfmatrix = propertyWorkflowService.getWfMatrix(property.getStateType(), null, null, additionalRule,
                        null, null);
                property.transition().start().withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvarComments).withStateValue(wfmatrix.getNextState())
                        .withDateInfo(new Date()).withOwner(pos).withNextAction(wfmatrix.getNextAction())
                        .withNatureOfTask(NATURE_DEMOLITION)
                        .withInitiator(wfInitiator != null ? wfInitiator.getPosition() : null);
                // to be enabled once acknowledgement feature is developed
                // buildSMS(property, workFlowAction);
            } else if (property.getCurrentState().getNextAction().equalsIgnoreCase("END"))
                property.transition().end().withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvarComments).withDateInfo(currentDate.toDate());
            else {
                wfmatrix = propertyWorkflowService.getWfMatrix(property.getStateType(), null, null, additionalRule,
                        property.getCurrentState().getValue(), property.getCurrentState().getNextAction(), null,
                        loggedInUserDesignation);

                property.transition(true).withSenderName(user.getUsername() + "::" + user.getName())
                        .withComments(approvarComments).withStateValue(wfmatrix.getNextState())
                        .withDateInfo(currentDate.toDate()).withOwner(pos)
                        .withNextAction(StringUtils.isNotBlank(nextAction) ? nextAction : wfmatrix.getNextAction());
                if (workFlowAction.equalsIgnoreCase(WFLOW_ACTION_STEP_APPROVE)) {
                    buildSMS(property, workFlowAction);
                    final String clientSpecificDmdBill = propertyTaxCommonUtils
                            .getAppConfigValue(APPCONFIG_CLIENT_SPECIFIC_DMD_BILL, PTMODULENAME);
                    if ("Y".equalsIgnoreCase(clientSpecificDmdBill))
                        propertyTaxCommonUtils
                                .makeExistingDemandBillInactive(property.getBasicProperty().getUpicNo());
                    else
                        propertyTaxUtil.makeTheEgBillAsHistory(property.getBasicProperty());
                }
            }
        }
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(" WorkFlow Transition Completed for Demolition ...");
    }

    public void validateProperty(final Property property, final BindingResult errors, final HttpServletRequest request) {
        final PropertyDetail propertyDetail = property.getPropertyDetail();
        if (StringUtils.isBlank(propertyDetail.getPattaNumber()))
            errors.rejectValue("propertyDetail.pattaNumber", "pattaNumber.required");
        if (StringUtils.isBlank(propertyDetail.getSurveyNumber()))
            errors.rejectValue("propertyDetail.surveyNumber", "surveyNumber.required");
        if (null == propertyDetail.getSitalArea().getArea())
            errors.rejectValue("propertyDetail.sitalArea.area", "vacantLandArea.required");
        if (null == propertyDetail.getMarketValue())
            errors.rejectValue("propertyDetail.marketValue", "marketValue.required");
        if (null == propertyDetail.getCurrentCapitalValue())
            errors.rejectValue("propertyDetail.currentCapitalValue", "currCapitalValue.required");
        if (StringUtils.isBlank(property.getBasicProperty().getPropertyID().getNorthBoundary()))
            errors.rejectValue("basicProperty.propertyID.northBoundary", "northBoundary.required");
        if (StringUtils.isBlank(property.getBasicProperty().getPropertyID().getEastBoundary()))
            errors.rejectValue("basicProperty.propertyID.eastBoundary", "eastBoundary.required");
        if (StringUtils.isBlank(property.getBasicProperty().getPropertyID().getWestBoundary()))
            errors.rejectValue("basicProperty.propertyID.westBoundary", "westBoundary.required");
        if (StringUtils.isBlank(property.getBasicProperty().getPropertyID().getSouthBoundary()))
            errors.rejectValue("basicProperty.propertyID.southBoundary", "southBoundary.required");
        if (StringUtils.isBlank(property.getDemolitionReason()))
            errors.rejectValue("demolitionReason", "demolitionReason.required");

    }

    public void addModelAttributes(final Model model, final BasicProperty basicProperty) {
        Property property = null;
        if (null != basicProperty.getProperty())
            property = basicProperty.getProperty();
        else
            property = basicProperty.getActiveProperty();
        final Ptdemand ptDemand = ptDemandDAO.getNonHistoryCurrDmdForProperty(property);
        if (ptDemand != null && ptDemand.getDmdCalculations() != null && ptDemand.getDmdCalculations().getAlv() != null)
            model.addAttribute("ARV", ptDemand.getDmdCalculations().getAlv());
        else
            model.addAttribute("ARV", BigDecimal.ZERO);
        if (!basicProperty.getActiveProperty().getIsExemptedFromTax())
            try {
                // Based on the current installment, fetch tax details for the respective installment
                final Map<String, Map<String, BigDecimal>> demandCollMap = propertyTaxUtil.prepareDemandDetForView(property,
                        propertyTaxCommonUtils.getCurrentInstallment());
                final Map<String, BigDecimal> currentTaxDetails = propertyService.getCurrentTaxDetails(demandCollMap, new Date());
                model.addAttribute("propertyTax", currentTaxDetails.get(DEMANDRSN_STR_GENERAL_TAX));
                model.addAttribute("eduCess", currentTaxDetails.get(DEMANDRSN_STR_EDUCATIONAL_CESS) == null ? BigDecimal.ZERO
                        : currentTaxDetails.get(DEMANDRSN_STR_EDUCATIONAL_CESS));
                model.addAttribute("libraryCess", currentTaxDetails.get(DEMANDRSN_STR_LIBRARY_CESS) == null ? BigDecimal.ZERO
                        : currentTaxDetails.get(DEMANDRSN_STR_LIBRARY_CESS));
                model.addAttribute("currTax", currentTaxDetails.get(CURR_DMD_STR));
                model.addAttribute("currTaxDue", currentTaxDetails.get(CURR_BAL_STR));
                model.addAttribute("totalTax", currentTaxDetails.get(CURR_DMD_STR));
                model.addAttribute("totalArrDue", currentTaxDetails.get(ARR_BAL_STR));
            } catch (final Exception e) {
                LOGGER.error("Exception in addModelAttributes : ", e);
                throw new ApplicationRuntimeException("Exception in addModelAttributes : " + e);
            }
    }

    public void buildSMS(final Property property, final String workFlowAction) {
        for (final PropertyOwnerInfo ownerInfo : property.getBasicProperty().getPropertyOwnerInfo())
            if (StringUtils.isNotBlank(ownerInfo.getOwner().getMobileNumber()))
                buildSms(property, ownerInfo.getOwner(), workFlowAction);
    }

    private void buildSms(final Property property, final User user, final String workFlowAction) {
        final String assessmentNo = property.getBasicProperty().getUpicNo();
        final String mobileNumber = user.getMobileNumber();
        final String applicantName = user.getName();
        String smsMsg = "";
        if (workFlowAction.equals(WFLOW_ACTION_STEP_FORWARD)) {
            // to be enabled once acknowledgement feature is developed
            /*
             * smsMsg = messageSource.getMessage("demolition.ack.sms", new String[] { applicantName, assessmentNo }, null);
             */
        } else if (workFlowAction.equals(WFLOW_ACTION_STEP_REJECT))
            smsMsg = ptisMessageSource.getMessage("demolition.rejection.sms", new String[] { applicantName, assessmentNo,
                    ApplicationThreadLocals.getMunicipalityName() }, null);
        else if (workFlowAction.equals(WFLOW_ACTION_STEP_APPROVE)) {
            Installment effectiveInstallment = null;
            final Map<String, Installment> yearwiseInstMap = propertyTaxUtil.getInstallmentsForCurrYear(new Date());
            final Installment installmentFirstHalf = yearwiseInstMap.get(CURRENTYEAR_FIRST_HALF);
            final Installment installmentSecondHalf = yearwiseInstMap.get(CURRENTYEAR_SECOND_HALF);
            Date effectiveDate = null;
            Map<String, BigDecimal> demandMap = null;
            BigDecimal totalTax = BigDecimal.ZERO;

            /*
             * If demolition is done in 1st half, then fetch the total tax amount for the 2nd half, else fetch the total tax for
             * next installment 1st half and display in the SMS.
             */
            if (DateUtils.between(new Date(), installmentFirstHalf.getFromDate(), installmentFirstHalf.getToDate()))
                effectiveInstallment = installmentSecondHalf;
            else {
                final Module module = moduleDao.getModuleByName(PTMODULENAME);
                effectiveDate = org.apache.commons.lang3.time.DateUtils.addDays(installmentSecondHalf.getToDate(), 1);
                effectiveInstallment = installmentDao.getInsatllmentByModuleForGivenDate(module, effectiveDate);
            }
            demandMap = propertyTaxUtil.getTaxDetailsForInstallment(property, effectiveInstallment, installmentFirstHalf);
            totalTax = demandMap.get(DEMANDRSN_STR_VACANT_TAX) == null ? BigDecimal.ZERO : demandMap.get(DEMANDRSN_STR_VACANT_TAX)
                    .add(demandMap.get(DEMANDRSN_STR_LIBRARY_CESS) == null ? BigDecimal.ZERO
                            : demandMap.get(DEMANDRSN_STR_LIBRARY_CESS));
            smsMsg = ptisMessageSource.getMessage("demolition.approval.sms", new String[] { applicantName, assessmentNo,
                    totalTax.toString(), new SimpleDateFormat("dd/MM/yyyy").format(effectiveInstallment.getFromDate()),
                    ApplicationThreadLocals.getMunicipalityName() },
                    null);
        }

        if (StringUtils.isNotBlank(mobileNumber))
            messagingService.sendSMS(mobileNumber, smsMsg);

    }
    
    public Assignment getUserAssignmentOnReject(final String loggedInUserDesignation,final PropertyImpl property) {
        Assignment assignmentOnreject = null;
        if (loggedInUserDesignation.equalsIgnoreCase(REVENUE_OFFICER_DESGN)
                || loggedInUserDesignation.equalsIgnoreCase(ASSISTANT_COMMISSIONER_DESIGN) ||
                loggedInUserDesignation.equalsIgnoreCase(ADDITIONAL_COMMISSIONER_DESIGN)
                || loggedInUserDesignation.equalsIgnoreCase(DEPUTY_COMMISSIONER_DESIGN) ||
                loggedInUserDesignation.equalsIgnoreCase(COMMISSIONER_DESGN) ||
                loggedInUserDesignation.equalsIgnoreCase(ZONAL_COMMISSIONER_DESIGN)) 
        assignmentOnreject = propertyService.getUserOnRejection(property);
        
        return assignmentOnreject;
     
    }
    
    public String getLoggedInUserDesignation(final Long posId,final User user) {
        List<Assignment> loggedInUserAssign = assignmentService.getAssignmentByPositionAndUserAsOnDate(
                posId, user.getId(), new Date());
        return !loggedInUserAssign.isEmpty() ? loggedInUserAssign.get(0).getDesignation().getName() : null;
    }
    
    public Assignment getWfInitiator(final PropertyImpl property) {
     return propertyService.getWorkflowInitiator(property);
    }
    
   
}
