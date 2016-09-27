/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.entities.Activities;
import com.cm.entities.Users;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class ActivityMBean {

    private List<Activities> activities;
    private List<Activities> filteredActivity;
    private Activities activity;
    private Activities selectedActivity;
    String username;
    Users user;
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
    /**
     * Creates a new instance of ActivityMBean
     */
    public ActivityMBean() {
    }
    
    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");  
        user=appManager.getUser(username);                        
        activities = appManager.getMyActivities(user);
    }

    public List<Activities> getActivities() {
        return activities;
    }

    public void setActivities(List<Activities> activities) {
        this.activities = activities;
    }

    public List<Activities> getFilteredActivity() {
        return filteredActivity;
    }

    public void setFilteredActivity(List<Activities> filteredActivity) {
        this.filteredActivity = filteredActivity;
    }

    public Activities getActivity() {
        return activity;
    }

    public void setActivity(Activities activity) {
        this.activity = activity;
    }

    public Activities getSelectedActivity() {
        return selectedActivity;
    }

    public void setSelectedActivity(Activities selectedActivity) {
        this.selectedActivity = selectedActivity;
    }                    
    
}
