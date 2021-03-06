/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extractkubrik;

import com.itextpdf.text.pdf.*;
import java.io.*;

/**
 *
 * @author Jenia
 */
public class MultimediaExtractor {
 public static final String PATH = "/home/reshet/Downloads/db2_2_2010.pdf";
     public void extractAttachments(String src) throws IOException {

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
                            FileOutputStream fos = new FileOutputStream(String.format(PATH, fs.getAsString(name).toString()));
                            fos.write(PdfReader.getStreamBytes((PRStream) refs.getAsStream(name)));
                            fos.flush();
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
                          
                                       FileOutputStream fos = new FileOutputStream(String.format(PATH, prs.getAsString(name3).toString()));
    
                                       PRStream prstream=(PRStream) prs2.getAsStream(name3);
                                       byte [] bytearray=PdfReader.getStreamBytes(prstream);
                                       
                                       fos.write(bytearray);
                                        fos.flush();
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
    }
}
