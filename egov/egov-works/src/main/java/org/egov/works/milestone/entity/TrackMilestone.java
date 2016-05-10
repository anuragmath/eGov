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
package org.egov.works.milestone.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.egov.commons.EgwStatus;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.egov.infra.workflow.entity.StateAware;

@Entity
@Table(name = "EGW_TRACK_MILESTONE")
@Unique(id = "id", tableName = "EGW_TRACK_MILESTONE")
@SequenceGenerator(name = TrackMilestone.SEQ_EGW_TRACK_MILESTONE, sequenceName = TrackMilestone.SEQ_EGW_TRACK_MILESTONE, allocationSize = 1)
public class TrackMilestone extends StateAware {

    private static final long serialVersionUID = -366602348464540736L;

    public static final String SEQ_EGW_TRACK_MILESTONE = "SEQ_EGW_TRACK_MILESTONE";

    public enum TrackMilestoneStatus {
        CREATED, APPROVED, REJECTED, CANCELLED, RESUBMITTED
    }

    public enum Actions {
        SUBMIT_FOR_APPROVAL, APPROVE, REJECT, CANCEL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @Id
    @GeneratedValue(generator = SEQ_EGW_TRACK_MILESTONE, strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal total;

    private Boolean isProjectCompleted;

    @Temporal(TemporalType.DATE)
    private Date approvedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status", nullable = false)
    private EgwStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone", nullable = false)
    private Milestone milestone;

    @OrderBy("id")
    @OneToMany(mappedBy = "trackMilestone", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = TrackMilestoneActivity.class)
    private List<TrackMilestoneActivity> activities = new LinkedList<TrackMilestoneActivity>();

    private transient String ownerName;

    @Transient
    private String approvalComent;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(final BigDecimal total) {
        this.total = total;
    }

    public void setProjectCompleted(final boolean isProjectCompleted) {
        this.isProjectCompleted = isProjectCompleted;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(final Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public EgwStatus getStatus() {
        return status;
    }

    public void setStatus(final EgwStatus status) {
        this.status = status;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public String getStateDetails() {
        return "Estimate Number : " + milestone.getWorkOrderEstimate().getEstimate().getEstimateNumber();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }

    public List<TrackMilestoneActivity> getActivities() {
        return activities;
    }

    public void addActivity(final TrackMilestoneActivity activity) {
        activities.add(activity);
    }

    public Boolean getIsProjectCompleted() {
        return isProjectCompleted;
    }

    public void setIsProjectCompleted(final Boolean isProjectCompleted) {
        this.isProjectCompleted = isProjectCompleted;
    }

    public void setActivities(final List<TrackMilestoneActivity> activities) {
        this.activities = activities;
    }

    public String getApprovalComent() {
        return approvalComent;
    }

    public void setApprovalComent(final String approvalComent) {
        this.approvalComent = approvalComent;
    }
}
