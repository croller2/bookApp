/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DatabaseAccessObjects;

import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

/**
 *
 * @author chris.roller
 */
public class MySqlDataAccess implements IDataAccess {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String url;
    private String driverClass;
    private String userName;
    private String password;
    private PreparedStatement psmt;

    private final int ALL_RECORDS = 0;

    @Override
    public final String getUrl() {
        return url;
    }

    @Override
    public final void setUrl(String url) {
        if (url != null) {
            this.url = url;
        }
    }

    @Override
    public final String getDriverClass() {
        return driverClass;
    }

    @Override
    public final void setDriverClass(String driverClass) {
        if (driverClass != null) {
            this.driverClass = driverClass;
        }
    }

    @Override
    public final String getUserName() {
        return userName;
    }

    @Override
    public final void setUserName(String userName) {
        if (userName != null) {
            this.userName = userName;
        }
    }

    @Override
    public final String getPassword() {
        return password;
    }

    @Override
    public final void setPassword(String password) {
        this.password = password;
    }

    public MySqlDataAccess(String driverClass,
            String url,
            String userName,
            String password) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }

    @Override
    public final void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    @Override
    public final void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * Returns records from a table, requires an open connection
     *
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     */
    @Override
    public final List<Map<String, Object>> getAllRecords(String tableName, int maxRecords)
            throws SQLException, ClassNotFoundException {

        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        if (maxRecords > ALL_RECORDS) {
            sql = "SELECT * FROM " + tableName + " LIMIT " + maxRecords;
        } else {
            sql = "SELECT * FROM " + tableName;
        }

        openConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();

        int colCount = rsmd.getColumnCount();
        Map<String, Object> record = null;
        while (rs.next()) {
            record = new LinkedHashMap<>();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }
        closeConnection();
        return rawData;
    }

    /**
     * Returns a single record based on id
     * @param tableName
     * @param columnName
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final Map<String, Object> getRecordById(String tableName, String columnName, int id) throws SQLException, ClassNotFoundException {
        @SuppressWarnings("UseOfObsoleteCollectionType")
        Map<String, Object> record = new LinkedHashMap<>();
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";

        openConnection();
        psmt = conn.prepareStatement(sql);
        psmt.setObject(1, id);
        rs = psmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {
            for (int colNum = 1; colNum <= colCount; colNum++) {
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
        }
        closeConnection();
        return record;
    }
  
    /**
     * Deletes a single record by id
     * @param tableName
     * @param columnName
     * @param id
     * @return 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final int deleteRecordById(String tableName, String columnName, Object id) throws SQLException, ClassNotFoundException {
        int deletedRecords = 0;
        String sql = "DELETE FROM " + tableName + " WHERE " + columnName + " = ?";
        openConnection();
        psmt = conn.prepareStatement(sql);
        psmt.setObject(1, id);
        deletedRecords = psmt.executeUpdate(sql);
        closeConnection();
        return deletedRecords;
    }
    
    @Override
    public final int updateRecord(String tableName, ArrayList<String> columnNames, ArrayList<Object> values, String identifierColumnName, Object identifierValue) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE " + tableName + " SET ";
        int updatedRecords = 0;
        if(columnNames.size() == values.size()){
            ArrayList<String> joinedValues = new ArrayList<>();
            for(int col = 0; col < columnNames.size() ; col++){
                joinedValues.add(columnNames.get(col) + " = \"" + values.get(col).toString()+ "\"");
            }
            String updateValues = String.join(",", joinedValues);
            sql = sql + updateValues + " WHERE " + identifierColumnName + " = " + identifierValue.toString() + "";
            
            openConnection();
            stmt = conn.createStatement();
            updatedRecords = stmt.executeUpdate(sql);
            closeConnection();
        }
        return updatedRecords;
    }    
   
    @Override
    public final void insertNewRecord(String tableName, ArrayList<String> columnNames, ArrayList<Object> values) throws ClassNotFoundException, SQLException{
        String sql = "INSERT INTO " + tableName + " (";
        if(columnNames.size() == values.size()){
            for(String col : columnNames){
                sql += col + ", ";
            }
            sql = sql.substring(0, sql.length()- 2 )+ ") VALUES (";
            
            for(Object value : values){
                sql += "\'" + value.toString() + "\',";
            }
            sql = sql.substring(0, sql.length()-1) + ")";
            
            openConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            closeConnection();
        }
    }
    public final static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlDataAccess dbObject = new MySqlDataAccess(
                "com.mysql.jdbc.Driver", 
                "jdbc:mysql://localhost:3306/bookWebApp", 
                "root", 
                "admin");
        ArrayList<String> cols = new ArrayList<>();
        cols.add("author_date");
        cols.add("author_name");
        ArrayList<Object> values = new ArrayList<>();
        values.add("2017-08-09");
        values.add("Joseph Heller 3");
        
        dbObject.deleteRecordById("authors", "author_id", 8);
        //int updatedRecords = dbObject.updateRecord("authors", cols,values, "author_id", 2);
        //Map<String, Object> record = dbObject.getRecordById("authors", "author_id", 1);
        dbObject.insertNewRecord("authors", cols, values);
        System.out.println(dbObject.getAllRecords("authors", 0));
        

     
    }





}
