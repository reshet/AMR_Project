/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import Logic.service.CustomerFacadeREST;
import com.itextpdf.text.pdf.*;
import it.sauronsoftware.jave.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpedal.PdfDecoder;


/**
 *
 * @author Jenia
 */
public class MultimediaExtractor {
     private String PATH = "/home/reshet/Downloads/34333/%s";
     
     
      private void pedalParse()
    {
        try {
           BufferedImage bufferedImage = null; 
            final PdfDecoder pdfDocument = new PdfDecoder(false); 
            pdfDocument.setExtractionMode(PdfDecoder.FINALIMAGES, 100, 1); 
            pdfDocument.openPdfFile("<documentPath>"); 
            try { 
            final int pageCount = pdfDocument.getPageCount(); 
            for (int i = 1; i < pageCount+1; i++) { 
            bufferedImage = pdfDocument.getPageAsImage(i); 
            //watermark(bufferedImage, getWaterMarkText(SecuritySessionAccessor.getLogonUser(request))); 
            } 
            final long completed = System.currentTimeMillis(); 
            //return new DataRequestStringResult(pageCount + " pages Fetch took " + (afterFetch - start) + "ms, Render took " + (completed - afterFetch) + "ms, "); 
            } finally { 
            if (bufferedImage != null) { 
            bufferedImage.flush(); 
            } 
            pdfDocument.dispose(); 
    } 
        } catch (org.jpedal.exception.PdfException ex) {
            Logger.getLogger(MultimediaExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
     public void extractAttachments(String src) {
        try {
           // PATH = src;
          PdfReader reader = new PdfReader(src);


           PdfArray array;
           //Pdf
           PdfDictionary annot;
           PdfDictionary fs;
           PdfDictionary refs;
           PdfArray mine;
           //PdfArray refs2;
           byte[] b;

           for (int i = 1; i <= reader.getNumberOfPages(); i++) {
               array = reader.getPageN(i).getAsArray(PdfName.ANNOTS);
               if (array == null) {
                   continue;
               }

               for (int j = 0; j < array.size(); j++) {
                   annot = array.getAsDict(j);
                   if (PdfName.FILEATTACHMENT.equals(annot.getAsName(PdfName.SUBTYPE))) {

                       fs = annot.getAsDict(PdfName.FS);
                       refs = fs.getAsDict(PdfName.EF);

                       for (PdfName name : refs.getKeys()) {
                           try 
                           {
                               System.out.println("First cycel iter");
                               FileOutputStream fos = new FileOutputStream(String.format(PATH, fs.getAsString(name).toString()));
                               fos.write(PdfReader.getStreamBytes((PRStream) refs.getAsStream(name)));
                               fos.flush();
                               fos.close();
                           }finally
                           {}
                       }
                   }

                   if (PdfName.RICHMEDIA.equals(annot.getAsName(PdfName.SUBTYPE))) {
                       fs = annot.getAsDict(PdfName.RICHMEDIACONTENT);
                       refs = fs.getAsDict(PdfName.ASSETS);

                       for (PdfName name : refs.getKeys()) {
                           mine = refs.getAsArray(name);
                           int key = 0;
                           String result = "";
                           for (PdfObject name2 : mine) {

                               if (name2.type() == PdfName.INDIRECT) {
                                   PdfDictionary prs = (PdfDictionary) mine.getDirectObject(key);
                                   PdfDictionary prs2 = prs.getAsDict(PdfName.EF);
                                   for (PdfName name3 : prs2.getKeys()) {
                                       try {
                                           System.out.println("Second cycel iter");
                                           
                                          FileOutputStream fos = new FileOutputStream(String.format(PATH, prs.getAsString(name3).toString()));
       
                                          PRStream prstream=(PRStream) prs2.getAsStream(name3);
                                          byte [] bytearray=PdfReader.getStreamBytes(prstream);
                                          
                                         // doConvert(String.format(PATH, prs.getAsString(name3).toString()));
                                          fos.write(bytearray);
                                           fos.flush();
                                           fos.close();
                                       }
                                       finally{}
                                   }
                               }
                               key++;
                           }
                       }
                   }
               }
           }
        } catch (IOException ex) {
            Logger.getLogger(MultimediaExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
