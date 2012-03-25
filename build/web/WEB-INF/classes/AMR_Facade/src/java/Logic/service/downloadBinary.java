/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.service;

import entity.Book;
import entity.Page;
import java.io.*;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author reshet
 */
public class downloadBinary extends HttpServlet {

     public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @PersistenceContext(unitName = "AMR_FacadePU")
    private EntityManager em;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try {
            String id = request.getParameter("id");
            String type = request.getParameter("type");
            
            //File                f        = new File("/home/reshet/Downloads/34333/HPL.pdf");
            int                 length   = 0;
            byte [] binary = null;
            String extension = "";
            if("pdf".equals(type))
            {
                Book b = em.find(Book.class, Integer.parseInt(id));
                binary = b.getPdf();
                extension="pdf";
            }
            if("cover".equals(type))
            {
                Book b = em.find(Book.class, Integer.parseInt(id));
                binary = b.getGlance();
                extension = "png";
            }
            if("zip".equals(type))
            {
                Book b = em.find(Book.class, Integer.parseInt(id));
                Collection<Page> pages = b.getPageCollection();
                binary = zipFiles(pages);
                extension = "zip";
            }
            
            
            //ServletOutputStream op       = response.getOutputStream();
            ServletContext      context  = getServletConfig().getServletContext();
            
            
            //String              mimetype = context.getMimeType("/home/reshet/Downloads/34333/HPL.pdf");
            //
            //  Set the response and go!
            //
            //
            response.setContentType("application/octet-stream");
            //response.setContentLength( (int)f.length() );
             response.setContentLength( (int)binary.length );
            response.setHeader( "Content-Disposition", "attachment; filename=\"" + "original_filename." + extension+"\"" );
            //
            //  Stream to the requester.
            //
            byte[] bbuf = new byte[4048];
           
            
            
            //DataInputStream in = new DataInputStream(new FileInputStream(f));
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(binary));
            ServletOutputStream op       = response.getOutputStream();
            while ((in != null) && ((length = in.read(bbuf)) != -1))
            {
                op.write(bbuf,0,length);
            }

            in.close();
            op.flush();
            op.close();        
        } finally {            
            //out.close();
        }
    }
    
    private byte[] zipFiles(Collection<Page> pages) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        //byte bytes[] = new byte[2048];

        int i = 0;
        for (Page pg : pages) {
            //FileInputStream fis = new FileInputStream(String.valueOf(i)+".png");
//            ByteArrayOutputStream bais = new ByteArrayOutputStream();
//            bais.write(pg.getContent());
//            
//            byte [] buff = new byte[4096];
//            ByteArrayInputStream bin = new ByteArrayInputStream(buff);
            
            //BufferedInputStream bis = new BufferedInputStream(bais);

            zos.putNextEntry(new ZipEntry(String.valueOf(i)+".png"));

            //int bytesRead;
            //while ((bytesRead = bis.read(bytes)) != -1) {
                //zos.write(bytes, 0, bytesRead);
            //}
            zos.write(pg.getContent());
            zos.closeEntry();
            i++;
            //bis.close();
            //bais.close();
            //fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
