/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DAO;

import edu.wctc.car.bookwebapp.model.Author;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author chris.roller
 */
public interface IAuthorDAO {

    Author getAuthorById(int id) throws SQLException, ClassNotFoundException;

    IDataAccess getDb();

    String getDriverClass();

    List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;
    
    void deleteAuthorById(int id) throws SQLException, ClassNotFoundException;
    
    String getPassword();

    String getUrl();

    String getUserName();

    void setDb(IDataAccess db);

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUserName(String userName);
    
}