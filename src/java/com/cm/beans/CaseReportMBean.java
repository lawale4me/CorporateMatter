/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.dto.CaseDTO;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class CaseReportMBean {

    private List<CaseDTO> casedto;  
    private List<CaseDTO> casedtoDetails;  
    private List<CaseDTO> filteredCasedto;  
        
    private Date currentDate = new Date();  
    private boolean checkValue;  
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
      
  
    public CaseReportMBean() {  
        if (casedto == null) {  
            casedto = appManager.getAllCases();
        }  
    }  
  
    
  

  
    public Date getCurrentDate() {  
        return currentDate;  
    }  
  
    public void setCurrentDate(Date currentDate) {  
        this.currentDate = currentDate;  
    }  
  
    public boolean getCheckValue(){  
       return checkValue;  
    }  
  
    public void setCheckValue(boolean checkValue) {  
       this.checkValue = checkValue;  
    }  
       
  
    public void doSomething() {  
        try {  
            // simulate a long running request  
            Thread.sleep(1500);  
        } catch (Exception e) {  
            // ignore  
        }  
    }  

    public List<CaseDTO> getCasedto() {
        return casedto;
    }

    public void setCasedto(List<CaseDTO> casedto) {
        this.casedto = casedto;
    }

    public List<CaseDTO> getCasedtoDetails() {
        return casedtoDetails;
    }

    public void setCasedtoDetails(List<CaseDTO> casedtoDetails) {
        this.casedtoDetails = casedtoDetails;
    }

    public List<CaseDTO> getFilteredCasedto() {
        return filteredCasedto;
    }

    public void setFilteredCasedto(List<CaseDTO> filteredCasedto) {
        this.filteredCasedto = filteredCasedto;
    }
    
    
    
      
}        
