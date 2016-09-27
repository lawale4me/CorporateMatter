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
import com.cm.entities.Cases_;
import com.cm.entities.Comments;
import com.cm.entities.Documents;
import com.cm.entities.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 *
 * @author Ahmed
 */

public class AdminRepositoryImpl extends RepositoryBase implements AdminRepository 
{
    
    public AdminRepositoryImpl() {
    }
    

    @Override
    public Users validate(String userid, String password) {
        EntityManager manager = RepositoryManager.getManager();
        String query="SELECT u FROM Users u WHERE u.username = :username and u.password = :password";
        List<Users> adminUser =    manager.createQuery(query,Users.class).setParameter("username", userid).setParameter("password", password).getResultList();
        return adminUser.isEmpty() ? null : adminUser.get(0);
    }

    @Override
    public List<Cases> getMyCases(Users user) {
        EntityManager manager = RepositoryManager.getManager();
        String query="SELECT c FROM Cases c WHERE c.caseOwner = :caseOwner";
        List<Cases> myCases =    manager.createQuery(query,Cases.class).setParameter("caseOwner", user.getUserID()).getResultList();
        return myCases.isEmpty() ? null : myCases;
    }

    @Override
    public Users getUsers(String username) {
      if(session!=null&&session.isActive())
        {
        List<Users> profile = manager.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", username).getResultList();
        return profile.isEmpty() ? null : profile.get(0); 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Users> profile = manager.createNamedQuery("Users.findByUsername", Users.class).setParameter("username", username).getResultList();
        manager.close();
        return profile.isEmpty() ? null : profile.get(0); 
        } 
    }

    @Override
    public void addCase(Cases case1) 
    {
        if(session!=null&&session.isActive())
        {
         manager.persist(case1);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(case1);
        manager.getTransaction().commit();
        }
     }

    @Override
    public void addActivity(Activities activity) {
        if(session!=null&&session.isActive())
        {
         manager.persist(activity);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(activity);
        manager.getTransaction().commit();
        }
    }

    @Override
    public List<Activities> getMyActivities(Users user) {
        EntityManager manager = RepositoryManager.getManager();                
        
        List<Cases> mycases=getMyCases(user);        
       // String query=  "SELECT * FROM activities where caseID in (SELECT caseID FROM cases where caseOwner = :caseOwner )";        
        // order by activityID desc limit 10                        
        //Criteria
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Activities> query = cb.createQuery(Activities.class);
        Root<Activities> poRoot = query.from(Activities.class);
        query.select(poRoot);
        Subquery<Cases> subquery = query.subquery(Cases.class);
        Root<Cases> subRoot = subquery.from(Cases.class);
        subquery.select(subRoot);
        Predicate p = cb.equal(subRoot.get(Cases_.activitiesCollection),poRoot);
        subquery.where(p);
        query.where(
                cb.or(
                cb.exists(subquery),
                cb.equal(poRoot.get("owner"), user)
                )
        );                         
        query.orderBy(cb.desc(poRoot.get("activityID")));
        
        TypedQuery<Activities> typedQuery = manager.createQuery(query);
        List<Activities> myActivities = typedQuery.getResultList();        
        //END OF CRITERIA                                                                
//        List<Activities> myActivities =    manager.createQuery(query,Activities.class).setParameter("caseOwner", user.getUserID()).getResultList();
        
        return myActivities.isEmpty() ? null : myActivities;
    }

    @Override
    public List<Branch> getBranches() {
     if(session!=null&&session.isActive())
        {
        List<Branch> branches = manager.createNamedQuery("Branch.findAll", Branch.class).getResultList();
        return branches.isEmpty() ? null : branches;       
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Branch> branches = manager.createNamedQuery("Branch.findAll", Branch.class).getResultList();     
        manager.close();
        return branches.isEmpty() ? null : branches;  
        }   
    }

    @Override
    public void addUser(Users user) {
         if(session!=null&&session.isActive())
        {
         manager.persist(user);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
        }
    }

    @Override
    public void addDocument(Documents doc) {
          if(session!=null&&session.isActive())
        {
         manager.persist(doc);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(doc);
        manager.getTransaction().commit();
        }
    }

    @Override
    public Cases getCase(Integer caseID) {
        if(session!=null&&session.isActive())
        {
        List<Cases> case1 = manager.createNamedQuery("Cases.findByCaseID", Cases.class).setParameter("caseID", caseID).getResultList();
        return case1.isEmpty() ? null : case1.get(0); 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Cases> case1 = manager.createNamedQuery("Cases.findByCaseID", Cases.class).setParameter("caseID", caseID).getResultList();        
        manager.close();
        return case1.isEmpty() ? null : case1.get(0); 
        } 
    }

