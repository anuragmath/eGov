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
package org.egov.wtms.masters.service;

import org.egov.wtms.masters.entity.PropertyType;
import org.egov.wtms.masters.repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    @Autowired
    public PropertyTypeService(final PropertyTypeRepository propertyTypeRepository) {
        this.propertyTypeRepository = propertyTypeRepository;
    }

    public PropertyType findBy(final Long propertyTypeId) {
        return propertyTypeRepository.findOne(propertyTypeId);
    }

    @Transactional
    public PropertyType createPropertyType(final PropertyType propertyType) {
        return propertyTypeRepository.save(propertyType);
    }

    @Transactional
    public void updatePropertyType(final PropertyType propertyType) {
        propertyTypeRepository.save(propertyType);
    }

    public List<PropertyType> findAll() {
        return propertyTypeRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
    }

    public List<PropertyType> findAllByNameLike(final String name) {
        return propertyTypeRepository.findByNameContainingIgnoreCase(name);
    }

    public PropertyType findByName(final String name) {
        return propertyTypeRepository.findByName(name);
    }

    public PropertyType load(final Long id) {
        return propertyTypeRepository.getOne(id);
    }

    public Page<PropertyType> getListOfPropertyTypes(final Integer pageNumber, final Integer pageSize) {
        final Pageable pageable = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "name");
        return propertyTypeRepository.findAll(pageable);
    }

    public PropertyType findByCode(final String code) {
        return propertyTypeRepository.findByCode(code);
    }

    public List<PropertyType> getAllActivePropertyTypes() {
        return propertyTypeRepository.findByActiveTrueOrderByNameAsc();
    }

    public List<PropertyType> getPropertyTypeListForRest() {
        final List<PropertyType> propertyTypeList = propertyTypeRepository.findByActiveTrueOrderByNameAsc();
        final List<PropertyType> prepareListForRest = new ArrayList<PropertyType>(0);

        for (final PropertyType propertyType : propertyTypeList) {
            final PropertyType propertyTypeRest = new PropertyType();
            propertyTypeRest.setCode(propertyType.getCode());
            propertyTypeRest.setName(propertyType.getName());
            propertyTypeRest.setConnectionEligibility(propertyType.getConnectionEligibility());
            propertyTypeRest.setActive(propertyType.isActive());
            prepareListForRest.add(propertyTypeRest);
        }
        return prepareListForRest;
    }

}
