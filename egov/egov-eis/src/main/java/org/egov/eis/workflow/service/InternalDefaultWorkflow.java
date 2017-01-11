package org.egov.eis.workflow.service;

import static org.egov.infra.workflow.entity.State.DEFAULT_STATE_VALUE_CLOSED;
import static org.egov.infra.workflow.entity.State.StateStatus.INPROGRESS;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.ACTION_CANCEL;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.STATUS_NEW;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.STATUS_REJECTED;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_ADDITIONALRULE;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_AMOUNTRULE;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_APPROVERDESIGNATIONNAME;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_APPROVERNAME;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_NEXTACTION;
import static org.egov.infra.workflow.multitenant.model.WorkflowConstants.VAR_VALIDACTIONS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.Department;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.State.StateStatus;
import org.egov.infra.workflow.entity.StateHistory;
import org.egov.infra.workflow.entity.WorkflowTypes;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.infra.workflow.matrix.service.CustomizedWorkFlowService;
import org.egov.infra.workflow.multitenant.model.Attribute;
import org.egov.infra.workflow.multitenant.model.ProcessInstance;
import org.egov.infra.workflow.multitenant.model.Task;
import org.egov.infra.workflow.multitenant.model.WorkflowBean;
import org.egov.infra.workflow.multitenant.model.WorkflowConstants;
import org.egov.infra.workflow.multitenant.model.WorkflowEntity;
import org.egov.infra.workflow.multitenant.service.WorkflowInterface;
import org.egov.infra.workflow.service.StateHistoryService;
import org.egov.infra.workflow.service.StateService;
import org.egov.infra.workflow.service.WorkflowTypeService;
import org.egov.infstr.services.EISServeable;
import org.egov.pims.commons.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InternalDefaultWorkflow implements WorkflowInterface {

 
    private static final String ID = ":ID";
    private static Logger LOG = LoggerFactory.getLogger(InternalDefaultWorkflow.class);
    @Autowired
    @Qualifier("assignmentService")
    private AssignmentService assignmentService;

    @Autowired
    private PositionMasterService positionMasterService;

    @Autowired
    private StateService stateService;

    @Autowired
    @Qualifier("eisService")
    private EISServeable eisService;

    @Autowired
    private StateHistoryService stateHistoryService;

    @Autowired
    private WorkflowTypeService workflowTypeService;

    @Autowired
    protected DepartmentService departmentService;

    @Autowired
    private CustomizedWorkFlowService workflowService;
    @Autowired
    private SecurityUtils securityUtils;

    @Transactional
    @Override
    public ProcessInstance start(String jurisdiction, ProcessInstance processInstance) {

        BigDecimal amountRule = null;
        String additionalRule = null;
        if (processInstance.getAttributes().get(VAR_AMOUNTRULE) != null) {  
            amountRule = BigDecimal.valueOf(Long.valueOf(processInstance.getAttributes().get(VAR_AMOUNTRULE).getCode()));
        }

        if (processInstance.getAttributes().get(VAR_ADDITIONALRULE) != null) {
            additionalRule = processInstance.getAttributes().get(VAR_ADDITIONALRULE).getCode();
        }

        WorkFlowMatrix wfMatrix = workflowService.getWfMatrix(processInstance.getBusinessKey(), null, amountRule, additionalRule,
                null,
                null);
        Position owner = null;
        if (processInstance.getAssignee() != null)
            owner = positionMasterService.getPositionById(Long.valueOf(processInstance.getAssignee()));
        WorkflowEntity entity = processInstance.getEntity();
        State state = new State();
        state.setType(processInstance.getType());
        state.setSenderName(securityUtils.getCurrentUser().getName());
        state.setStatus(INPROGRESS);
        state.setValue(wfMatrix.getNextState());
        state.setComments(processInstance.getDescription());
        state.setOwnerPosition(owner);
        state.setNextAction(wfMatrix.getNextAction());
        state.setType(processInstance.getBusinessKey());
        state.setInitiatorPosition(getInitiator());
        WorkflowTypes type = workflowTypeService.getWorkflowTypeByType(state.getType());
        state.setMyLinkId(type.getLink().replace(ID, entity.myLinkId()));
        state.setNatureOfTask(type.getDisplayName());
        state.setExtraInfo(entity.getStateDetails());
        stateService.create(state);
        if (state.getId() != null)
            processInstance.setId(state.getId().toString());
        return processInstance;
    }

    private Position getInitiator() {
        Position position = null;
        try {
            position = assignmentService.getPrimaryAssignmentForUser(securityUtils.getCurrentUser().getId())
                    .getPosition();
        } catch (Exception e) {
            LOG.error("Error while setting initiator position");
        }
        return position;
    }

    @Transactional
    @Override
    public ProcessInstance update(String jurisdiction, ProcessInstance pi) {
        return pi;

    }

    @Transactional
    @Override
    public Task update(String jurisdiction, Task task) {
        Position owner = null;
        if (task.getAssignee() != null)
            owner = positionMasterService.getPositionById(Long.valueOf(task.getAssignee()));
        WorkflowEntity entity = (WorkflowEntity) task.getEntity();
        String dept = null;
        BigDecimal amountRule = null;
        String additionalRule = null;
        if (task.getAttributes().get("department") != null)
            dept = task.getAttributes().get("department").getCode();

        if (task.getAttributes().get(VAR_AMOUNTRULE) != null) {
            amountRule = BigDecimal.valueOf(Long.valueOf(task.getAttributes().get(VAR_AMOUNTRULE).getCode()));
        }

        if (task.getAttributes().get(VAR_ADDITIONALRULE) != null) {
            additionalRule = task.getAttributes().get(VAR_ADDITIONALRULE).getCode();
        }

        WorkFlowMatrix wfMatrix = workflowService.getWfMatrix(task.getBusinessKey(), dept, amountRule, additionalRule,
                task.getStatus(),
                null);

        String nextState = wfMatrix.getNextState();
        State state = stateService.getStateById(Long.valueOf(task.getId()));
        if (wfMatrix.getNextAction().equalsIgnoreCase("END")) {
            state.setStatus(State.StateStatus.ENDED);
        } else {
            state.setStatus(State.StateStatus.INPROGRESS);
        }

        if (task.getAction().equalsIgnoreCase(WorkflowConstants.ACTION_REJECT)) {
            owner = state.getInitiatorPosition();
            if (owner != null)
                task.setAssignee(owner.getId().toString());
            else
                owner = assignmentService.getPrimaryAssignmentForUser(entity.getCreatedBy().getId()).getPosition();

            Attribute approverDesignationName = new Attribute();
            approverDesignationName.setCode(owner.getDeptDesig().getDesignation().getName());
            task.getAttributes().put(VAR_APPROVERDESIGNATIONNAME, approverDesignationName);

            Attribute approverName = new Attribute();
            approverName.setCode(getApproverName(owner));
            task.getAttributes().put(VAR_APPROVERNAME, approverName);
            nextState = STATUS_REJECTED;
        }
        if (task.getAction().equalsIgnoreCase(ACTION_CANCEL)) {
            state.setStatus(State.StateStatus.ENDED);
            nextState = DEFAULT_STATE_VALUE_CLOSED;
        }

        state.addStateHistory(new StateHistory(state));

        state.setValue(nextState);
        state.setComments(task.getDescription());
        state.setSenderName(securityUtils.getCurrentUser().getName());
        if (owner != null)
            state.setOwnerPosition(owner);
        state.setNextAction(wfMatrix.getNextAction());
        state.setType(task.getBusinessKey());
        state.setExtraInfo(entity.getStateDetails());
        // WorkflowTypes type =
        // workflowTypeService.getWorkflowTypeByType(state.getType());
        // state.setMyLinkId(type.getLink().replace(":ID",entity.myLinkId() ));
        // state.setNatureOfTask(type.getDisplayName());
        stateService.create(state);
        if (state.getId() != null)
            task.setId(state.getId().toString());
        stateService.update(state);

        return task;
    }

    private String getApproverName(Position owner) {
        String approverName = null;
        try {
            approverName = assignmentService.getPrimaryAssignmentForPositionAndDate(owner.getId(), new Date())
                    .getEmployee().getName();
        } catch (Exception e) {
            LOG.error("error while fetching users name");
        }
        return approverName;
    }

    private String getNextAction(final WorkflowEntity model, final WorkflowBean container) {

        WorkFlowMatrix wfMatrix = null;
        if (null != model && null != model.getId()) {
            if (null != model.getProcessInstance()) {
                wfMatrix = workflowService.getWfMatrix(model.getProcessInstance().getBusinessKey(),
                        container.getWorkflowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        container.getCurrentState(), container.getPendingActions(), model.getCreatedDate());
            } else {
                wfMatrix = workflowService.getWfMatrix(model.getProcessInstance().getBusinessKey(),
                        container.getWorkflowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        State.DEFAULT_STATE_VALUE_CREATED, container.getPendingActions(), model.getCreatedDate());
            }
        }
        return wfMatrix == null ? "" : wfMatrix.getNextAction();
    }

    /**
     * @param model
     * @param container
     * @return List of WorkFlow Buttons From Matrix By Passing parametres Type,CurrentState,CreatedDate
     */
    private List<String> getValidActions(final WorkflowEntity model, final WorkflowBean workflowBean) {
        List<String> validActions = Collections.emptyList();
        if (null == model || model.getWorkflowId() == null) {
            // validActions = Arrays.asList("Forward");
            validActions = workflowService.getNextValidActions(workflowBean.getBusinessKey(),
                    workflowBean.getWorkflowDepartment(), workflowBean.getAmountRule(),
                    workflowBean.getAdditionalRule(), STATUS_NEW, workflowBean.getPendingActions(), model.getCreatedDate());
        } else if (null != model.getProcessInstance()) {
            validActions = workflowService.getNextValidActions(workflowBean.getBusinessKey(),
                    workflowBean.getWorkflowDepartment(), workflowBean.getAmountRule(),
                    workflowBean.getAdditionalRule(), model.getProcessInstance().getStatus(),
                    workflowBean.getPendingActions(), model.getCreatedDate());

        }
        return validActions;
    }

    private List<Department> getDepartments(final WorkflowEntity model, final WorkflowBean container) {
        List<Department> departmentList = new ArrayList<Department>();
        WorkFlowMatrix wfMatrix = null;
        if (null != model && null != model.getId()) {
            if (null != model.getProcessInstance().getId()) {
                wfMatrix = workflowService.getWfMatrix(model.getProcessInstance().getBusinessKey(),
                        container.getWorkflowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        container.getCurrentState(), container.getPendingActions(), model.getCreatedDate());
            } else {
                wfMatrix = workflowService.getWfMatrix(model.getProcessInstance().getBusinessKey(),
                        container.getWorkflowDepartment(), container.getAmountRule(), container.getAdditionalRule(),
                        STATUS_NEW, container.getPendingActions(), model.getCreatedDate());
            }
        }
        if (wfMatrix.getDepartment().equalsIgnoreCase("any")) {

            departmentList = departmentService.getAllDepartments();
        } else {
            departmentList.add(departmentService.getDepartmentByName(wfMatrix.getDepartment()));
        }
        return departmentList;
    }

    @Override
    public ProcessInstance getProcess(String jurisdiction, ProcessInstance processInstance) {
        WorkflowBean wfbean = new WorkflowBean();

        State state = null;
        if (processInstance.getId() != null && !processInstance.getId().isEmpty())
            state = stateService.getStateById(Long.valueOf(processInstance.getId()));
        if (state != null) {
            processInstance.setBusinessKey(state.getType());
            if (state.getOwnerPosition() != null)
                processInstance.setAsignee(state.getOwnerPosition().getId().toString());
            else if (state.getOwnerUser() != null)
                processInstance.setAsignee(state.getOwnerUser().getId().toString());
            processInstance.setStatus(state.getValue());
        }
        processInstance.getEntity().setProcessInstance(processInstance);
        wfbean.map(processInstance);
        Attribute validActions = new Attribute();
        validActions.setValues(getValidActions(processInstance.getEntity(), wfbean));
        processInstance.getAttributes().put(VAR_VALIDACTIONS, validActions);
        Attribute nextAction = new Attribute();
        nextAction.setCode(getNextAction(processInstance.getEntity(), wfbean));
        processInstance.getAttributes().put(VAR_NEXTACTION, nextAction);
        // Attribute departments=new Attribute();
        // departments.setValues(getDepartments(processInstance.getEntity(),
        // wfbean));
        // processInstance.getAttributes().put("departmentList",departments);
        return processInstance;
    }

    private WorkflowBean copy(ProcessInstance processInstance, WorkflowBean workflowBean) {
        workflowBean.setBusinessKey(processInstance.getBusinessKey());
        workflowBean.setWorkflowAction(processInstance.getAction());
        workflowBean.setWorkflowId(processInstance.getId());
        workflowBean.setCurrentState(processInstance.getStatus());
        if (processInstance.getAssignee() != null)
            workflowBean.setApproverPositionId(Long.valueOf(processInstance.getAssignee()));
        return workflowBean;

    }

    @Override
    public List<Task> getTasks(String jurisdiction, ProcessInstance processInstance) {
        List<Task> tasks = new ArrayList<Task>();
        Task t = null;
        Long userId = securityUtils.getCurrentUser().getId();
        List<String> types = workflowTypeService.getEnabledWorkflowType(false);
        List<Long> ownerIds = this.eisService.getPositionsForUser(userId, new Date()).parallelStream()
                .map(position -> position.getId()).collect(Collectors.toList());
        List<State> states = new ArrayList<State>();
        if (!types.isEmpty())
            states = stateService.getStates(ownerIds, types, userId);
        for (State s : states) {
            tasks.add(s.map());
        }
        return tasks;
    }

    @Override
    public List<Task> getHistoryDetail(String workflowId) {
        List<Task> tasks = new ArrayList<Task>();
        Task t = null;
        State state = stateService.getStateById(Long.valueOf(workflowId));
        Set<StateHistory> history = state.getHistory();
        for (StateHistory stateHistory : history) {
            t = stateHistory.map();
            tasks.add(t);
        }
        t = state.map();
        tasks.add(t);
        return tasks;
    }

}