    @Override
    public Documents getDocument(String docName) {
      if(session!=null&&session.isActive())
        {
        List<Documents> docs = manager.createNamedQuery("Documents.findByDocname", Documents.class).setParameter("documentname", docName).getResultList();
        return docs.isEmpty() ? null : docs.get(0); 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Documents> docs = manager.createNamedQuery("Documents.findByDocname", Documents.class).setParameter("documentname", docName).getResultList();        
        manager.close();
        return docs.isEmpty() ? null : docs.get(0); 
        } 
    }

    @Override
    public void deleteDocument(Documents doc) {
        if (session!=null&&session.isActive())
        {
            Log.l.infoLog.info("session");            
            manager.remove(doc);
        } else
        {
            Log.l.infoLog.info("no session");
            EntityManager manager = RepositoryManager.getManager();
            manager.getTransaction().begin();            
            manager.remove(doc);
            manager.getTransaction().commit();
        }
    }

    @Override
    public List<Users> getAdminUsers(UserType selectedType) {
        EntityManager manager = RepositoryManager.getManager();
        List<Users> users = manager.createNamedQuery("Users.findByRole", Users.class).setParameter("role", selectedType.toString()).getResultList();                
        return users.isEmpty() ? null : users;
    }

    @Override
    public void updateCase(Cases cases) {
          if (session!=null&&session.isActive()) {
            manager.merge(cases);
        }else{
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.merge(cases);
        manager.getTransaction().commit();
        }
    }

    @Override
    public List<Comments> getComments(Integer caseID) {        
        if(session!=null&&session.isActive())
        {           
        List<Comments> cmnts = manager.createNamedQuery("Comments.findByCaseID", Comments.class).setParameter("caseID", getCase(caseID).getCaseID()).getResultList();        
           return cmnts.isEmpty() ? null : cmnts;       
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Comments> cmnts = manager.createNamedQuery("Comments.findByCaseID", Comments.class).setParameter("caseID", getCase(caseID).getCaseID()).getResultList();        
        manager.close();
        return cmnts.isEmpty() ? null : cmnts;  
        }   
    }

    @Override
    public void addComment(Comments comments) {
         if(session!=null&&session.isActive())
        {
         manager.persist(comments);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(comments);
        manager.getTransaction().commit();
        }
    }

    @Override
    public Users getUser(Integer userID) {
        if(session!=null&&session.isActive())
        {
        List<Users> user = manager.createNamedQuery("Users.findByUserID", Users.class).setParameter("userID", userID).getResultList();
        return user.isEmpty() ? null : user.get(0); 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Users> user = manager.createNamedQuery("Users.findByUserID", Users.class).setParameter("userID", userID).getResultList();
        manager.close();
        return user.isEmpty() ? null : user.get(0); 
        }         
    }

    @Override
    public List<Documents> getDocumments(Integer caseID) {
           
        if(session!=null&&session.isActive())
        {           
        List<Documents> cmnts = manager.createNamedQuery("Documents.findByCaseID", Documents.class).setParameter("caseID", getCase(caseID).getCaseID()).getResultList();        
           return cmnts.isEmpty() ? null : cmnts;       
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Documents> cmnts = manager.createNamedQuery("Comments.findByCaseID", Documents.class).setParameter("caseID", getCase(caseID).getCaseID()).getResultList();        
        manager.close();
        return cmnts.isEmpty() ? null : cmnts;  
        }   
    }

    @Override
    public List<Users> getAllUsers() {
        if(session!=null&&session.isActive())
        {           
        List<Users> cmnts = manager.createNamedQuery("Users.findAll", Users.class).getResultList();        
        return cmnts.isEmpty() ? null : cmnts;       
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Users> cmnts = manager.createNamedQuery("Users.findAll", Users.class).getResultList();        
        manager.close();
        return cmnts.isEmpty() ? null : cmnts;
        }
    }

    @Override
    public List<Cases> getAllCases() {
         if(session!=null&&session.isActive())
        {
        List<Cases> case1 = manager.createNamedQuery("Cases.findAll", Cases.class).getResultList();
        return case1.isEmpty() ? null : case1; 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<Cases> case1 = manager.createNamedQuery("Cases.findAll", Cases.class).getResultList();        
        manager.close();
        return case1.isEmpty() ? null : case1; 
        } 
    }
    
    @Override
    public List<AuditReport> getAllAuditReports() {
         if(session!=null&&session.isActive())
        {
        List<AuditReport> case1 = manager.createNamedQuery("AuditReport.findAll", AuditReport.class).getResultList();
        return case1.isEmpty() ? null : case1; 
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        List<AuditReport> case1 = manager.createNamedQuery("AuditReport.findAll", AuditReport.class).getResultList();
        manager.close();
        return case1.isEmpty() ? null : case1; 
        } 
    }

    @Override
    public void audit(AuditReport ar) {
        if(session!=null&&session.isActive())
        {
         manager.persist(ar);
        }
        else
        {
        EntityManager manager = RepositoryManager.getManager();
        manager.getTransaction().begin();
        manager.persist(ar);
        manager.getTransaction().commit();
        }
    }
    
    
   
}
