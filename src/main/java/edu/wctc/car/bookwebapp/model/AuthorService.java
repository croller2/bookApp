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
    public final List<Author> getAuthorList() throws SQLException, ClassNotFoundException{
        return adao.getListOfAuthors();
    }

    public final Author getAuthorById(int id) throws SQLException, ClassNotFoundException{
        return adao.getAuthorById(id);
    }
    
    public final void deleteAuthor(int id) throws SQLException, ClassNotFoundException{
        adao.deleteAuthorById(id);
    }
    public final IAuthorDAO getAdao() {
        return adao;
    }

    public final void setAdao(IAuthorDAO adao) {
        if(adao != null){
            this.adao = adao;            
        }

    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IDataAccess db = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        AuthorDAO adao = new AuthorDAO("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin", db);
        AuthorService as = new AuthorService(adao);
        List<Author> authorList = as.getAuthorList();
        for(Author author : authorList){
            System.out.println(author);
        }
    }
    
    
}
