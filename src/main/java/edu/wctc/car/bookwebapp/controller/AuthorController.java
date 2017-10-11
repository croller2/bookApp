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
import java.util.Date;
import java.util.List;
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
    
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/bookWebApp";
    private String userName = "root";
    private String password = "admin";
    private IDataAccess db;
    private AuthorService as;
    
    private static String RESULT_PAGE = "/authorList.jsp";
    private static String LIST_ACTION = "authorList";
    private static String EDIT = "edit";
    private static String DELETE = "delete";
    private static String ACTION = "action";
    private static String ERROR_MESSAGE = "errorMessage";
    private static String ADD = "add";
    
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
        
        db = new MySqlDataAccess(driver, url, userName, password);
        IAuthorDAO adao = new AuthorDAO(driver, url, userName, password, db);
        as = new AuthorService(adao);
        
        try{
            String action = request.getParameter(ACTION);
            if(action.equalsIgnoreCase(LIST_ACTION)){
                GetAuthorListPage(request,response);
            }else if(action.equalsIgnoreCase(EDIT)){
                EditAuthor(request,response);
            }else if(action.equalsIgnoreCase(DELETE)){
                DeleteAuthor(request,response);
            }else if(action.equalsIgnoreCase(ADD)){
                AddAuthor(request, response);            
            }
        }catch(Exception ex){
            RESULT_PAGE = "/error.jsp";
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

    
    private void GetAuthorListPage(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setAttribute("authors", as.getAuthorList());
            
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }

    private void EditAuthor(HttpServletRequest request, HttpServletResponse response) {
        try{
            int authorId = Integer.parseInt(request.getParameter("id"));
            Author authorToEdit = as.getAuthorById(authorId);
            authorToEdit.setAuthorName(request.getParameter("authorName_" + authorId));
            DateFormat format = new SimpleDateFormat("MM-dd-yy");
            Date dateAdded = format.parse(request.getParameter("authorDate_" + authorId ));
            authorToEdit.setDateAdded(dateAdded);
            as.updateAuthor(authorToEdit);
            RESULT_PAGE = "/authorList.jsp";
            request.setAttribute("authors", RefreshAuthorList());
        }catch(Exception ex){
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }
    
    private void DeleteAuthor(HttpServletRequest request, HttpServletResponse response){
        try{
            as.deleteAuthor(Integer.parseInt(request.getParameter("id")));
            RESULT_PAGE = "/authorList.jsp";
            request.setAttribute("authors", RefreshAuthorList());
        }catch(Exception ex){
            request.setAttribute(ERROR_MESSAGE, ex.getCause());
        }
    }
    
    private void AddAuthor(HttpServletRequest request, HttpServletResponse response){
        try{
           
            Author newAuthor = new Author();
            newAuthor.setAuthorName(request.getParameter("addAuthorName"));
            DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
            Date dateAdded = format.parse(request.getParameter("addAuthorDate"));
            newAuthor.setDateAdded(dateAdded);
            as.addAuthor(newAuthor);
        }catch(Exception ex){
           request.setAttribute(ERROR_MESSAGE, ex.getCause()); 
        }
    }
    private List<Author> RefreshAuthorList() throws SQLException, ClassNotFoundException{
        return as.getAuthorList();
    }
            
        
}
