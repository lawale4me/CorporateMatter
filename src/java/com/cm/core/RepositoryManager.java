/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PHI
 */
public class RepositoryManager
{
    private static EntityManagerFactory factory;    
    static 
    {
        factory = Persistence.createEntityManagerFactory("CorporateMatterPU");
    }
    
    public static EntityManager getManager()
    {
        return factory.createEntityManager();
    }
}
