/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.beans;

import com.mplatrforma.amr.entity.UserAccount;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author reshet
 */
@Stateless
public class UserAccountFacade extends AbstractFacade<UserAccount> {
    @PersistenceContext(unitName = "DatabankEnterprise-warPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAccountFacade() {
        super(UserAccount.class);
    }
    
}
