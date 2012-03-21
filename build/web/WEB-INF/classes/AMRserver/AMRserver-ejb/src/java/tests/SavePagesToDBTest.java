/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.DataAccessObject;
import entity.Book;
import entity.Page;
import java.util.List;
import utilities.*;


/**
 *
 * @author Kate Nezdoly
 */
public class SavePagesToDBTest {
    
    public static void main(String args[]){
        
        
        Book book = new Book("Opacha",null,null);
        book.setId(3);
        
        DataAccessObject dao = new DataAccessObject();
        dao.createBook(book);
      
        
        SplitIntoImages splitter = new SplitIntoImages();
        List<SerializablePNG> pages = splitter.split("C:/att/db2.pdf");
        for(int i = 0; i < pages.size(); i++){
            Page page = new Page(pages.get(i),book,null);
            page.setId(i+1);
            dao.createPage(page);
        }

        dao.close();    
   
        
    }
    
}
