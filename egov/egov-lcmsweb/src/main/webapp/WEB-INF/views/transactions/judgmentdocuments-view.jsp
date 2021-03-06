<%@ include file="/includes/taglibs.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/taglib/cdn.tld" prefix="cdn"%>
<script
	src="<cdn:url value='/resources/js/app/documentsupload.js?rnd=${app_release_no}'/>"></script>
<div class="panel panel-primary" data-collapsed="0">
	<div class="panel-heading">
		<div class="panel-title">
		<c:choose>
			<c:when test="${mode != 'view'}">
			<spring:message code="lbl.documents" />
			</c:when>
			<c:otherwise>
				<spring:message code="lbl.upload.document" />
			</c:otherwise>
		</c:choose>
		</div>
	</div>
	<c:if test="${!judgment.judgmentDocuments.isEmpty() && mode == 'view' || mode =='edit'}">
		<c:forEach items="${judgment.judgmentDocuments}" var="judgmentDocuments">
			<a href="/egi/downloadfile?fileStoreId=${judgmentDocuments.supportDocs.fileStoreId}&moduleName=LCMS">${judgmentDocuments.supportDocs.fileName }</a><br />
		</c:forEach>
	</c:if>
	<c:if test="${mode == 'view' && judgment.judgmentDocuments.isEmpty()}">
		<spring:message code="lbl.no.documents" />
	</c:if>
	<c:if test="${ mode != 'view'}">
		<div>
			<table>
				<tbody>
					<tr>
						<td valign="top">
						 	<table id="uploadertbl"><tbody>
						 		<tr id="row1">			 				
									<td>
										<input type="file" name="file" id="file1" onchange="isValidFile(this.id)">
									</td>
								</tr>									 										
						 	</tbody></table>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="buttonbottom" align="center">
				<div class="form-group text-center">
				<button id="attachNewFileBtn" type="button" class="btn btn-primary" onclick="addFileInputField()"><spring:message code="lbl.addfile" /></button>
				</div>
			</div>
		</div>
	</c:if>
</div>