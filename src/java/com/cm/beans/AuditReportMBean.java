/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.dto.AuditReportDTO;
import com.cm.dto.CaseDTO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class AuditReportMBean {
    
    private List<AuditReportDTO> ardto;  
    private List<AuditReportDTO> ardtoDetails;  
    private List<AuditReportDTO> filteredARdto; 
    
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    

    /**
     * Creates a new instance of AuditReportMBean
     */
    public AuditReportMBean() {
        if (ardto == null) {  
            ardto = appManager.getAllAuditReports();
        }  
    }

    public List<AuditReportDTO> getArdto() {
        return ardto;
    }

    public void setArdto(List<AuditReportDTO> ardto) {
        this.ardto = ardto;
    }

    public List<AuditReportDTO> getArdtoDetails() {
        return ardtoDetails;
    }

    public void setArdtoDetails(List<AuditReportDTO> ardtoDetails) {
        this.ardtoDetails = ardtoDetails;
    }

    public List<AuditReportDTO> getFilteredARdto() {
        return filteredARdto;
    }

    public void setFilteredARdto(List<AuditReportDTO> filteredARdto) {
        this.filteredARdto = filteredARdto;
    }
    
    
    
    
    
}
