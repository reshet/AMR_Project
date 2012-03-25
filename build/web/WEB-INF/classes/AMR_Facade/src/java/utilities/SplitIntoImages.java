/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import entity.Book;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import org.icepdf.core.exceptions.*;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

/**
 *
 * @author Kate Nezdoly
 */
public class SplitIntoImages {

    public List<SerializablePNG> split(EntityManager em,Book book, String filepath) {
        Document document = new Document();
        final List<SerializablePNG> pages = new ArrayList<SerializablePNG>();
        try {
            document.setFile(filepath);
       

        float scale = 1f;
        float rotation = 0f;

      
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(
                    i, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX, rotation, scale);

            pages.add(getSerializablePNG(image));
             File file = new File("/home/reshet/Downloads/imgs/imageCapture1_" + i + ".png");
            ImageIO.write(image, "png", file);
            ByteArrayOutputStream outp = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outp);
            byte [] im_bytes = outp.toByteArray();
            //SerializablePNG png = getSerializablePNG(image);
            //entity.Page page = new entity.Page(png, book, null);
            
            
            //TO-DO HERE FAKE! Closed EMF!!! 
            //em.persist(page);
            image.flush();
        }
          return Collections.unmodifiableList(pages);
         } catch (PDFException ex) {
            System.out.println("Error parsing PDF document " + ex);
        } catch (PDFSecurityException ex) {
            System.out.println("Error encryption not supported " + ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error file not found " + ex);
        } catch (IOException ex) {
            System.out.println("Error IOException " + ex);
        }
        finally
        {
           document.dispose();
        }
        return null;

    }

    private SerializablePNG getSerializablePNG(BufferedImage image) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        int[] rgbs = new int[w * h];
        image.getRGB(0, 0, w, h, rgbs, 0, w);
        return new SerializablePNG(w, h, image.getType(), rgbs);
    }
}