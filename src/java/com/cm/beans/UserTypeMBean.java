/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.dto.CaseStatus;
import com.cm.dto.UserType;
import com.cm.entities.Users;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ApplicationScoped
public class UserTypeMBean {
    
        private List<Users> adminUsers =new ArrayList();


    /**
     * Creates a new instance of UserTypeMBean
     */
    public UserTypeMBean() {
    }
    
    public UserType[] getUserTypes() {
        return UserType.values();
    }
    
    public UserType[] getUserOtherTypes() {
        UserType[] u = {UserType.Admin,UserType.ExternalLawyer,UserType.LegalDept};
        return  u;
    }
    
    public CaseStatus[] getCaseStatus() {
        return CaseStatus.values();
    }

    public List<Users> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<Users> adminUsers) {
        this.adminUsers = adminUsers;
    }
    
    
    
    
}
