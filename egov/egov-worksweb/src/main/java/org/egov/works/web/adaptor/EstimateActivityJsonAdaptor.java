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
package org.egov.works.web.adaptor;

import java.lang.reflect.Type;

import org.egov.works.abstractestimate.entity.Activity;
import org.egov.works.abstractestimate.entity.MeasurementSheet;
import org.egov.works.abstractestimate.entity.NonSor;
import org.egov.works.models.masters.ScheduleOfRate;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Component
public class EstimateActivityJsonAdaptor implements JsonSerializer<Activity> {

    @Override
    public JsonElement serialize(final Activity activity, final Type typeOfSrc,
            final JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        if (activity != null)
            if (activity.getSchedule() != null) {
                final ScheduleOfRate schedule = activity.getSchedule();
                jsonObject.addProperty("scheduleId", schedule.getId());
                jsonObject.addProperty("scheduleCategoryCode", schedule.getScheduleCategory().getCode());
                jsonObject.addProperty("scheduleCode", schedule.getCode());
                jsonObject.addProperty("scheduleSummary", schedule.getSummary());
                jsonObject.addProperty("scheduleDescription", schedule.getSummary());
                jsonObject.addProperty("scheduleUom", schedule.getUom().getUom());
                jsonObject.addProperty("scheduleUomId", schedule.getUom().getId());
                schedule.setSorRateValue(schedule.getRateOn(activity.getEstimateDate()).getRate().getValue());
                jsonObject.addProperty("scheduleRate", schedule.getSorRate());
                jsonObject.addProperty("scheduleQuantity", activity.getQuantity());
            } else {
                final NonSor nonSor = activity.getNonSor();
                jsonObject.addProperty("nonSorDescription", nonSor.getDescription());
                jsonObject.addProperty("nonSorUomId", activity.getUom().getId());
                jsonObject.addProperty("nonSorUom", activity.getUom().getUom());
                jsonObject.addProperty("nonSorRate", activity.getRate());
                jsonObject.addProperty("nonSorQuantity", activity.getQuantity());
            }

        if (!activity.getMeasurementSheetList().isEmpty()) {
            final JsonArray jsonArray = new JsonArray();
            for (final MeasurementSheet ms : activity.getMeasurementSheetList()) {
                final JsonObject child = new JsonObject();
                child.addProperty("slNo", ms.getSlNo());
                child.addProperty("remarks", ms.getRemarks());
                child.addProperty("no", ms.getNo());
                child.addProperty("length", ms.getLength());
                child.addProperty("width", ms.getWidth());
                child.addProperty("depthOrHeight", ms.getDepthOrHeight());
                child.addProperty("quantity", ms.getQuantity());
                child.addProperty("identifier", ms.getIdentifier());
                jsonArray.add(child);
            }
            jsonObject.add("ms", jsonArray);
        } else
            jsonObject.add("ms", new JsonArray());
        return jsonObject;
    }
}