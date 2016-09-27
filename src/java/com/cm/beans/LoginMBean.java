/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.beans;



import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.dto.Response;
import com.cm.dto.UserType;
import com.cm.dto.Util;
import com.cm.entities.Users;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
@ManagedBean(name = "loginMBean")
@SessionScoped
public class LoginMBean implements Serializable {

    String userid;
    String password;
    String rrole;
    String clientId;
    String LastLogonDate;
    String currentPW, newPW;    
    Users au;    
    Boolean admin;

    public LoginMBean() {
        if (au == null) {
            au = new Users();
        }
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCurrentPW() {
        return currentPW;
    }

    public void setCurrentPW(String currentPW) {
        this.currentPW = currentPW;
    }

    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public String getLastLogonDate() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        if (params.get("lastLogonDate") == null) {
            return new Date().toString();
        }
        return params.get("lastLogonDate").toString();
    }

    public void setLastLogonDate(String LastLogonDate) {
        this.LastLogonDate = LastLogonDate;
    }


    public String login2FA() {
        FacesMessage msg;// = null;
        try {                
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();                

        AppManager manager=new AppManager(new AdminRepositoryImpl());                                                               
        Response result = manager.login(userid,password);
        if (result.getStatus()) {                    
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", userid);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", userid);          
            Users adminUser= manager.getUser(userid);
            session.setAttribute("rrole", adminUser.getRole());
            rrole=adminUser.getRole();
            
            manager.audit(userid, "Successful logon","IPADDRESS");
            
            return "pretty:home";
           }else 
          {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", result.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
          }
        }catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void redirector(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void forwarder(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().dispatch(url);
        } catch (Exception ex) {
            System.out.println("Exception in trying to forward to [" + url + "] due to " + ex.getMessage());
        }
    }


    public void logout() {
        HttpServletRequest hs = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String loginPage = hs.getContextPath() + "/faces/index.xhtml";
        redirector(loginPage);
    }

    public void idleListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Your session has expired", "You have been idle for at least 5 minutes"));
        HttpServletRequest hs = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String loginPage = hs.getContextPath() + "/faces/logout.xhtml";
        redirector(loginPage);
    }

    public void activeListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Welcome Back", "That's a long coffee break!"));
    }

    public String userInitiatelogout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        String userSessionId = (String) params.get("userid");
        String sessionId = session.getId();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        sessionId = null;
        if (userSessionId != null) {
            userSessionId = null;
        }
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout Request", "User successfully logout");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return "pretty:logout";
    }

    public String homePage() {
        return "pretty:home";
    }
    public String pwResetPage() {
        return "pretty:pwreset";
    }
    
    
    
    
//    public String login()
//    {                      
//            FacesMessage msg;// = null;
//            HttpSession ses = Util.getSession();            
//            
//        try {
//            
//            AppManager manager=new AppManager(new AdminRepositoryImpl());
//            Response resp=manager.login(userid, password);
//         
//            if(resp.getStatus()){
//              
//                            
//              Users adminUser= manager.getUser(userid);
//              ses.setAttribute("rrole", adminUser.getRole());
//              ses.setAttribute("username", userid);                                 
//              
//              return "pretty:home";
//              
//            }
//      
//           else 
//          {
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", resp.getDescription());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            return null;
//          }
//            
//        } catch (Exception ex) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", ex.getMessage());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }        
//    }

    
    
    public boolean isAdmin(){                        
       return rrole.equals(UserType.Admin.toString());
    }
    
    public boolean isBranchUser(){                 
       return rrole.equals(UserType.BranchUser.toString());
    }
    
     public boolean isLawyer(){                         
       return rrole.equals(UserType.ExternalLawyer.toString());
    }
    
      public boolean isLegalDept(){        
        return rrole.equals(UserType.LegalDept.toString());
      }

    public String getRrole() {
        return rrole;
    }

    public void setRrole(String rrole) {
        this.rrole = rrole;
    }
    
    
    
    
}
