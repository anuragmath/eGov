<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
      .position_alert{
        position:fixed;z-index:9999;top:85px;right:20px;background:#F2DEDE;padding:10px 20px;border-radius: 5px;
      }
      .position_alert1{
        position:absolute;top:15px;right:20px;background:#F2DEDE;padding:10px 20px;border-radius: 5px;width:215px;
      }
      .position_alert2{
        position:absolute;top:15px;right:240px;background:#F2DEDE;padding:10px 20px;border-radius: 5px;
      }
      .position_alert3{
        background:#F2DEDE;padding:10px 20px;border-radius: 5px;margin-right: 10px;color:#333;font-size:14px;position: absolute; top: 11px;right: 180px;
      }
    </style>
<form:form modelAttribute="mbHeader" name="mbHeader" role="form" action="" method="post" id="mbHeader"
	class="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	
	<input name="mbHeader" type="hidden" id="id" value="${mbHeader.id }" />
	<input name="workCommencedDate" type="hidden" id="workCommencedDate" value="${workCommencedDate }" />
	<input name="totalMBAmountOfMBs" type="hidden" id="totalMBAmountOfMBs" value="${totalMBAmountOfMBs }" />
	<input name="workOrderAmount" type="hidden" id="workOrderAmount" value="${mbHeader.workOrderEstimate.workOrder.workOrderAmount }" />
	<input name="quantityTolerance" type="hidden" id="quantityTolerance" value="${quantityTolerance }" />
	<form:input path="workOrder.id" type="hidden" id="workOrderId" value="${mbHeader.workOrderEstimate.workOrder.id }" />
	<form:input path="workOrderEstimate.id" type="hidden" id="workOrderEstimateId" value="${mbHeader.workOrderEstimate.id }" />
	<input type="hidden" name="removedDetailIds" id="removedDetailIds" value="${removedDetailIds }" class="form-control table-input hidden-input"/>
	<input type="hidden" id="errorsornonsor" value="<spring:message code='error.mb.sor.nonsor.required' />">
	<input type="hidden" id="errortotalmbamount" value="<spring:message code='error.sum.mb.workorder.amount' />">
	<input type="hidden" id="errortoleranceexceeded" value="<spring:message code='error.tolerance.exceeded' />">
	
	<div class="new-page-header"><spring:message code="lbl.createmb" /></div> 
	
	<div class="panel-title text-center" style="color: green;" id="successMessage">
		<c:out value="${message}" /><br />
	</div>
	
	<div class="alert text-left" style="color: red;" id="errorMessage">
	</div>
	
	<div>
	       <spring:hasBindErrors name="mbHeader">
			    <div class="col-md-10 col-md-offset-1">
					<form:errors path="*" cssClass="error-msg add-margin" /><br/>
			   </div>
          </spring:hasBindErrors>
	</div>

	<div class="main-content">
		<div class="position_alert1">
			<spring:message code="lbl.mb.amount" /><span class="mandatory"></span> : &#8377 <span id="mbAmountSpan">${mbHeader.mbAmount }</span>
			<form:hidden path="mbAmount" value="${mbHeader.mbAmount }" id="mbAmount" class="form-control"/>
		</div>
		<c:choose>
			<c:when test="${mbHeader.workOrderEstimate.workOrder.tenderFinalizedPercentage >= 0 }">
				<div class="position_alert2"><spring:message code="lbl.mb.finalised.tender" /> (&#37;) : <span id="tenderFinalisedPercentage"> +${mbHeader.workOrderEstimate.workOrder.tenderFinalizedPercentage }</span></div>
			</c:when>
			<c:otherwise>
				<div class="position_alert2"><spring:message code="lbl.mb.finalised.tender" /> (&#37;) : <span id="tenderFinalisedPercentage"> ${mbHeader.workOrderEstimate.workOrder.tenderFinalizedPercentage }</span></div>
			</c:otherwise>
		</c:choose>
		<div class="panel-heading">
			<ul class="nav nav-tabs" id="settingstab">
				<li class="active"><a data-toggle="tab" href="#mbheader"
					data-tabidx=0><spring:message code="lbl.mbheader" /></a></li>
				<li><a data-toggle="tab" href="#tenderedItems" data-tabidx=1><spring:message
							code="lbl.tendered.items" /> </a></li>
				<%-- <li><a data-toggle="tab" href="#nonTenderedItems" data-tabidx=1><spring:message
							code="lbl.nontendered.items" /> </a></li> --%>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane fade in active" id="mbheader">
				<%@ include file="mb-header.jsp"%>
			</div>
			<div class="tab-pane fade" id="tenderedItems">
				<%@ include file="mb-sor.jsp"%>
				<%@ include file="mb-nonsor.jsp"%>
			</div>
			<%-- <div class="tab-pane fade" id="nonTenderedItems">
				<%@ include file="mb-nonTenderedItems.jsp"%>
			</div> --%>
			<c:if test="${!workflowHistory.isEmpty() && mode != null }">
				<div class="panel panel-primary" data-collapsed="0">
					<div class="panel-heading">
						<div class="panel-title">
							<spring:message code="lbl.apphistory"/>
						</div>
					</div>
					<jsp:include page="../common/commonWorkflowhistory-view.jsp"></jsp:include>
				</div>
			</c:if>
			<jsp:include page="../common/commonWorkflowMatrix.jsp"/>
			<div class="buttonbottom" align="center">
				<jsp:include page="../common/commonWorkflowMatrix-button.jsp" />
			</div>
		</div>
	</div>
</form:form>

<script type="text/javascript"
	src="<c:url value='/resources/js/mb/measurementbook.js?rnd=${app_release_no}'/>"></script>
	<script src="<c:url value='/resources/global/js/egov/inbox.js' context='/egi'/>"></script>
<c:if test="${mode == 'edit' || mode == 'workflowView' }">
	<script type="text/javascript"
	src="<c:url value='/resources/js/mb/mbformsubmit.js?rnd=${app_release_no}'/>"></script>
</c:if>