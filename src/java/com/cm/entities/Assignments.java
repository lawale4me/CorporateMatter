/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "assignments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assignments.findAll", query = "SELECT a FROM Assignments a"),
    @NamedQuery(name = "Assignments.findByAssignmentID", query = "SELECT a FROM Assignments a WHERE a.assignmentID = :assignmentID"),
    @NamedQuery(name = "Assignments.findByStatus", query = "SELECT a FROM Assignments a WHERE a.status = :status")})
public class Assignments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assignmentID")
    private Integer assignmentID;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "userID", referencedColumnName = "UserID")
    @ManyToOne
    private Users userID;
    @JoinColumn(name = "caseID", referencedColumnName = "caseID")
    @ManyToOne
    private Cases caseID;

    public Assignments() {
    }

    public Assignments(Integer assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Integer getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(Integer assignmentID) {
        this.assignmentID = assignmentID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
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
        hash += (assignmentID != null ? assignmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assignments)) {
            return false;
        }
        Assignments other = (Assignments) object;
        if ((this.assignmentID == null && other.assignmentID != null) || (this.assignmentID != null && !this.assignmentID.equals(other.assignmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cm.entities.Assignments[ assignmentID=" + assignmentID + " ]";
    }
    
}
