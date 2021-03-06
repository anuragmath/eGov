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

<%@ page contentType="text/html" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0"
			style="text-align: left">
			<div class="panel-heading">
				<div class="panel-title"><spring:message code="lbl.hdr.propertydetails" /></div>
			</div>
			<div class="panel-body">
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.assmtno" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.basicProperty.upicNo}"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.assmtno.parentproperty" />
					</div>
					<div class="col-xs-3 add-margin view-content">N/A</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.propertytype" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.categoryType}"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.excemption" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.taxExemptedReason.name}" default="N/A"></c:out>
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.annualvalue" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						Rs. <fmt:formatNumber value="${ARV}" pattern="#,##0" /> 
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.effectivedate" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${property.basicProperty.propOccupationDate}" />
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.category.ownership" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out default="N/A" value="${property.propertyDetail.propertyTypeMaster.type}"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.appartmentorcomplex" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.apartment.name}" default="N/A"></c:out>
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.extentofsite" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.sitalArea.area}"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.extent.appurtenant" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.extentAppartenauntLand}"></c:out>
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.superstructure" />
					</div>
					<c:choose>
						<c:when test="${property.propertyDetail.structure == 'true'}">
							<div class="col-xs-3 add-margin view-content">Yes</div>
						</c:when>
						<c:otherwise>
							<div class="col-xs-3 add-margin view-content">No</div>
						</c:otherwise>
					</c:choose>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.siteowner" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.siteOwner}"></c:out>
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.registrationDoc.no" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.basicProperty.regdDocNo}"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.registrationDoc.date" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${property.basicProperty.regdDocDate}" />
					</div>
				</div>
				<%-- <div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.bpno" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.buildingPermissionNo}" default="N/A"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.bpdate" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<fmt:formatDate pattern="dd/MM/yyyy" value="${property.propertyDetail.buildingPermissionDate}" />
					</div>
				</div>
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.percentagedeviation" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.deviationPercentage}" default="N/A"></c:out>
					</div>
					<div class="col-xs-3 add-margin">
						<spring:message code="lbl.reason.creation" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<c:out value="${property.propertyDetail.propertyMutationMaster.mutationName}"></c:out>
					</div>
				</div> --%>
			</div>
		</div>
	</div>
</div>