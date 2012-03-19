/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;
/**
 *
 * @author Jenia
 */
public class SplitIntoImages {

    public SplitIntoImages() {
    }

      // open the file
    
 public void split (String filepath){
         Document document = new Document();
          
          //ls = new ArrayList<Integer>();
         // List<File> list=new List<>();
      try {
         document.setFile(filepath);
      } catch (PDFException ex) {
         System.out.println("Error parsing PDF document " + ex);
      } catch (PDFSecurityException ex) {
         System.out.println("Error encryption not supported " + ex);
      } catch (FileNotFoundException ex) {
         System.out.println("Error file not found " + ex);
      } catch (IOException ex) {
         System.out.println("Error IOException " + ex);
      }

      // save page captures to file.
      float scale = 1.2f;
      float rotation = 0f;

      // Paint each pages content to an image and
      // write the image to file
      //List <RenderedImage> l=new ArrayList<>();
      
      for (int i = 0; i < document.getNumberOfPages(); i++) {
         BufferedImage image = (BufferedImage) document.getPageImage(
             i, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX, rotation, scale);
         RenderedImage rendImage = image;
         try {
            System.out.println(" capturing page " + i);
            File file = new File("/home/reshet/Downloads/imgs/imageCapture1_" + i + ".png");
            ImageIO.write(image, "png", file);
            
         } catch (IOException e) {
         }
         image.flush();
      }
      File file=new File(filepath);
      // clean up resources
      document.dispose();  
          
    }
  
}
