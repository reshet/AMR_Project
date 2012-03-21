/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.beans;

import com.mplatrforma.amr.entity.Someresearch;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author reshet
 */
@Stateless
public class SomeresearchFacade extends AbstractFacade<Someresearch> {
    @PersistenceContext(unitName = "DatabankEnterprise-warPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public SomeresearchFacade() {
        super(Someresearch.class);
    }
    
}
