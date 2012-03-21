/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
            File                f        = new File("/home/reshet/Downloads/34333/HPL.pdf");
            int                 length   = 0;
            //ServletOutputStream op       = response.getOutputStream();
            ServletContext      context  = getServletConfig().getServletContext();
            String              mimetype = context.getMimeType("/home/reshet/Downloads/34333/HPL.pdf");
            //
            //  Set the response and go!
            //
            //
            response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
            response.setContentLength( (int)f.length() );
            response.setHeader( "Content-Disposition", "attachment; filename=\"" + "original_filename" + "\"" );
            //
            //  Stream to the requester.
            //
            byte[] bbuf = new byte[4048];
            DataInputStream in = new DataInputStream(new FileInputStream(f));
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
