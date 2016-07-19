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

package org.egov.infra.web.controller.admin.masters.accesscontrol;

import org.egov.infra.admin.master.entity.Feature;
import org.egov.infra.admin.master.entity.Role;
import org.egov.infra.admin.master.service.FeatureAccessControlService;
import org.egov.infra.admin.master.service.FeatureService;
import org.egov.infra.admin.master.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/feature/access-control")
public class FeatureAccessControlController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FeatureAccessControlService featureAccessControlService;

    @ModelAttribute
    public Feature feature() {
        return new Feature();
    }

    @RequestMapping(value = "/by-feature", method = RequestMethod.GET)
    public String createByFeature(Model model) {
        model.addAttribute("features", featureService.getAllFeatures());
        return "accesscontrol-search";
    }

    @RequestMapping(value = "/by-role", method = RequestMethod.GET)
    public String createByRole(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "accesscontrol-search";
    }

    @RequestMapping(value = "/by-feature", method = RequestMethod.POST)
    public String createByFeature(@RequestParam Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("feature", featureService.getFeatureById(id));
        return "accesscontrol";
    }

    @RequestMapping(value = "/by-role", method = RequestMethod.POST)
    public String createByRole(@RequestParam Long id, Model model) {
        model.addAttribute("features", featureService.getAllFeatures());
        model.addAttribute("role", roleService.getRoleById(id));
        return "accesscontrol";
    }

    @RequestMapping(value = "/grant/{featureId}/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public String createFeatureRoleMapping(@PathVariable("featureId") Feature feature, @PathVariable("roleId") Role role){
        featureAccessControlService.grantAccess(feature, role);
        return "DONE";
    }

    @RequestMapping(value = "/revoke/{featureId}/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public String removeFeatureRoleMapping(@PathVariable("featureId") Feature feature, @PathVariable("roleId") Role role){
        featureAccessControlService.revokeAccess(feature, role);
        return "DONE";
    }

}