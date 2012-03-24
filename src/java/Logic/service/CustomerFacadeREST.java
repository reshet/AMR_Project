/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.service;

import com.itextpdf.text.pdf.*;
import dao.DataAccessObject;
import dtos.AttachmentDTO;
import dtos.BookDTO;
import dtos.PageDTO;
import entity.Attachment;
import entity.Book;
import entity.Customer;
import entity.Page;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.commons.lang3.StringEscapeUtils;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;
import org.json.JSONArray;
import org.json.JSONObject;
import parser.MultimediaExtractor;
import utilities.SerializablePNG;
import utilities.SplitIntoImages;

/**
 *
 * @author reshet
 */
@Stateless
@Path("entity.customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {
    @PersistenceContext(unitName = "AMR_FacadePU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Customer entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Customer entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Customer find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Customer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @GET
    @Path("login")
    @Produces({"application/xml", "application/json"})
    public Customer doLogin(@PathParam("username") String username,@PathParam("password") String password) {
        username = "kostya.personal@gmail.com";
        password = "kassiopeya";
        Customer c = approveUser(username,password);
        //parseBookMultimedia();
        return c;
    }
    
    
    
    @GET
    @Path("booklist")
    @Produces({"application/json"})
    public List<BookDTO> getBooksList(@PathParam("username") String username,@PathParam("password") String password) {
        username = "kostya.personal@gmail.com";
        password = "kassiopeya";
        Customer c = approveUser(username,password);
        List<Book> books = (List<Book>) c.getBookCollection();
        List<BookDTO> bdtos = new ArrayList<BookDTO>();
        for(Book b:books)
        {
            List<PageDTO> pgs = new ArrayList<PageDTO>();
            Collection<Page> ps = b.getPageCollection();
            for(Page p: ps)
            {
                List<AttachmentDTO> ats = new ArrayList<AttachmentDTO>();
                Collection<Attachment> as = p.getAttachmentCollection();
                for(Attachment a: as)
                {
                    ats.add(new AttachmentDTO(a.getId(), a.getName()));
                }
                PageDTO pdto = new PageDTO(p.getId(), ats);
                pgs.add(pdto);
            }
            BookDTO dt = new BookDTO(b.getId(),b.getName(), pgs);
            bdtos.add(dt);
        }
        
        return bdtos;
    }
    
    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private Customer approveUser(String username,String password) 
    {
        try {
            String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
             data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
             data += "&" + URLEncoder.encode("language_id", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");

             // Send data
             URL url = new URL("http://store.kassiopeya.com/ios.php");
             URLConnection conn = url.openConnection();
             conn.setDoOutput(true);
             OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
             wr.write(data);
             wr.flush();

             // Get the response
             BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             String total_resp="";
             String line;
             while ((line = rd.readLine()) != null) {
                 total_resp+=line;
                 // Process line...
             }
             wr.close();
             rd.close();
             
             
              Customer c;
              c = Customer.getCustomer(username, password, em);
              if (c == null)c = new Customer(username, password);
                
                
             testUser(username, password);
             //processAccountResources(total_resp);
         return c;
        } catch (MalformedURLException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private void processAccountResources(String respJSON)
    {
        try {
            JSONArray arr = new JSONArray(respJSON);
            for(int i =0;i < arr.length();i++)
            {
                JSONObject obj = (JSONObject) arr.get(i);
                String prodName = obj.getString("PRODUCTS_NAME");
                String s = StringEscapeUtils.unescapeJava(prodName);
                //obj.
            }
            //obj.
        } catch (ParseException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void parseBook()
    {
        try {
            Book book = new Book("Opacha",null,null);
            //book.setId(3);
            //em.persist(book);
            //DataAccessObject dao = new DataAccessObject();
            //dao.createBook(book);


            SplitIntoImages splitter = new SplitIntoImages();
            List<SerializablePNG> pages = splitter.split(em,book,"/home/reshet/Downloads/34333/server.pdf");
            for(int i = 0; i < pages.size(); i++){
                Page page = new Page(pages.get(i),book,null);
                page.setId(i+1);
                em.persist(page);
            }
            em.persist(book);
            //dao.close();    
            //parseBookMultimedia();
        }
        catch(Exception ex)
        {
            int b = 2;
            ex.printStackTrace();
        }

    }
    private void parseBookMultimedia()
    {
            MultimediaExtractor extractor = new MultimediaExtractor();
            String sss = "/home/reshet/Downloads/34333/db2_2_2010.pdf";
            extractor.extractAttachments(sss);
    }
    
    private void testUser(String username,String password)
    {
        Customer c;
        c = Customer.getCustomer(username, password, em);
        if (c == null)c = new Customer(username, password);
        
        List<Book> books = (List<Book>) c.getBookCollection();
        if(books == null || books.isEmpty())
        {
            books = new ArrayList<Book>();
            books.add(doParsePDF(c,"/home/reshet/Downloads/34333/db2_2_2010.pdf"));
            books.add(doParsePDF(c,"/home/reshet/Downloads/34333/HPL.pdf"));
            books.add(doParsePDF(c,"/home/reshet/Downloads/34333/server.pdf"));
            c.setBookCollection(books);
        }
       em.persist(c);
    }
    private byte[] getCover()
    {
        try {
            BufferedImage img = ImageIO.read(new File("/home/reshet/Downloads/34333/cover.jpg"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img,"JPG" , baos);
            byte[] bytesOut = baos.toByteArray();
            return bytesOut;
        } catch (IOException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private byte[] getPdfBytes(String path)
    {
            File f = new File(path);
            FileInputStream fin = null;
            FileChannel ch = null;
            try {
                fin = new FileInputStream(f);
                ch = fin.getChannel();
                int size = (int) ch.size();
                MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
                byte[] bytes = new byte[size];
                buf.get(bytes);
                return bytes;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                    if (ch != null) {
                        ch.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
    }
    private Book doParsePDF(Customer c,String path)
    {
       ArrayList<Page> pages = new ArrayList<Page>();
       Book book = new Book(path,c,pages);
       book.setGlance(getCover());
       book.setPdf(getPdfBytes(path));
       
               try {
           // PATH = src;
          PdfReader reader = new PdfReader(path);


           PdfArray array;
           //Pdf
           PdfDictionary annot;
           PdfDictionary fs;
           PdfDictionary refs;
           PdfArray mine;
           //PdfArray refs2;
           byte[] b;
           for (int i = 1; i <= reader.getNumberOfPages(); i++)
           {
               ArrayList<Attachment> attachments = new ArrayList<Attachment>();
               Page p = new Page(null, book, attachments);
               em.persist(p);
               pages.add(p);
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
                               
                               byte [] bytearray = PdfReader.getStreamBytes((PRStream) refs.getAsStream(name));
                               Attachment at = new Attachment(fs.getAsString(name).toString(),bytearray, p);
                               attachments.add(at);
                               //FileOutputStream fos = new FileOutputStream(String.format(PATH, fs.getAsString(name).toString()));
                               //fos.write(PdfReader.getStreamBytes((PRStream) refs.getAsStream(name)));
                               ///fos.flush();
                              // fos.close();
                               
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
                             
                                          //FileOutputStream fos = new FileOutputStream(String.format(PATH, prs.getAsString(name3).toString()));
       
                                          PRStream prstream=(PRStream) prs2.getAsStream(name3);
                                          byte [] bytearray=PdfReader.getStreamBytes(prstream);
                                          
                                           //byte [] bytearray = PdfReader.getStreamBytes((PRStream) refs.getAsStream(name));
                                            Attachment at = new Attachment(prs.getAsString(name3).toString(),bytearray, p);
                                            attachments.add(at);
                                          //fos.write(bytearray);
                                          // fos.flush();
                                          // fos.close();
                                       }
                                       finally{}
                                   }
                               }
                               key++;
                           }
                       }
                   }
               }
               p.setAttachmentCollection(attachments);
               em.persist(p);
               pages.remove(i-1);
               pages.add(p);
           }
        } catch (IOException ex) {
            Logger.getLogger(MultimediaExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
       book.setPageCollection(pages);
       em.persist(book);
       return book;
    }
}
