/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.beans;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.core.Log;
import com.cm.entities.Cases;
import com.cm.entities.Users;
import com.cm.entities.Activities;
import com.cm.entities.Documents;
import com.cm.util.Email;
import com.cm.util.SendEmail;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class NewCaseMBean implements Serializable{

    
    String casename,description,practicearea,documentDescription;
    private UploadedFile file;
    private List<UploadedFile> files=new ArrayList<UploadedFile>();
    AppManager manager=new AppManager(new AdminRepositoryImpl());      
    private static final int BUFFER_SIZE = 6124;  
    Users user;
    Cases ccase;
    
    /**
     * Creates a new instance of NewCaseMBean
     */
    public NewCaseMBean() {
    }
    
        @PostConstruct
    public void init() 
    {
        
            FacesContext context = FacesContext.getCurrentInstance();  
             HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
             HttpSession httpSession = request.getSession(false);          
             String username=(String) httpSession.getAttribute("username");  
             user=manager.getUser(username);
        
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getPracticearea() {
        return practicearea;
    }

    public void setPracticearea(String practicearea) {
        this.practicearea = practicearea;
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
         
         manager.addDocument(doc);
         Cases case1= manager.getCases(((Cases)httpSession.getAttribute("cases")).getCaseID());
         
         context.getExternalContext().getSessionMap().put("cases", case1);
         context.getExternalContext().getSessionMap().put("documents", new ArrayList<Documents>(case1.getDocumentsCollection()));
//         httpSession.setAttribute("cases", case1);  
//         httpSession.setAttribute("documents", new ArrayList<Documents>(case1.getDocumentsCollection()));  
         
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
        manager.audit(user.getUsername(), "Successful File upload "+file.getFileName(),"IPADDRESS");
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
    
    
    public void upload() 
    {
                
        
        if(practicearea!=null&&casename!=null&&description!=null) {                                 
            
            try 
            {                                                                                             
            
            Cases case1= new Cases();
            case1.setCaseDate(new Date());
            case1.setCaseOwner(user.getUserID());
            case1.setPracticeArea(practicearea);
            case1.setCaseName(casename);
            case1.setCaseRef("CM-UBN"+case1.getCaseDate().getMonth()+"-"+case1.getCaseDate().getDay()+"-"+case1.getCaseDate().getMinutes());
            case1.setDescription(description);
            case1.setStatus("Open");                                            
            
            manager.addCase(case1);
            manager.addActivity(new Activities("You Created a new case:"+case1.getCaseName(), user, case1));
            
            Email email=new Email();
            email.setEmailAddress(user.getEmail());
            email.setSubject("Your have create a new Case :" +case1.getCaseName());
            email.setMessage("Case :" +case1.getCaseName()+"\n"+case1.getPracticeArea()+"\n"+case1.getStatus()+"\n "+Log.APPURL);
            new SendEmail().sendSimpleMail(email);
            
            manager.audit(user.getUsername(), "Case created "+casename,"IPADDRESS");
            FacesMessage message = new FacesMessage("Succesful", casename+" Case Has been Created.");
            
            casename="";
            description="";practicearea="";
            file=null;
            
            
            
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            
            } catch (Exception ex) {
                FacesMessage message = new FacesMessage("FAILED", ex.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, message);
                Log.l.errorLog.error(message.getSummary());                
            }
        }        
        else{                
                FacesMessage message = new FacesMessage("FAILED", "File is null");
                FacesContext.getCurrentInstance().addMessage(null, message);
                Log.l.errorLog.error("File is null");
        }
        
    }

    public List<UploadedFile> getFiles() {
        return files;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }
    
    
    public void deleteFile(String docName){
     Documents doc=manager.findDocument(docName);
     if(doc!=null){
         manager.deleteDoc(doc);
         manager.audit(user.getUsername(), "Document deleted "+docName,"IPADDRESS");
     }
    }
    
    
}
