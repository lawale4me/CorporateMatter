/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.dto;


/**
 *
 * @author Ahmed
 */
public class UserDTO  {
    
    private Integer status;
    private Integer userID;    
    private String username;    
    private String email;    
    private String fullname;
    private String role;    
    private String password;    
    private int branchid;

    public UserDTO(Integer userID) {
        this.userID = userID;
    }

    public UserDTO(String username) {
        this.username = username;
    }
    
    

    public UserDTO(Integer status, Integer userID, String username, String email, String fullname, String role, int branchid) {
        this.status = status;
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.role = role;        
        this.branchid = branchid;
    }

    public UserDTO() {
        
    }

 
    
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }
    
    
    @Override
    public String toString(){
        return userID.toString();
    }
    
    @Override
    public boolean equals(Object obj){
        return this.userID == ((Integer) obj).intValue();
    }

    
    
    
}
