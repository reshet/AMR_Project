/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import dao.DataAccessObject;
import entity.Page;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author  Kate Nezdoly
 */
public class RetrievePagesFromDB {
    
//    public static void main(String args[]) throws IOException {
//        
//        DataAccessObject dao = new DataAccessObject();
//        for (int i = 1; i < 28; i++) {
//            Page page = dao.retrievePage(i);
////            BufferedImage image = new BufferedImage(page.getContent().getW(),
////                    page.getContent().getH(), page.getContent().getImageType());
////            image.setRGB(0, 0, page.getContent().getW(), page.getContent().getH(), page.getContent().getPixels(),0,page.getContent().getW());
////            File file = new File("C:/att/images/imageCapture1_" + i + ".png");
////            ImageIO.write(image, "png", file);
//        }
//        
//        dao.close();
//        
 //   }
}
