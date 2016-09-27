/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "cases")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cases.findAll", query = "SELECT c FROM Cases c"),
    @NamedQuery(name = "Cases.findByCaseID", query = "SELECT c FROM Cases c WHERE c.caseID = :caseID"),
    @NamedQuery(name = "Cases.findByCaseName", query = "SELECT c FROM Cases c WHERE c.caseName = :caseName"),
    @NamedQuery(name = "Cases.findByCaseDate", query = "SELECT c FROM Cases c WHERE c.caseDate = :caseDate"),
    @NamedQuery(name = "Cases.findByCaseRef", query = "SELECT c FROM Cases c WHERE c.caseRef = :caseRef"),
    @NamedQuery(name = "Cases.findByCaseOwner", query = "SELECT c FROM Cases c WHERE c.caseOwner = :caseOwner"),
    @NamedQuery(name = "Cases.findByStatus", query = "SELECT c FROM Cases c WHERE c.status = :status")})
public class Cases implements Serializable {
    @OneToMany(mappedBy = "caseID")
    private Collection<Assignments> assignmentsCollection;
//    @OneToMany(mappedBy = "case1")
//    private Collection<Comments> commentsCollection;
    private static final long serialVersionUID = 1L;
    
    
  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="caseID")
  private Collection<Documents> documentsCollection;

  @OneToMany(mappedBy="caseID")
  private Collection<Activities> activitiesCollection;
  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "caseID")
    private Integer caseID;
    @Column(name = "caseName")
    private String caseName;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "caseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date caseDate;
    @Column(name = "caseRef")
    private String caseRef;
    @Lob
    @Column(name = "practiceArea")
    private String practiceArea;
    @Column(name = "caseOwner")
    private Integer caseOwner;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "assignedTo", referencedColumnName = "UserID")
    @ManyToOne
    private Users assignedTo;

    public Cases() {
    }

    public Cases(Integer caseID) {
        this.caseID = caseID;
    }

    public Integer getCaseID() {
        return caseID;
    }

    public void setCaseID(Integer caseID) {
        this.caseID = caseID;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(Date caseDate) {
        this.caseDate = caseDate;
    }

    public String getCaseRef() {
        return caseRef;
    }

    public void setCaseRef(String caseRef) {
        this.caseRef = caseRef;
    }

    public String getPracticeArea() {
        return practiceArea;
    }

    public void setPracticeArea(String practiceArea) {
        this.practiceArea = practiceArea;
    }

    public Integer getCaseOwner() {
        return caseOwner;
    }

    public void setCaseOwner(Integer caseOwner) {
        this.caseOwner = caseOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Users assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (caseID != null ? caseID.hashCode() : 0);
        return hash;
    }

    public Collection<Documents> getDocumentsCollection() {
        return documentsCollection;
    }

    public void setDocumentsCollection(Collection<Documents> documentsCollection) {
        this.documentsCollection = documentsCollection;
    }

    public Collection<Activities> getActivitiesCollection() {
        return activitiesCollection;
    }

    public void setActivitiesCollection(Collection<Activities> activitiesCollection) {
        this.activitiesCollection = activitiesCollection;
    }
    
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cases)) {
            return false;
        }
        Cases other = (Cases) object;
        if ((this.caseID == null && other.caseID != null) || (this.caseID != null && !this.caseID.equals(other.caseID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cm.entities.Cases[ caseID=" + caseID + " ]";
    }

//    @XmlTransient
//    public Collection<Comments> getCommentsCollection() {
//        return commentsCollection;
//    }
//
//    public void setCommentsCollection(Collection<Comments> commentsCollection) {
//        this.commentsCollection = commentsCollection;
//    }
//    

    @XmlTransient
    public Collection<Assignments> getAssignmentsCollection() {
        return assignmentsCollection;
    }

    public void setAssignmentsCollection(Collection<Assignments> assignmentsCollection) {
        this.assignmentsCollection = assignmentsCollection;
    }
}
