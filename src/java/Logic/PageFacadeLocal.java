/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import entity.Page;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author reshet
 */
@Local
public interface PageFacadeLocal {

    void create(Page page);

    void edit(Page page);

    void remove(Page page);

    Page find(Object id);

    List<Page> findAll();

    List<Page> findRange(int[] range);

    int count();
    
}
