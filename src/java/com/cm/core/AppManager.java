/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.core;

import com.cm.dto.AuditReportDTO;
import com.cm.dto.CaseDTO;
import com.cm.dto.Response;
import com.cm.dto.UserType;
import com.cm.entities.Activities;
import com.cm.entities.AuditReport;
import com.cm.entities.Branch;
import com.cm.entities.Cases;
import com.cm.entities.Comments;
import com.cm.entities.Documents;
import com.cm.entities.Users;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 *
 * @author Ahmed
 */
public class AppManager 
{
    
    private final AdminRepository adminrepo;


    public AppManager(AdminRepositoryImpl adminRepositoryImpl) {
        this.adminrepo = adminRepositoryImpl;
    }

     
    

    public List<Branch> getBranches() {
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Branch> tranxs=adminrepo.getBranches();
        ses.commit();
        return tranxs;
    }   

    public Response login(String userid, String password) throws Exception {
      Log.l.infoLog.info("Attempt to logon to the application "+userid);
      Response authres=new Response();
        UnitOfWorkSession ses = adminrepo.begin();
        try {
          //  password = Util.hash(password);
        } catch (Exception ex) {            
            Log.l.errorLog.error(ex.getMessage());
            Log.l.infoLog.info("Password hashing issue "+userid);
            throw new Exception("Password hashing issue");
        }
        
        
        
        Users adminUser = adminrepo.validate(userid, password);
        if(adminUser!=null)
        {            
            if(true){
            if(adminUser.getStatus()==1)
            {
            authres.setStatus(true);
            authres.setStatuscode(ResponseCodes.SUCCESSFUL);
            authres.setDescription("User exists");
            }
            else
            {
            authres.setStatuscode(ResponseCodes.USER_DISABLED);
            authres.setDescription("User is disabled");
            }
            }
            else{
                authres.setStatuscode(ResponseCodes.USER_EXPIRED);
                authres.setDescription("User password has expired");
            }
        } 
        else
        {             
             authres.setStatuscode(ResponseCodes.USER_NOT_FOUND);            
             authres.setDescription("Invalid username or password");     
        }                
        
        ses.commit();
        return authres;  
    }



    public List<Cases> getMyCases(Users user) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Cases> tranxs=adminrepo.getMyCases(user);
        ses.commit();
        return tranxs;
    }

    public Users getUser(String username) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         Users mprofile=adminrepo.getUsers(username);
         ses.commit();
        return mprofile;
    }
    
      public Users getUser(Integer userID) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         Users mprofile=adminrepo.getUser(userID);
         ses.commit();
        return mprofile;
    }


    public void addCase(Cases case1) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         adminrepo.addCase(case1);
         ses.commit();
    }
    
    public void addActivity(Activities activity) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         adminrepo.addActivity(activity);
         ses.commit();
    }

    public List<Activities> getMyActivities(Users user) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Activities> activities=adminrepo.getMyActivities(user);
        ses.commit();
        return activities;
    }

    public void addUser(Users user) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         adminrepo.addUser(user);
         ses.commit();
    }

    public void addDocument(Documents doc) {
         UnitOfWorkSession ses = adminrepo.begin(); 
         adminrepo.addDocument(doc);
         ses.commit();
    }

    public Cases getCases(Integer caseID) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Cases profile=adminrepo.getCase(caseID);
        ses.commit();
        return profile;
    }

    public Documents findDocument(String docName) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        Documents doc=adminrepo.getDocument(docName);
        ses.commit();
        return doc; 
    }

    public void deleteDoc(Documents doc) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        adminrepo.deleteDocument(doc);
        ses.commit();        
    }

    public List<Users> getAdminUsers(UserType selectedType) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Users> users=adminrepo.getAdminUsers(selectedType);
        ses.commit();
        return users;
    }

    public void updateCase(Cases cases) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        adminrepo.updateCase(cases);
        ses.commit();
        
    }

    public void addComment(Integer caseID, String comment, String logonUser) {
         UnitOfWorkSession ses = adminrepo.begin();          
         adminrepo.addComment(new Comments(comment,logonUser,caseID));
         ses.commit();
    }

    public List<Comments> getComments(Integer caseID) {
         UnitOfWorkSession ses = adminrepo.begin(); 
        List<Comments> cmts=adminrepo.getComments(caseID);
        ses.commit();
        return cmts;
    }

    public List<Users> getAllUsers() {
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Users> users=adminrepo.getAllUsers();
        ses.commit();
        return users;
    }

    public void audit(String userid, String action, String ipaddress) {
        UnitOfWorkSession ses = adminrepo.begin(); 
        AuditReport ar = new  AuditReport();
        ar.setAction(action);
        ar.setActionDate(new Date());
        ar.setIpAddress(ipaddress);
        ar.setUsername(userid);
        adminrepo.audit(ar);
         ses.commit();
    }

    public List<CaseDTO> getAllCases() {
        List<CaseDTO> casesdto=new ArrayList<CaseDTO>();
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<Cases> ccases=adminrepo.getAllCases();
        if(ccases!=null){
        for(Cases c:ccases){
            CaseDTO cas=new CaseDTO(c);            
            casesdto.add(cas);
        }
        }
        ses.commit();
        return casesdto;
    }
    
    public List<AuditReportDTO> getAllAuditReports() {
        List<AuditReportDTO> ardtos=new ArrayList<AuditReportDTO>();
        UnitOfWorkSession ses = adminrepo.begin(); 
        List<AuditReport> ar=adminrepo.getAllAuditReports();
        for(AuditReport a:ar){
            AuditReportDTO ardto=new AuditReportDTO(a);            
            ardtos.add(ardto);
        }
        ses.commit();
        return ardtos;
    }
    
    
     
}
