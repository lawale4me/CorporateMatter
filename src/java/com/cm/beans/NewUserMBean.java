/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.entities.Users;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class NewUserMBean {

    String username;
    Users adminUser,selectedUser;
    List<Users> adminUsers,filteredUsers;
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
    /**
     * Creates a new instance of NewUserMBean
     */
    public NewUserMBean() {
    }
    
    @PostConstruct
    public void init() {
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");  
        adminUser=appManager.getUser(username);                
        adminUsers = appManager.getAllUsers();
        
    }

    public List<Users> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<Users> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<Users> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<Users> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
    
    
    public void onRowSelect(){
        
    }
    
    
}
