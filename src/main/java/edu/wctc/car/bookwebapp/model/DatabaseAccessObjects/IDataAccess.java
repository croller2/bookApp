/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DatabaseAccessObjects;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author chris.roller
 */
public interface IDataAccess {

    void openConnection() throws SQLException, ClassNotFoundException;
    void closeConnection() throws SQLException;

    /**
     * Returns records from a table, requires an open connection
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     * @throws java.lang.ClassNotFoundException
     */
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException;

    Map<String,Object> getRecordById(String tableName, String columnName, int id) throws SQLException, ClassNotFoundException;
    
    void deleteRecordById(String tableName, String columnName, int id)throws SQLException, ClassNotFoundException;
    
    String getDriverClass();

    String getPassword();

    String getUrl();

    String getUserName();

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUserName(String userName);
    
}
