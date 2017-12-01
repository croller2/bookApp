/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.controller;

import edu.wctc.car.bookwebapp.model.Book;
import edu.wctc.car.bookwebapp.model.BookFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJB;
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
@WebServlet(name = "BookController", urlPatterns = {"/book"})
public class BookController extends HttpServlet {
    @EJB
    private BookFacade bf;
    private static String RESULT_PAGE = "/index.jsp";
    private static final String BOOK_LIST_PAGE = "/bookList.jsp";
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String LIST_ACTION = "bookList";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    private static final String ACTION = "action";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String DELETION = "deletionMessage";
    private static final String DELETION_MESSAGE = "Record successfully deleted";
    private static final String ADD = "add";
    private static final String BOOKS = "books";
    private static final String BOOK_NAME = "book_name";
    private static final String BOOK_ID = "book_id";
    private static final String BOOK_PUBLISH_DATE = "book_publish_date";
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
                try{
            String action = request.getParameter(ACTION);
            if(action.equalsIgnoreCase(LIST_ACTION)){
                getBookListPage(request,response);
//            }else if(action.equalsIgnoreCase(EDIT)){
//                editAuthor(request,response);
//            }else if(action.equalsIgnoreCase(DELETE)){
//                deleteAuthor(request,response);
//            }else if(action.equalsIgnoreCase(ADD)){
//                addAuthor(request, response);            
            }else{
                RESULT_PAGE = BOOK_LIST_PAGE;
                request.setAttribute(BOOKS, refreshBookList());
            }
        }catch(ClassNotFoundException | SQLException ex){
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
    private void getBookListPage(HttpServletRequest request,HttpServletResponse response){
        RESULT_PAGE = BOOK_LIST_PAGE;
        request.setAttribute(BOOKS, bf.findAll());
    }
    
        /**Refreshes the author list after CRUD Operations
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    private List<Book> refreshBookList() throws SQLException, ClassNotFoundException{
        return bf.findAll();
    }
}
