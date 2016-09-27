/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cm.filters;

import com.cm.core.AdminRepositoryImpl;
import com.cm.core.AppManager;
import com.cm.dto.UserType;
import com.cm.entities.Users;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
public class AccessFilter implements Filter {
  
        
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    
    public AccessFilter() {
    }    
        
    /**     
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing     
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try {
            
        AppManager manager=new AppManager(new AdminRepositoryImpl());
        HttpSession session = ((HttpServletRequest)request).getSession(false);
                
        String servletName = ((HttpServletRequest)request).getServletPath();
        List<String> list=(List<String>)((HttpServletRequest)request).getSession().getAttribute("resourceInfos");
        List<String> allResourceInfos=(List<String>)((HttpServletRequest)request).getSession().getAttribute("allResourceInfos");
        String role=(String)((HttpServletRequest)request).getSession().getAttribute("rrole");
        
        if(list!=null&&role!=null)
        {
            if(allResourceInfos.contains(servletName.substring(1)))
            {
              if(!role.equals(UserType.Admin.toString()))
              {
                
              }
           }
         }
                        
        
        if(session == null || session.getAttribute("username") == null)
        {
            ((HttpServletRequest)request).getRequestDispatcher("/").forward(request, response);
            return;
        }
        
        
        Users adminUser= manager.getUser((String)session.getAttribute("username"));
        if(adminUser.getStatus()==2)
        {
                ((HttpServletRequest)request).getRequestDispatcher("/error").forward(request, response);
                return;
         }
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

//        if (!req.getRequestURI().startsWith(req.getContextPath() + ResourceHandler.WEBAPP_RESOURCES_DIRECTORY_PARAM_NAME)) { // Skip JSF resources (CSS/JS/Images/etc)            
//            res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//            res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//            res.setDateHeader("Expires", 0); // Proxies.
//        }
                                    
            chain.doFilter(request, response);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    /**
     * Destroy method for this filter
     */
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
    }
 

    
}
