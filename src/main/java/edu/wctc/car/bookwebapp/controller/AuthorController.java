/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.controller;

import edu.wctc.car.bookwebapp.model.Author;
import edu.wctc.car.bookwebapp.model.AuthorService;
import edu.wctc.car.bookwebapp.model.DAO.AuthorDAO;
import edu.wctc.car.bookwebapp.model.DAO.IAuthorDAO;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.MySqlDataAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chris.roller
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/authorController"})
public class AuthorController extends HttpServlet {
    
    private IDataAccess db;
    private AuthorService as;
    
    private static String RESULT_PAGE = "/index.jsp";
    private static String AUTHOR_LIST_PAGE = "/authorList.jsp";
    private static String ERROR_PAGE = "/error.jsp";
    private static String LIST_ACTION = "authorList";
    private static String EDIT = "edit";
    private static String DELETE = "delete";
    private static String ACTION = "action";
    private static String ERROR_MESSAGE = "errorMessage";
    private static String DELETION = "deletionMessage";
    private static String ADD = "add";
    private static String AUTHORS = "authors";
    
    private String driverClass;
    private String url;
    private String username;
    private String password;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        AuthorService authService = new AuthorService(new
        AuthorDAO(driverClass,url,username, password, 
            new MySqlDataAccess(driverClass,url,username, password)));
        
        try{
            String action = request.getParameter(ACTION);
            if(action.equalsIgnoreCase(LIST_ACTION)){
                getAuthorListPage(request,response);
            }else if(action.equalsIgnoreCase(EDIT)){
                editAuthor(request,response);
            }else if(action.equalsIgnoreCase(DELETE)){
                deleteAuthor(request,response);
            }else if(action.equalsIgnoreCase(ADD)){
                addAuthor(request, response);            
            }else{
                RESULT_PAGE = AUTHOR_LIST_PAGE;
                request.setAttribute(AUTHORS, refreshAuthorList());
            }
        }catch(Exception ex){
            RESULT_PAGE = ERROR_PAGE;
            request.setAttribute(ERROR_MESSAGE, ex.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher(RESULT_PAGE);
        view.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**Returns the author list page
     * 
     * @param request
     * @param response 
     */
    private void getAuthorListPage(HttpServletRequest request,HttpServletResponse response){
        try {
            RESULT_PAGE = AUTHOR_LIST_PAGE;
            request.setAttribute(AUTHORS, as.getAuthorList());
            
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }
   
    /**Edits the author
     * 
     * @param request
     * @param response 
     */
    private void editAuthor(HttpServletRequest request, HttpServletResponse response) {
        //Todo:  Check if author already exists via name
        try{
            int authorId = Integer.parseInt(request.getParameter("id"));
            Author authorToEdit = as.getAuthorById(authorId);
            if(authorToEdit != null){
                Map<String,Object> authorValues = new HashMap<String,Object>();
                authorValues.put("author_name", request.getParameter("addAuthorName"));
                authorValues.put("author_id", request.getParameter("id"));
                as.updateAuthor(authorValues);   
            }
            RESULT_PAGE = AUTHOR_LIST_PAGE;
            request.setAttribute(AUTHORS, refreshAuthorList());
        }catch(Exception ex){
            RESULT_PAGE = ERROR_PAGE;
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }
    
    /**Deletes the author by id
     * 
     * @param request
     * @param response 
     */
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response){
        try{
            int recordsDeleted = as.deleteAuthor(Integer.parseInt(request.getParameter("id")));
            RESULT_PAGE = AUTHOR_LIST_PAGE;
            request.setAttribute(DELETION , "Record Successfully deleted");
            request.setAttribute(AUTHORS, refreshAuthorList());
        }catch(Exception ex){
            RESULT_PAGE = ERROR_PAGE;
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }
    
    /**Adds a new author
     * 
     * @param request
     * @param response 
     */
    private void addAuthor(HttpServletRequest request, HttpServletResponse response){
        try{             
            Map<String,Object> authorValues = new HashMap<String,Object>();
            authorValues.put("author_name", request.getParameter("addAuthorName"));
            authorValues.put("author_date", new Date());
       

  
            as.addAuthor(authorValues);
            RESULT_PAGE = AUTHOR_LIST_PAGE;
            request.setAttribute(AUTHORS, refreshAuthorList());
        }catch(Exception ex){
            RESULT_PAGE = ERROR_PAGE;
           request.setAttribute(ERROR_MESSAGE, ex.getCause()); 
        }
    }
   
    /**Refreshes the author list after CRUD Operations
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private List<Author> refreshAuthorList() throws SQLException, ClassNotFoundException{
        return as.getAuthorList();
    }
    
    @Override
    public void init() throws ServletException {
        driverClass = getServletContext()
                .getInitParameter("db.driver.class");
        url = getServletContext()
                .getInitParameter("db.url");
        username = getServletContext()
                .getInitParameter("db.username");
        password = getServletContext()
                .getInitParameter("db.password");
    }

}
