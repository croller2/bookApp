/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DatabaseAccessObjects;

import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = " + id;

        openConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
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
    
    @Override
    public void deleteRecordById(String tableName, String columnName, int id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE * FROM " + tableName + " WHERE " + columnName + " = " + id;

        openConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        closeConnection();
    }
    
    public void upDateRecordById(String tableName, ArrayList<String> columns, ArrayList<String> values, String id_col, int id){
        String sql = "UPDATE " + tableName + " SET ";          
        if(columns.size() == values.size()){
            int i = columns.size() - 1;
            ArrayList<String> joinedValues = new ArrayList<>();
            while(i <= columns.size()){
                joinedValues.add(columns.get(i) + "=" + values.get(i));
                i++;
            }
            String updateValues = String.join(",", joinedValues);
            sql = sql + updateValues + " WHERE " + id_col + " = " + id;
        }

    }
    public final static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlDataAccess dbObject = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        Map<String, Object> record = dbObject.getRecordById("authors", "author_id", 1);

        System.out.println(record);
    }



}
