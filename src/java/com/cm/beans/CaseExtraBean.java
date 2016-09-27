/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.core.Log;
import com.cm.dto.CaseDTO;
import com.cm.dto.CaseStatus;
import com.cm.dto.UserDTO;
import com.cm.dto.UserType;
import com.cm.entities.Assignments;
import com.cm.entities.Cases;
import com.cm.entities.Comments;
import com.cm.entities.Documents;
import com.cm.entities.Users;
import com.cm.util.Email;
import com.cm.util.SendEmail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class CaseExtraBean implements Serializable {
             
        
    private List<String> users = new ArrayList<String>();  
    private UserType selectedType ;
    private String comment;    
    private Users selectedUser;
    private List<Users> adminUsers;
    private CaseDTO casedto;
    private List<CaseDTO> casedtos= new ArrayList<CaseDTO>();  
    private UserDTO userdto;
    private Integer selecteddto;
    private List<UserDTO> userdtos= new ArrayList<UserDTO>();      
    private List<String> usernames=new ArrayList();
    private CaseStatus selectedCaseStatus;
    private String selectedAdminUser = new String();
    
    //for file upload
    String documentDescription;
    private UploadedFile file;
    Users user;
    private static final int BUFFER_SIZE = 6124;  
    
    
    
    AppManager appManager = new AppManager(new AdminRepositoryImpl());
    
    
    /**
     * Creates a new instance of CaseExtraBean
     */
    public CaseExtraBean() {
    }
    
    
    @PostConstruct
    public void init() {
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);         
        String username=(String) httpSession.getAttribute("username");  
        user=appManager.getUser(username);
    }

    public Integer getSelecteddto() {
        return selecteddto;
    }

    public void setSelecteddto(Integer selecteddto) {
        this.selecteddto = selecteddto;
    }                

    public String getSelectedAdminUser() {
        return selectedAdminUser;
    }

    public void setSelectedAdminUser(String selectedAdminUser) {
        this.selectedAdminUser = selectedAdminUser;
    }
    
    
    public void setSelectedUserDTO(){
        	System.out.println("Selected userdto " + userdto);
    }
    
    
    public void setStateOptions() {
		System.out.println("Selected Role " + selectedType);
		users.clear();
                adminUsers=appManager.getAdminUsers(selectedType);
                System.out.println("Size of AdminUsers: " + adminUsers.size());
		if(adminUsers!=null){                        
			for(Users u:adminUsers){                        
                        userdtos.add(new UserDTO(u.getStatus(),u.getUserID(),u.getUsername(),u.getEmail(),u.getFullname(),u.getRole(),u.getBranchid().getBranchid()));
                        }
                }
		
	}

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }  

    public UserType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(UserType selectedType) {
        this.selectedType = selectedType;
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
    

    
    public void updateCase()
    {        
        System.out.println("Size of Users : " + userdtos.size());        
        System.out.println("Selected User:"+userdto);
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        Cases cases=(Cases) httpSession.getAttribute("cases"); 
        
        
        Collection<Assignments> col=cases.getAssignmentsCollection();
        boolean b=false;
        for(Assignments ass:col){
        if(ass.getUserID().getUsername().equalsIgnoreCase(userdto.getUsername()))b=true;                           
        }
        if(!b){
            Assignments a=new Assignments();
            a.setCaseID(cases);
            a.setStatus(1);
            a.setUserID(appManager.getUser(userdto.getUsername()));            
            cases.getAssignmentsCollection().add(a);
            
            Email email=new Email();
            email.setEmailAddress(userdto.getEmail());
            email.setSubject("A new Case " +cases.getCaseName()+" has been assigned to you");
            email.setMessage("You has been assigned to \n Case :" +cases.getCaseName()+"\n"+cases.getPracticeArea()+"\n"+cases.getStatus()+"\n "+Log.APPURL);
            new SendEmail().sendSimpleMail(email);
        }                
        
        appManager.updateCase(cases);
    
        System.out.println("Cases Assigned to"+cases.getAssignedTo());
        httpSession.setAttribute("cases", cases);        
    }
    
    public void updateStatus(){
        System.out.println("Case Status Changed to"+selectedCaseStatus.toString());
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        Cases cases=(Cases) httpSession.getAttribute("cases"); 
        cases.setStatus(selectedCaseStatus.toString());
        appManager.updateCase(cases);
        httpSession.setAttribute("cases", cases);        
    }

    public CaseStatus getSelectedCaseStatus() {
        return selectedCaseStatus;
    }

    public void setSelectedCaseStatus(CaseStatus selectedCaseStatus) {
        this.selectedCaseStatus = selectedCaseStatus;
    }

    public List<Users> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<Users> adminUsers) {
        this.adminUsers = adminUsers;
    }     

    public CaseDTO getCasedto() {
        return casedto;
    }

    public void setCasedto(CaseDTO casedto) {
        this.casedto = casedto;
    }

    public List<CaseDTO> getCasedtos() {
        return casedtos;
    }

    public void setCasedtos(List<CaseDTO> casedtos) {
        this.casedtos = casedtos;
    }

    public UserDTO getUserdto() {
        return userdto;
    }

    public void setUserdto(UserDTO userdto) {
        this.userdto = userdto;
    }

    public List<UserDTO> getUserdtos() {
        return userdtos;
    }

    public void setUserdtos(List<UserDTO> userdtos) {
        this.userdtos = userdtos;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String Comment) {
        this.comment = Comment;
    }                
    
    public void addComment(){        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        String logonUser=(String) httpSession.getAttribute("username");  
        
        Cases cases=(Cases) httpSession.getAttribute("cases"); 
        appManager.addComment(cases.getCaseID(),comment,logonUser);
        //cases.setStatus(comment);
        appManager.updateCase(cases);
        List<Comments> comments =appManager.getComments(cases.getCaseID());
        httpSession.setAttribute("comments", comments);         
    }
    
    
    
    
    
    
    
    
    
    
    public void fileUpload() 
    {
         System.out.println("File:"+file);
         
         FacesContext context = FacesContext.getCurrentInstance();  
         HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
         
         HttpSession httpSession = request.getSession(false);
         
         Documents doc=new Documents();
         doc.setCaseID(((Cases)httpSession.getAttribute("cases")));
         doc.setDocumentname(file.getFileName());
         doc.setUploaduser(user.getUserID());
         doc.setDescription(documentDescription);
         
         appManager.addDocument(doc);
         Cases case1= appManager.getCases(((Cases)httpSession.getAttribute("cases")).getCaseID());
         
         context.getExternalContext().getSessionMap().put("cases", case1);
         ArrayList<Documents> myDocuments = new ArrayList<Documents>(case1.getDocumentsCollection());
         httpSession.setAttribute("documents",  myDocuments);
         System.out.println("documents"+myDocuments.size());
         
         //ADDED FOR SAVING FILE ON SERVER
        ExternalContext extContext=FacesContext.getCurrentInstance().getExternalContext();
        File result = new File("//C://TEMP//"+file.getFileName());
        //System.out.println(extContext.getRealPath("//WEB-INF//"+file.getFileName()));
        try {
        FileOutputStream fileOutputStream = new FileOutputStream(result);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bulk;
        InputStream inputStream = file.getInputstream();
        while (true) {
        bulk = inputStream.read(buffer);
        if (bulk < 0) {
        break;
        }
        fileOutputStream.write(buffer, 0, bulk);
        fileOutputStream.flush();
        } 
        fileOutputStream.close();
        inputStream.close();
        FacesMessage msgs = new FacesMessage("Succesful", file.getFileName()
        + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msgs); 
        } catch (IOException e) {
        e.printStackTrace();
        FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR,
        "The files were not uploaded!", "");
        FacesContext.getCurrentInstance().addMessage(null, error);
        }
        //SAVING FILE ENDS HERE
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    
    
    
    
    
    
}
