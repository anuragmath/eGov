/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) 2016  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.infra.utils.customservice;

import org.egov.infra.common.entity.Config;
import org.egov.infra.common.entity.ServiceImplementations;
import org.egov.infra.common.service.ServiceImplementationService;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceFactory {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ServiceImplementationService serviceImplementationService;

    public <T> Object getService(final String stateName, final String ulbName, final String moduleName,
            final Class<T> serviceInterface) {

        ServiceImplementations impl = null;

        if (moduleName != null && serviceInterface != null)
            if (stateName != null && !stateName.isEmpty() && ulbName != null && !ulbName.isEmpty())
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndUlbNameAndStateName(moduleName,
                        serviceInterface.getName(), ulbName, stateName);
            else if (stateName != null && !stateName.isEmpty() && (ulbName == null || ulbName.isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndStateName(moduleName,
                        serviceInterface.getName(), stateName);

            else if (ulbName != null && !ulbName.isEmpty() && (stateName == null || stateName.isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndUlbName(moduleName,
                        serviceInterface.getName(), ulbName);

            else if ((ulbName == null || ulbName.isEmpty()) && (stateName == null || stateName.isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClass(moduleName,
                        serviceInterface.getName());

        if (impl == null)
            throw new ApplicationRuntimeException(
                    "Could not find any implementation bean for interface " + serviceInterface.getSimpleName());
        else
            try {
                final T service = (T) Class.forName(impl.getImplementationClass()).newInstance();
                context.getAutowireCapableBeanFactory().autowireBeanProperties(service,
                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                return service;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new ApplicationRuntimeException(
                        "Could not give any implementation bean for interface " + serviceInterface.getSimpleName());
            }
    }

    public <T> Object getService(final Config<T> config) {

        ServiceImplementations impl = null;

        if (config.getModuleName() != null && config.getServiceInterface() != null)
            if (config.getStateName() != null && !config.getStateName().isEmpty() && config.getUlbName() != null
                    && !config.getUlbName().isEmpty())
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndUlbNameAndStateName(config.getModuleName(),
                        config.getServiceInterface().getName(), config.getUlbName(), config.getStateName());
            else if (config.getStateName() != null && !config.getStateName().isEmpty()
                    && (config.getUlbName() == null || config.getUlbName().isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndStateName(config.getModuleName(),
                        config.getServiceInterface().getName(), config.getStateName());

            else if (config.getUlbName() != null && !config.getUlbName().isEmpty()
                    && (config.getStateName() == null || config.getStateName().isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClassAndUlbName(config.getModuleName(),
                        config.getServiceInterface().getName(), config.getUlbName());

            else if ((config.getUlbName() == null || config.getUlbName().isEmpty())
                    && (config.getStateName() == null || config.getStateName().isEmpty()))
                impl = serviceImplementationService.getByModuleNameAndServiceClass(config.getModuleName(),
                        config.getServiceInterface().getName());

        if (impl == null)
            throw new ApplicationRuntimeException(
                    "Could not find any implementation bean for interface " + config.getServiceInterface().getSimpleName());
        else
            try {
                final T service = (T) Class.forName(impl.getImplementationClass()).newInstance();
                context.getAutowireCapableBeanFactory().autowireBeanProperties(service,
                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
                return service;
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new ApplicationRuntimeException(
                        "Could not give any implementation bean for interface " + config.getServiceInterface().getSimpleName());
            }
    }
}
