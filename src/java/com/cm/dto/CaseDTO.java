/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.dto;

import com.cm.entities.Assignments;
import com.cm.entities.Cases;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ahmed
 */
public class CaseDTO implements Serializable
{  
    private Integer caseID;    
    private String caseName;        
    private String description;    
    private Date caseDate;    
    private String caseRef;   
    private String practiceArea;    
    private Integer caseOwner;    
    private String status;
    private String assignedTo;

    public CaseDTO(Cases c) {
        caseID=c.getCaseID();
        caseName=c.getCaseName();
        description=c.getDescription();
        caseDate=c.getCaseDate();
        caseRef=c.getCaseRef();
        practiceArea=c.getPracticeArea();
        caseOwner=c.getCaseOwner();
        status=c.getStatus();
        for(Assignments ass:c.getAssignmentsCollection()){
        assignedTo +=" "+ass.getUserID();
        }
        
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

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
 
    
    
    
    
}
