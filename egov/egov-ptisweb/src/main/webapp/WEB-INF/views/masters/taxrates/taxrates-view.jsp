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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="row" id="page-content">
	<div class="col-md-12">
		<c:if test="${not empty message}">
			<div class="alert alert-success" role="alert">
				<spring:message code="${message}" />
			</div>
		</c:if>
		<div class="row">
			<div class="col-md-12">
				<div class="panel-group">
					<div class="panel panel-primary">
						<div class="panel-heading slide-history-menu">
							<div class="panel-title">
								<strong><spring:message code="lbl.hdr.taxRates" /></strong>
							</div>

						</div>
						<div class="panel-body history-slide">
							<%-- <div
								class="row hidden-xs visible-sm visible-md visible-lg view-content header-color">
								<div class="col-sm-2 col-xs-6 add-margin">
									<spring:message code="lbl.taxRates.value" />
								</div>
							</div> --%>
							<div class="form-group col-sm-6 col-sm-offset-3">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>S.No</th>
											<th class="text-left">Name</th>
											<th class="text-right">Percentage</th>
											<th class="text-left">From Date</th>
											<th class="text-left">To Date</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="count" value="1" />
										<c:forEach var="taxRate"
											items="${taxRatesForm.demandReasonDetails}">
											<tr>
												<c:choose>
													<c:when
														test="${taxRate.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster() ne 'Total Residential Tax'
												&& taxRate.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster() ne 'Total Non Residential Tax'}">
														<td><c:out value="${count}" /></td>
														<td><c:out
																value="${taxRate.getEgDemandReason().getEgDemandReasonMaster().getReasonMaster()}" /></td>
														<td class="text-right"><fmt:formatNumber
																var="formattedRate" type="number" minFractionDigits="2"
																maxFractionDigits="2" value="${taxRate.percentage}" />
															<c:out value="${formattedRate}" /></td>
														<td><fmt:formatDate value="${taxRate.fromDate}"
																pattern="dd-MM-yyyy" /></td>
														<td><fmt:formatDate value="${taxRate.toDate}"
																pattern="dd-MM-yyyy" /></td>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</tr>
											<c:set var="count" value="${count+1}" />
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="text-center">
				<a href="javascript:void(0)" class="btn btn-default"
					onclick="self.close()"><spring:message code="lbl.close" /></a>
			</div>
		</div>
	</div>
</div>