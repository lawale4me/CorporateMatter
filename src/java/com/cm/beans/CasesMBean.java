/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.dto.CaseStatus;
import com.cm.entities.Cases;
import com.cm.entities.Comments;
import com.cm.entities.Documents;
import com.cm.entities.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class CasesMBean implements Serializable {

    private List<Cases> cases;
    private List<Cases> filteredList;
    private Cases selectedCases,filteredCases;
    String username;
    Users user;
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
    private BarChartModel barModel;
    
    
    
    /**
     * Creates a new instance of CasesMBean
     */
    public CasesMBean() {
    }

    @PostConstruct
    public void init() {
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");  
        user=appManager.getUser(username);                
        cases = appManager.getMyCases(user);
        createBarModel();
        
    }

    public List<Cases> getCases() {
        return cases;
    }

    public void setCases(List<Cases> cases) {
        this.cases = cases;
    }

    public Cases getSelectedCases() {
        return selectedCases;
    }

    public void setSelectedCases(Cases selectedCases) {
        this.selectedCases = selectedCases;
    }

    public Cases getFilteredCases() {
        return filteredCases;
    }

    public void setFilteredCases(Cases filteredCases) {
        this.filteredCases = filteredCases;
    }
    
    

    public void onRowSelect(SelectEvent event)
    {
        try {                                    
            //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCases", event.getObject());            
            ArrayList<Documents> myDocuments = new ArrayList<Documents>(((Cases) event.getObject()).getDocumentsCollection());
            //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("myDocuments", myDocuments);                        
            FacesContext context = FacesContext.getCurrentInstance();            
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
            HttpSession httpSession = request.getSession(false); 
            httpSession.setAttribute("cases", ((Cases) event.getObject()));  
            httpSession.setAttribute("documents", myDocuments);  
            List<Comments> comments =appManager.getComments(((Cases) event.getObject()).getCaseID());
            httpSession.setAttribute("comments", comments);  
            FacesContext.getCurrentInstance().getExternalContext().redirect("viewcase");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<Cases> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<Cases> filteredList) {
        this.filteredList = filteredList;
    }

    
    
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();              
        for(CaseStatus c:CaseStatus.values())
        {
         ChartSeries ChCases = new ChartSeries();    
         ChCases.setLabel(c.name());
         if(cases!=null){
         for(Cases mcase:cases){
             if(c.toString().equals(mcase.getStatus())){
             ChCases.set(mcase.getCaseName(),c.ordinal());
             System.out.println("Case Status:"+mcase.getStatus());
             model.addSeries(ChCases);
             }
         }               
         }
        }                                   
        return model;
    }
    
    
    private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Case/Matter By Status");
        barModel.setLegendPosition("w");
         
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Case Name");                
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("STATUS");
        yAxis.setMin(0);
        yAxis.setMax(50);
    }
             

    public BarChartModel getBarModel() {
        return barModel;
    }

     
    
    
    
}
