/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cm.core;

/**
 *
 * @author PHI
 */
public interface UnitOfWorkSession
{
    void commit();
    void rollback();
    boolean isActive();
}
