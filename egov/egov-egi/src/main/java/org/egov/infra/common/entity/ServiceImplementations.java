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

package org.egov.infra.common.entity;

import static org.egov.infra.common.entity.ServiceImplementations.SEQ_EG_SERVICEIMPLEMENTATIONS;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.infra.persistence.entity.AbstractPersistable;

@Entity
@Table(name = "eg_serviceimplementations", schema = "public")
@SequenceGenerator(name = SEQ_EG_SERVICEIMPLEMENTATIONS, sequenceName = SEQ_EG_SERVICEIMPLEMENTATIONS, allocationSize = 1)
public class ServiceImplementations extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -6477774727634633483L;

    public static final String SEQ_EG_SERVICEIMPLEMENTATIONS = "SEQ_EG_SERVICEIMPLEMENTATIONS";

    @Id
    @GeneratedValue(generator = SEQ_EG_SERVICEIMPLEMENTATIONS, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "state")
    private EgState state;

    @ManyToOne
    @JoinColumn(name = "ulb")
    private Ulb ulb;

    private String moduleName;

    private String serviceClass;

    private String implementationClass;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public EgState getState() {
        return state;
    }

    public void setState(final EgState state) {
        this.state = state;
    }

    public Ulb getUlb() {
        return ulb;
    }

    public void setUlb(final Ulb ulb) {
        this.ulb = ulb;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(final String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getImplementationClass() {
        return implementationClass;
    }

    public void setImplementationClass(final String implementationClass) {
        this.implementationClass = implementationClass;
    }

}
