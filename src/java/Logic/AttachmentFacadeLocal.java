/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import entity.Attachment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author reshet
 */
@Local
public interface AttachmentFacadeLocal {

    void create(Attachment attachment);

    void edit(Attachment attachment);

    void remove(Attachment attachment);

    Attachment find(Object id);

    List<Attachment> findAll();

    List<Attachment> findRange(int[] range);

    int count();
    
}
