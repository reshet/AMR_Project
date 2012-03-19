/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import entity.Page;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author reshet
 */
@Stateless
public class PageFacade extends AbstractFacade<Page> implements PageFacadeLocal {
    @PersistenceContext(unitName = "AMR_FacadePU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public PageFacade() {
        super(Page.class);
    }
    
}
