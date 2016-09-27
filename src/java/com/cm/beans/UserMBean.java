/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.core.Log;
import com.cm.dto.UserType;
import com.cm.entities.Activities;
import com.cm.entities.Branch;
import com.cm.entities.Users;
import com.cm.util.Email;
import com.cm.util.SendEmail;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class UserMBean {

    
    String username,email;
    List<Branch> branches;
    Branch branch=new Branch();
    Branch selectedBranch;
    UserType role;
    String adminUsername;
    Users adminUser;
    List<String> branchnames;
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
    
    /**
     * Creates a new instance of UserMBean
     */
    public UserMBean() {
    }

    @PostConstruct
    public void init() {        
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        adminUsername=(String) httpSession.getAttribute("username");  
        adminUser=appManager.getUser(adminUsername);        
        branches = (List<Branch>) appManager.getBranches();
    }
    
    

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public Branch getSelectedBranch() {
        return selectedBranch;
    }

    public void setSelectedBranch(Branch selectedBranch) {
        this.selectedBranch = selectedBranch;
    }


    
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
    public void create(){
       
        
        Users user =new Users();
        user.setEmail(email);
        user.setUsername(username);
        user.setRole(role.toString());
        user.setPassword(username);
        user.setBranchid(adminUser.getBranchid());
        user.setStatus(1);
        
        
        appManager.addUser(user);
        appManager.addActivity(new Activities("You Created a new user:"+user.getUsername(), adminUser, null));
        role=null;
        email="";branch=null;            
        username="";
        
         Email email1=new Email();
         Email email2=new Email();
         email1.setEmailAddress(adminUser.getEmail());
         email1.setSubject("Your have create a new user :" +user.getUsername());
         email1.setMessage("User :" +user.getUsername()+"\n"+user.getEmail()+"\n"+user.getRole()+"\n "+Log.APPURL);
         
         email2.setEmailAddress(user.getEmail());
         email2.setSubject("Your Corporate Matter account has been created:" );
         email2.setMessage("User :" +user.getUsername()+"\n"+user.getEmail()+"\n"+user.getRole()+"\n "+Log.APPURL);
         
         new SendEmail().sendSimpleMail(email1);
         new SendEmail().sendSimpleMail(email2);
        
         appManager.audit(user.getUsername(), "New User created  "+user.getUsername(),"IPADDRESS");
        FacesMessage message = new FacesMessage("Succesful", user.getUsername()+" user has been Created.");
        FacesContext.getCurrentInstance().addMessage(null, message);     
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}