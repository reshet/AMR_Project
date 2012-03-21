/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import entity.Book;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author reshet
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> implements BookFacadeLocal {
    @PersistenceContext(unitName = "AMR_FacadePU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
}
