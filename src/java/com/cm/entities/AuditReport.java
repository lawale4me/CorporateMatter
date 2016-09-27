package com.cm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries(
{
    @NamedQuery(name = "AuditReport.findAll", query = "SELECT a FROM AuditReport a"),
    @NamedQuery(name="AuditReport.findByUsername", query="SELECT a FROM AuditReport a WHERE a.username = :username order by a.id desc "),
    @NamedQuery(name="AuditReport.findByDate", query="SELECT a FROM AuditReport a WHERE a.actionDate BETWEEN :actiondate1 AND :actiondate2 order by a.id desc ")
})
public class AuditReport implements Serializable 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;    
    private String username;
    private String action;
    private String ipAddress;
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;

    public AuditReport(){
        
    }
    
    public AuditReport(String username, String action, String ipAddress) {        
        this.username = username;
        this.action = action;
        this.ipAddress = ipAddress;
        this.actionDate = new Date();
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }        
    
}
