/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) 2016  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *          1) All versions of this program, verbatim or modified must carry this
 *             Legal Notice.
 *
 *          2) Any misrepresentation of the origin of the material is prohibited. It
 *             is required that all modified versions of this material be marked in
 *             reasonable ways as different from the original version.
 *
 *          3) This license does not grant any rights to any user of the program
 *             with regards to rights under trademark law for use of the trade names
 *             or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.tl.web.response.adaptor;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.egov.tl.entity.dto.BaseRegisterForm;

import java.lang.reflect.Type;


public class BaseRegisterResponseAdaptor implements JsonSerializer<BaseRegisterForm> {
    @Override
    public JsonElement serialize(BaseRegisterForm baseRegisterForm, Type type, JsonSerializationContext jsc) {
        JsonObject baseRegisterResponse = new JsonObject();
        baseRegisterResponse.addProperty("tinno", baseRegisterForm.getLicensenumber() != null ? baseRegisterForm.getLicensenumber() : "NA");
        baseRegisterResponse.addProperty("licenseId", baseRegisterForm.getLicenseid());
        baseRegisterResponse.addProperty("tradetitle", baseRegisterForm.getTradetitle());
        baseRegisterResponse.addProperty("category", baseRegisterForm.getCategoryname());
        baseRegisterResponse.addProperty("subcategory", baseRegisterForm.getSubcategoryname());
        baseRegisterResponse.addProperty("owner", baseRegisterForm.getOwner());
        baseRegisterResponse.addProperty("mobile", baseRegisterForm.getMobile());
        baseRegisterResponse.addProperty("assessmentno", baseRegisterForm.getAssessmentno() != null && !baseRegisterForm.getAssessmentno().isEmpty() ? baseRegisterForm.getAssessmentno() : "NA");
        baseRegisterResponse.addProperty("wardname", baseRegisterForm.getWardname());
        baseRegisterResponse.addProperty("localityname", baseRegisterForm.getLocalityname());
        baseRegisterResponse.addProperty("tradeaddress", baseRegisterForm.getTradeaddress());
        baseRegisterResponse.addProperty("commencementdate", baseRegisterForm.getCommencementdate());
        baseRegisterResponse.addProperty("status", baseRegisterForm.getStatusname());
        baseRegisterResponse.addProperty("arrearlicfee", baseRegisterForm.getArrearlicensefee());
        baseRegisterResponse.addProperty("arrearpenaltyfee", baseRegisterForm.getArrearpenaltyfee());
        baseRegisterResponse.addProperty("curlicfee", baseRegisterForm.getCurlicensefee());
        baseRegisterResponse.addProperty("curpenaltyfee", baseRegisterForm.getCurpenaltyfee());
        baseRegisterResponse.addProperty("unitofmeasure", baseRegisterForm.getUnitofmeasure());
        baseRegisterResponse.addProperty("tradearea", baseRegisterForm.getTradewt());
        baseRegisterResponse.addProperty("rate", baseRegisterForm.getRateval());
        return baseRegisterResponse;
    }
}
