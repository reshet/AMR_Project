/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.service;

import dao.DataAccessObject;
import entity.Book;
import entity.Customer;
import entity.Page;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
    @Produces("text/plain")
    public String doLogin(@PathParam("username") String username,@PathParam("password") String password) {
        //.return approveUser(username,password);
        parseBook();
        return "approved";
    }
    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private String approveUser(String username,String password) 
    {
        try {
            String data = URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode("kostya.personal@gmail.com", "UTF-8");
             data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("kassiopeya", "UTF-8");
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
             
             //processAccountResources(total_resp);
         return "Approved: "+total_resp;
        } catch (MalformedURLException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CustomerFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Disapproved";
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
        parseBook();
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
            //splitter.split(em,book,"/home/reshet/Downloads/34333/server.pdf");
//            for(int i = 0; i < pages.size(); i++){
//                Page page = new Page(pages.get(i),book,null);
//                //page.setId(i+1);
//                em.persist(page);
//            }
            em.persist(book);
            //dao.close();    
            //parseBookMultimedia();
        }
        catch(Exception ex)
        {
            int b = 2;
        }

    }
    private void parseBookMultimedia()
    {
            MultimediaExtractor extractor = new MultimediaExtractor();
            String sss = "/home/reshet/Downloads/34333/db2_2_2010.pdf";
            extractor.extractAttachments(sss);
    }
}
