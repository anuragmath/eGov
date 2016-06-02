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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="panel panel-primary" data-collapsed="0">
	<div class="panel-heading">
		<div class="panel-title"><spring:message code="header.yearwiseestimate" /></div>
	</div>
	<input type="hidden" value="${abstractEstimate.multiYearEstimates.size() }" id="detailsSize" />
	<div class="panel-body">
		<table class="table table-bordered" id="tblyearestimate">
			<thead>
				<tr>
					<th><spring:message code="lbl.slno"/></th>
					<th><spring:message code="lbl.year"/><span class="mandatory"></span></th>
					<th><spring:message code="lbl.percentage"/><span class="mandatory"></span></th>
					<th><spring:message code="lbl.action"/></th>
				</tr>
			</thead>
			<tbody id="multiYeaeEstimateTbl">
				<c:choose>
					<c:when test="${abstractEstimate.multiYearEstimates.size() == 0}">
						<tr id="yearEstimateRow">
						<form:hidden path="multiYearEstimates[0].id" name="multiYearEstimates[0].id" value="${multiYearEstimates[0].id}" class="form-control table-input hidden-input"/>
							<td>
								<span class="spansno">1</span>
								<form:hidden path="multiYearEstimates[0].id" name="multiYearEstimates[0].id" value="${multiYearEstimates[0].id}" class="form-control table-input hidden-input"/>
							</td>
							<td>
							<form:select path="multiYearEstimates[0].financialYear" data-first-option="false" id="multiYearEstimates[0].financialYear" class="form-control" required="required">
					            <form:option value="">
						          <spring:message code="lbl.select" />
					           </form:option>
					            <form:options items="${finYear}" itemValue="id"	itemLabel="finYearRange" />
				              </form:select>
								<form:errors path="multiYearEstimates[0].financialYear" cssClass="add-margin error-msg" />
							</td>
							<td>
								<form:input path="multiYearEstimates[0].percentage" name="multiYearEstimates[0].percentage" value="${multiYearEstimates[0].percentage}" data-errormsg="Estimated amount is mandatory!" data-pattern="decimalvalue" data-idx="0" data-optional="0" class="form-control table-input text-right estimateAmount" onkeyup="calculateEstimatedAmountTotal();" onblur="calculateEstimatedAmountTotal();" required="required"/>
								<form:errors path="multiYearEstimates[0].percentage" cssClass="add-margin error-msg" />
							</td>
							<!-- <td> <span class="add-padding" onclick="deleteMultiYearEstimate(this);"><i class="fa fa-trash" data-toggle="tooltip" title="" data-original-title="Delete!"></i></span> </td> -->
							<td><button type="button" class="btn btn-xs btn-secondary delete-row" onclick="deleteMultiYearEstimate(this);"><span class="glyphicon glyphicon-trash"></span> Delete</button></td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${abstractEstimate.getMultiYearEstimates()}" var="yearEstimateDtls" varStatus="item">
							<tr id="yearEstimateRow">
							<form:hidden path="multiYearEstimates[${item.index}].id" name="multiYearEstimates[${item.index}].id" value="${multiYearEstimates.id}" class="form-control table-input hidden-input"/>
								<td> 
									<span class="spansno"><c:out value="${item.index + 1}" /></span>
									<form:hidden path="multiYearEstimates[${item.index}].id" name="multiYearEstimates[${item.index}].id" value="${multiYearEstimates.id}" class="form-control table-input hidden-input"/>
								</td>
								<td>
								<form:select path="multiYearEstimates[${item.index}].financialYear" data-first-option="false" id="multiYearEstimates[${item.index}].financialYear" class="form-control" required="required">
					            <form:option value="">
						          <spring:message code="lbl.select" />
					           </form:option>
					            <form:options items="${finYear}" itemValue="id"	itemLabel="finYearRange" />
				              </form:select>
									<form:errors path="multiYearEstimates[${item.index}].financialYear.finYearRange" cssClass="add-margin error-msg" />
								</td>
								<td>
									<form:input path="multiYearEstimates[${item.index}].percentage" name="multiYearEstimates[${item.index}].percentage" value="${multiYearEstimates.percentage}"  data-pattern="decimalvalue" data-idx="0" data-optional="0" class="form-control table-input text-right estimateAmount" onkeyup="calculateEstimatedAmountTotal();" required="required"/>
									<form:errors path="multiYearEstimates[${item.index}].percentage" cssClass="add-margin error-msg" />
								</td>
								<td> <span class="add-padding" onclick="deleteMultiYearEstimate(this);"><i class="fa fa-trash" data-toggle="tooltip" title="" data-original-title="Delete!"></i></span> </td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
			<tfoot>
				<c:set var="total" value="${0}" scope="session"/> 
				<c:if test="${abstractEstimate.getMultiYearEstimates() != null}">
					<c:forEach items="${abstractEstimate.getMultiYearEstimates()}" var="lineEstimateDtls">
						<c:set var="total" value="${total + multiYearEstimates.percentage}"/>
					</c:forEach>
				</c:if>
				<tr>
					<td colspan="2" class="text-right"><spring:message code="lbl.total" /></td>
					<td class="text-right"> <span id="estimateTotal"><c:out value="${total}"/></span> </td>
					<td></td>
				</tr>
			</tfoot>
		</table>
		<div class="col-sm-12 text-center">
			<button id="addRowBtn" type="button" class="btn btn-primary" onclick="addMultiyearEstimate()"><spring:message code="lbl.addrow" /></button>
		</div>
	</div>
</div>