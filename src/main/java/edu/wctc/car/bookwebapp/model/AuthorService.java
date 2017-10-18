/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model;

import edu.wctc.car.bookwebapp.model.DAO.AuthorDAO;
import edu.wctc.car.bookwebapp.model.DAO.IAuthorDAO;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.MySqlDataAccess;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chris.roller
 */
public class AuthorService {
    IAuthorDAO adao;
    
    
    public AuthorService(IAuthorDAO adao){
        setAdao(adao);
    }
    
    public final IAuthorDAO getAdao() {
        return adao;
    }
    
    public final void setAdao(IAuthorDAO adao) {
        if(adao != null){
            this.adao = adao;            
        }
    }
    /**Gets  a list of authors
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final List<Author> getAuthorList() throws SQLException, ClassNotFoundException{
        return adao.getListOfAuthors();
    }
/**
 * Returns a single author by ID
 * @param id
 * @return
 * @throws SQLException
 * @throws ClassNotFoundException 
 */
    public final Author getAuthorById(int id) throws SQLException, ClassNotFoundException{
        return adao.getAuthorById(id);
    }
    /**
     * Deletes an author by id
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final int deleteAuthor(int id) throws SQLException, ClassNotFoundException{
        return adao.deleteAuthorById(id);
    }
    /**
     * Adds a new author
     * @param newAuthor
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final void addAuthor(Author newAuthor) throws SQLException, ClassNotFoundException{
        
        //Todo: Update to pass parameters not author objects
        //List of maps 
        
        adao.addNewAuthor(newAuthor);
    }
    

    /**
     * Updates an author 
     * @param updatedAuthor
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final int updateAuthor(Author updatedAuthor) throws SQLException, ClassNotFoundException{
        return adao.updateAuthorById(updatedAuthor);
    }
    /**
     * Class level testing method
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IDataAccess db = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        AuthorDAO adao = new AuthorDAO("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin", db);
        AuthorService as = new AuthorService(adao);
        as.deleteAuthor(20);
        List<Author> authorList = as.getAuthorList();
        for(Author author : authorList){
            System.out.println(author);
        }
    }
    
    
}
