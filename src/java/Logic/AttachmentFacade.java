/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import entity.Attachment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author reshet
 */
@Stateless
public class AttachmentFacade extends AbstractFacade<Attachment> implements AttachmentFacadeLocal {
    @PersistenceContext(unitName = "AMR_FacadePU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AttachmentFacade() {
        super(Attachment.class);
    }
    
}
