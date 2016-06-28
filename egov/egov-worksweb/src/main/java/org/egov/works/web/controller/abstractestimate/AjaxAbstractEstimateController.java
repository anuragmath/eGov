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
package org.egov.works.web.controller.abstractestimate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.infra.exception.ApplicationException;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.works.abstractestimate.entity.AbstractEstimate;
import org.egov.works.abstractestimate.entity.AbstractEstimateForLoaSearchRequest;
import org.egov.works.abstractestimate.entity.AbstractEstimateForLoaSearchResult;
import org.egov.works.abstractestimate.service.EstimateService;
import org.egov.works.master.service.EstimateTemplateService;
import org.egov.works.master.service.OverheadService;
import org.egov.works.master.service.ScheduleOfRateService;
import org.egov.works.models.masters.EstimateTemplate;
import org.egov.works.models.masters.EstimateTemplateActivity;
import org.egov.works.models.masters.Overhead;
import org.egov.works.models.masters.OverheadRate;
import org.egov.works.models.masters.ScheduleOfRate;
import org.egov.works.web.adaptor.AbstractEstimateForLOAJsonAdaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(value = "/abstractestimate")
public class AjaxAbstractEstimateController {

    @Autowired
    private OverheadService overheadService;

    @Autowired
    private ScheduleOfRateService scheduleOfRateService;

    @Autowired
    private EstimateTemplateService estimateTemplateService;

    @Autowired
    private EstimateService estimateService;
    
    public Object toSearchAbstractEstimateForLOAResultJson(final Object object) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.registerTypeAdapter(AbstractEstimate.class, new AbstractEstimateForLOAJsonAdaptor()).create();
        final String json = gson.toJson(object);
        return json;
    }

    @RequestMapping(value = "/getpercentageorlumpsumbyoverheadid", method = RequestMethod.GET)
    public @ResponseBody OverheadRate getPercentageOrLumpsumByOverhead(@RequestParam("overheadId") final Long overheadId) {

        Overhead overhead = new Overhead();
        OverheadRate overheadRate = new OverheadRate();
        Date startDate, endDate;
        final Date estDate = new Date();
        if (overheadId != null)
            overhead = overheadService.getOverheadById(overheadId);

        if (overhead != null && overhead.getOverheadRates() != null && overhead.getOverheadRates().size() > 0)
            for (final OverheadRate obj : overhead.getOverheadRates()) {
                startDate = obj.getValidity().getStartDate();
                endDate = obj.getValidity().getEndDate();
                if (estDate.compareTo(startDate) >= 0 && (endDate == null || endDate.compareTo(estDate) >= 0))
                    overheadRate = obj;
            }

        return overheadRate;
    }

    @RequestMapping(value = "/ajaxsor-byschedulecategories", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody List<ScheduleOfRate> findSorByScheduleCategories(@RequestParam("code") final String code,
            @RequestParam("scheduleCategories") final String scheduleCategories, @RequestParam("estimateDate") final Date estimateDate) {
        if (!scheduleCategories.equals("null")) {
            return scheduleOfRateService.getScheduleOfRatesByCodeAndScheduleOfCategories(code, scheduleCategories, estimateDate);
        }
        return null;
    }

    @RequestMapping(value = "/ajaxestimatetemplatebycode", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<EstimateTemplate> getEstimateTemplateByCodeIgnoreCase(@RequestParam final String code) {
        return estimateTemplateService.getEstimateTemplateByCodeIgnoreCase(code);
    }

    @RequestMapping(value = "/ajaxgetestimatetemplatebyid/{id}", method = RequestMethod.GET)
    public @ResponseBody List<EstimateTemplateActivity> populateMilestoneTemplateActivity(@PathVariable final String id,
            final Model model)
            throws ApplicationException {
        List<EstimateTemplateActivity> activities = estimateTemplateService.getEstimateTemplateById(Long.valueOf(id))
                .getEstimateTemplateActivities();
        for (EstimateTemplateActivity activity : activities) {
            try {
                if (activity.getSchedule() != null)
                    activity.getSchedule().setSorRateValue(activity.getSchedule().getRateOn(new Date()).getRate().getValue());
            } catch (final ApplicationRuntimeException e) {
                activity.getSchedule().setSorRateValue((double) 0);
            }
        }
        return activities;
    }

    @RequestMapping(value = "/getAbstractEstimatesByNumber", method = RequestMethod.GET)
    public @ResponseBody List<String> findAbstractEstimateNumbersForAbstractEstimate(@RequestParam final String number) {
        List<AbstractEstimate> abstractEstimates = estimateService.getAbstractEstimateByEstimateNumberLike(number);
        final List<String> results = new ArrayList<String>();
        for (final AbstractEstimate abstractEstimate : abstractEstimates)
            results.add(abstractEstimate.getEstimateNumber());
        return results;
    }
    
    @RequestMapping(value = "/ajaxsearchabstractestimatesforloa", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String ajaxSearchAbstractEstimatesForLOA(final Model model,
            @ModelAttribute final AbstractEstimateForLoaSearchRequest abstractEstimateForLoaSearchRequest) {
        final List<AbstractEstimateForLoaSearchResult> searchResultList = estimateService
                .searchAbstractEstimatesForLOA(abstractEstimateForLoaSearchRequest);
        final String result = new StringBuilder("{ \"data\":").append(toSearchAbstractEstimateForLOAResultJson(searchResultList))
                .append("}").toString();
        return result;
    }
}