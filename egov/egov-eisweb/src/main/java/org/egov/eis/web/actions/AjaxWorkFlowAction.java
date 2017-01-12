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
package org.egov.eis.web.actions;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.egov.eis.service.DesignationService;
import org.egov.eis.workflow.service.InternalDefaultWorkflow;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.web.struts.actions.BaseFormAction;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.matrix.service.CustomizedWorkFlowService;
import org.egov.infra.workflow.multitenant.model.Attribute;
import org.egov.infra.workflow.multitenant.model.Task;
import org.egov.infra.workflow.multitenant.service.WorkflowInterface;
import org.egov.pims.commons.Designation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author subhash
 *
 */
@ParentPackage("egov")
@ResultPath("/WEB-INF/jsp/")
@Results({
        @Result(name = "designations", location = "/WEB-INF/jsp/workflow/ajaxWorkFlow-designations.jsp"),
        @Result(name = "approvers", location = "/WEB-INF/jsp/workflow/ajaxWorkFlow-approvers.jsp") })
public class AjaxWorkFlowAction extends BaseFormAction {

    private static final long serialVersionUID = -4816498948951535977L;

    private static final String WF_APPROVERS = "approvers";
    private static final String WF_DESIGNATIONS = "designations";
    private List<Designation> designationList;
    protected InternalDefaultWorkflow internalDefaultWorkflow = new InternalDefaultWorkflow();
    private List<Object> approverList;
    private Long designationId;
    private Long approverDepartmentId;
    private CustomizedWorkFlowService customizedWorkFlowService;

    @Autowired
    private ApplicationContext applicationContext;
    private String type;
    private BigDecimal amountRule;
    private String additionalRule;
    private String currentState;
    private String pendingAction;
    private String departmentRule;
    private List<String> roleList;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    DesignationService designationService;

    @Action(value = "/workflow/ajaxWorkFlow-getPositionByPassingDesigId")
    public String getPositionByPassingDesigId() {
        final WorkflowInterface workflowImplementation = getWorkflowImplementation();
        final Department dept = departmentService.getDepartmentById(approverDepartmentId);
        final String approverDeptCode = dept.getCode();
        final Designation desig = designationService.getDesignationById(designationId);
        final String desigName = desig.getName();
        approverList = workflowImplementation.getAssignee(approverDeptCode, desigName);
        return WF_APPROVERS;
    }

    @Action(value = "/workflow/ajaxWorkFlow-getDesignationsByObjectType")
    public String getDesignationsByObjectType() {
        final WorkflowInterface workflowImplementation = getWorkflowImplementation();

        final Task task = new Task();
        task.setBusinessKey(type);
        task.setStatus(currentState);
        final Attribute amount = new Attribute();
        amount.setCode(amountRule == null ? null : amountRule.toString());
        task.getAttributes().put("amountRule", amount);
        final Attribute additional = new Attribute();
        additional.setCode(additionalRule);
        task.getAttributes().put("additionalRule", additional);
        task.setAction(pendingAction);
        final Department dept = departmentService.getDepartmentByName(departmentRule);
        designationList = workflowImplementation.getDesignations(task, dept.getCode());
        return WF_DESIGNATIONS;
    }

    /**
     * For Struts 1.x This method is called to get valid actions(Approve,Reject) and nextaction(END)
     *
     * @throws IOException
     */

    public void getAjaxValidButtonsAndNextAction() throws IOException {
        final StringBuffer actionString = new StringBuffer("");
        final WorkFlowMatrix matrix = getWfMatrix();
        if (currentState == null || "".equals(currentState)) {

            if (matrix != null && "END".equals(matrix.getNextAction()))
                actionString.append("Save,Approve");
            else
                actionString.append("Save,Forward");
            actionString.append('@');
            if (matrix != null)
                actionString.append(matrix.getNextAction());
            else
                actionString.append(' ');
        } else if (matrix != null) {
            actionString.append(matrix.getValidActions());
            actionString.append('@');
            actionString.append(matrix.getNextAction());
        }
        ServletActionContext.getResponse().getWriter().write(actionString.toString());
    }

    private WorkFlowMatrix getWfMatrix() {
        return customizedWorkFlowService.getWfMatrix(type, departmentRule,
                amountRule, additionalRule, currentState, pendingAction);
    }

    public List<Designation> getDesignationList() {
        return designationList;
    }

    public List<? extends Object> getApproverList() {
        return approverList;
    }

    public void setDesignationId(final Long designationId) {
        this.designationId = designationId;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setAmountRule(final BigDecimal amountRule) {
        this.amountRule = amountRule;
    }

    public void setAdditionalRule(final String additionalRule) {
        this.additionalRule = additionalRule;
    }

    public void setCurrentState(final String currentState) {
        this.currentState = currentState;
    }

    public void setApproverDepartmentId(final Long approverDepartmentId) {
        this.approverDepartmentId = approverDepartmentId;
    }

    public void setApproverDepartmentCode(final String approverDepartmentCode) {
    }

    public void setDepartmentRule(final String departmentRule) {
        this.departmentRule = departmentRule;
    }

    @Override
    public Object getModel() {
        return null;
    }

    public void setCustomizedWorkFlowService(
            final CustomizedWorkFlowService customizedWorkFlowService) {
        this.customizedWorkFlowService = customizedWorkFlowService;
    }

    public void setPendingAction(final String pendingAction) {
        this.pendingAction = pendingAction;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(final List<String> roleList) {
        this.roleList = roleList;
    }

    public WorkflowInterface getWorkflowImplementation() {
        // this will decide by p.getBusinessKey and p.getId

        return (WorkflowInterface) applicationContext.getBean("internalDefaultWorkflow");
    }
}
