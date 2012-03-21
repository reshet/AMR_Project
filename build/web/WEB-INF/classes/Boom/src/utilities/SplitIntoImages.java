/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.image.*;
import java.io.*;
import java.util.*;
import org.icepdf.core.exceptions.*;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

/**
 *
 * @author Kate Nezdoly
 */
public class SplitIntoImages {

    public List<SerializablePNG> split(String filepath) {
        Document document = new Document();
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

        float scale = 1f;
        float rotation = 0f;

        final List<SerializablePNG> pages = new ArrayList();
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            BufferedImage image = (BufferedImage) document.getPageImage(
                    i, GraphicsRenderingHints.PRINT, Page.BOUNDARY_CROPBOX, rotation, scale);
            
            pages.add(getSerializablePNG(image));
            image.flush();
        }
        File file = new File(filepath);
        document.dispose();
        return Collections.unmodifiableList(pages);

    }

    private SerializablePNG getSerializablePNG(BufferedImage image) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        int[] rgbs = new int[w * h];
        image.getRGB(0, 0, w, h, rgbs, 0, w);
        return new SerializablePNG(w, h, image.getType(), rgbs);
    }
}