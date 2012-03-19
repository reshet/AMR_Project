/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Book;
import entity.Page;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Kate Nezdoly
 */
public class DataAccessObject {
    
    private EntityManager em;
    private EntityManagerFactory emf;
    
    public DataAccessObject() {
        emf = Persistence.createEntityManagerFactory("BoomPU");
        em = emf.createEntityManager();
    }
    
    public Page createPage(Page page){
        em.getTransaction().begin();
        em.persist(page);
        em.getTransaction().commit();
        return page;
        
    }
    
    public Page retrievePage(int pageId){
        em.getTransaction().begin();
        Page page = em.find(Page.class, pageId);
        em.getTransaction().commit();
        return page;
    }
    
    public Book createBook(Book book){
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        return book;
        
    }
    
    public Book retrieveBook(int bookId){
        Book book = em.find(Book.class, bookId);
        return book;
    }
    
    public void close() {
        em.close();
        emf.close();
    }
    
    
}
