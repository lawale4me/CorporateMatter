/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.core;


import com.cm.dto.UserType;
import com.cm.entities.Activities;
import com.cm.entities.AuditReport;
import com.cm.entities.Branch;
import com.cm.entities.Cases;
import com.cm.entities.Comments;
import com.cm.entities.Documents;
import com.cm.entities.Users;
import java.util.List;


/**
 *
 * @author Ahmed
 */
public interface AdminRepository extends UnitOfWork
{

    public Users validate(String userid, String password);
//    public List<Transactions> getTransactions();    
//    public List<Profile> getProfiles();
//    public Profile getProfile(String criteria);
//    public List<Profile> getProfilesByDate();
//    public Merchantprofile getMerchantProfile(String username);
//
//
//    public void addCampaignBatch(Campaignbatch campaignBatch);
//
//    public List<Transactions> getCampaigners(Double mindeposit);
//
//    public void addCampaign(Campaigns campaign);
//
//    public List<Campaignbatch> getMyCampaigns(Merchantprofile mprofile);
//
//    public List<Campaigns> getCampaigners(Campaignbatch cb);

    public List<Cases> getMyCases(Users user);
    public Users getUsers(String username);
    public void addCase(Cases case1);
    public void addActivity(Activities activity);
    public List<Activities> getMyActivities(Users user);
    public List<Branch> getBranches();

    public void addUser(Users user);

    public void addDocument(Documents doc);

    public Cases getCase(Integer caseID);

    public Documents getDocument(String docName);

    public void deleteDocument(Documents doc);

    public List<Users> getAdminUsers(UserType selectedType);

    public void updateCase(Cases cases);

    public List<Comments> getComments(Integer caseID);
    
    public List<Documents> getDocumments(Integer caseID);

    public void addComment(Comments comments);

    public Users getUser(Integer userID);

    public List<Users> getAllUsers();

    public List<Cases> getAllCases();
    public List<AuditReport> getAllAuditReports();

    public void audit(AuditReport ar);
    
}
