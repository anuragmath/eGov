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
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" method="post"  modelAttribute="legalCaseInterimOrder"
	id="legalCaseInterimOrderform" cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	<c:if test="${not empty message}">
					<div role="alert">${message}</div>
				</c:if>
				<input type="hidden" id="mode" name="mode" value="${mode}" />
	<%@ include file="lcinterimorder-form.jsp"%>
	 <input type="hidden" name="lcInterimOrderDocList" value="${lcInterimOrderDocList}" />
 	<%-- <input type="hidden" id="legalCaseInterimOrder" name="legalCaseInterimOrder" value="${legalCaseInterimOrder.id}" /> --%>
	<%-- <input type="hidden"  id="lcInterimOrderId" name="lcInterimOrderId" value="${lcInterimOrder.id}" />  --%>
	<%-- <form:hidden path ="" name="legalCaseInterimOrder" id="legalCaseInterimOrder" value="${legalCaseInterimOrder.id}" class="form-control table-input hidden-input"/> --%>
	<input type="hidden" name="legalCase" value="${legalCase.id}" />
	<input type="hidden" id="lcNumber" name="lcNumber"
		value="${legalCaseInterimOrder.legalCase.lcNumber}" />  
		<input type="hidden" id="employeeName" name="employeeName"
		value="${legalCaseInterimOrder.employee.name}" />  
		<input type="hidden" name="interimOrders" value="${interimOrders}" />
		
	</div>
	</div>
	</div>
	</div>
	<jsp:include page="lcinterimorderdocuments-view.jsp"></jsp:include>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				<spring:message code='lbl.update' />
			</button>
			<button type='button' class='btn btn-default' id="btnclose"><spring:message code='lbl.close' />
			 <button type="button" class="btn btn-default" id="buttonBack" onclick="goback()"><spring:message code="lbl.back"/></button>
		</div>
	</div>
</form:form>
<script src="<cdn:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egi'/>"></script>
<link rel="stylesheet" href="<cdn:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egi'/>">
<script type="text/javascript"
	src="<cdn:url value='/resources/js/app/lcInterimOrderHelper.js?rnd=${app_release_no}'/>"></script>
	<script type="text/javascript"
	src="<cdn:url value='/resources/js/app/legalcaseSearch.js?rnd=${app_release_no}'/>"></script>
	<script
	src="<cdn:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egi'/>"></script>
	<link rel="stylesheet" href="<cdn:url value='/resources/global/css/bootstrap/typeahead.css' context='/egi'/>">