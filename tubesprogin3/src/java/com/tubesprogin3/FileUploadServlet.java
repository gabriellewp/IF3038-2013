/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tubesprogin3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Gabrielle
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"},loadOnStartup=1)
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getCanonicalName());

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        final Part filePart = request.getPart("attachment_upload");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();


        try {
            out = new FileOutputStream(new File(getServletContext().getRealPath("/") + "img/"
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
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
        //processRequest(request, response);
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
       // response.setContentType("text/html;charset=UTF-8");
        // Create path components to save the file
   /* final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);*/
       // System.out.println("test");
        final Part filePart = request.getPart("attachment_file");

        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
//        final PrintWriter writer = response.getWriter();


        try {
            File filePath = new File(getServletContext().getRealPath("/") + "file/"
                    + fileName);
//        System.out.println(filePath);
            out = new FileOutputStream(filePath);
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
            //    out.write(bytes, 0, read);
            }
//        System.out.println("beres");
        } catch (FileNotFoundException fne) {
          //  writer.println("You either did not specify a file to upload or are "
               //     + "trying to upload a file to a protected or nonexistent "
              //      + "location.");
         //   writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
//            if (writer != null) {
//                writer.close();
//            }


            ResultSet rs = null;
            String lastidx = request.getParameter("idlast");
            //String lastidx="5";
            try {
                String connectionURL = "jdbc:mysql://localhost:3306/progin_405_13510060";
                Connection connection = null;
                Statement statement = null;

                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(connectionURL, "progin", "progin");
                statement = connection.createStatement();
                String QueryStr = "SELECT cat_task_name,task_name,task_status,task_tag_multivalue,checkbox,assignee_name,file FROM task WHERE task_id='" + lastidx + "'";
                rs = statement.executeQuery(QueryStr);
                if (rs.next()) {
                    response.sendRedirect("src/taskdetail_file.jsp?ct="+rs.getString("cat_task_name")+"&tm="+rs.getString("task_name")+"&ts="+rs.getString("task_status")+"&ttm="+rs.getString("task_tag_multivalue")+"&c="+rs.getString("checkbox")+"&asign="+ rs.getString("assignee_name") +"&file="+rs.getString("file")            );
//                   response.sendRedirect("../index.jsp");
                    return;
                }
                
            } catch (Exception e) {
               // System.out.println("Exception pas connect ");
                e.printStackTrace();
                //out.println("Unable to connect to database "+e.getMessage());
            }



//        System.out.println("final");
        }
        // System.out.println("method dopost kepanggil");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
