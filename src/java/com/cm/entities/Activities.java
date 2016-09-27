/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "activities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activities.findAll", query = "SELECT a FROM Activities a"),
    @NamedQuery(name = "Activities.findByActivityID", query = "SELECT a FROM Activities a WHERE a.activityID = :activityID"),
    @NamedQuery(name = "Activities.findByActivityType", query = "SELECT a FROM Activities a WHERE a.activityType = :activityType"),
    @NamedQuery(name = "Activities.findByActivityDate", query = "SELECT a FROM Activities a WHERE a.activityDate = :activityDate")})
public class Activities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "activityID")
    private Integer activityID;
    @Column(name = "activityType")
    private String activityType;
    @Column(name = "activityDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activityDate;
    @JoinColumn(name = "owner", referencedColumnName = "UserID")
    @ManyToOne
    private Users owner;
    @JoinColumn(name = "caseID", referencedColumnName = "caseID")
    @ManyToOne
    private Cases caseID;

    public Activities() {
    }
     public Activities(String activityType, Users owner, Cases caseID) {
         this.activityDate=new Date();
         this.activityType=activityType;
         this.owner=owner;
         this.caseID=caseID;
    }

    public Activities(Integer activityID) {
        this.activityID = activityID;
    }

    public Integer getActivityID() {
        return activityID;
    }

    public void setActivityID(Integer activityID) {
        this.activityID = activityID;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Cases getCaseID() {
        return caseID;
    }

    public void setCaseID(Cases caseID) {
        this.caseID = caseID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activityID != null ? activityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activities)) {
            return false;
        }
        Activities other = (Activities) object;
        if ((this.activityID == null && other.activityID != null) || (this.activityID != null && !this.activityID.equals(other.activityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cm.entities.Activities[ activityID=" + activityID + " ]";
    }
    
}
