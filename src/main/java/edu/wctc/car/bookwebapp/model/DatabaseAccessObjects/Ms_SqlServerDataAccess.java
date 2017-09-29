/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DatabaseAccessObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author chris.roller
 */
public class Ms_SqlServerDataAccess implements IDataAccess {
    
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
        if(url != null){
            this.url = url;            
        }
    }

    @Override
    public final String getDriverClass() {
        return driverClass;
    }

    @Override
    public final void setDriverClass(String driverClass) {
        if(driverClass != null){
            this.driverClass = driverClass;           
        }
    }

    @Override
    public final String getUserName() {
        return userName;
    }

    @Override
    public final void setUserName(String userName) {
        if(userName != null){
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

    public Ms_SqlServerDataAccess(String driverClass, 
            String url,
            String userName, 
            String password) {
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
    }
    
    /**
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public final void openConnection() throws ClassNotFoundException, SQLException{
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void closeConnection()throws SQLException{
        if(conn != null)
            conn.close();
    }
    /**
     * Returns records from a table, requires an open connection
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    @Override
    public final List<Map<String,Object>> getAllRecords(String tableName, int maxRecords) 
            throws SQLException, ClassNotFoundException{
        
       List<Map<String,Object>> rawData = new Vector<>();
       String sql = "";
       if(maxRecords > ALL_RECORDS ){
           sql = "SELECT TOP " + maxRecords + " * FROM " + tableName;
       }else{
           sql = "SELECT * FROM " + tableName;
       }
       
       openConnection();
       stmt = conn.createStatement();
       rs = stmt.executeQuery(sql);   
       
       ResultSetMetaData rsmd = rs.getMetaData();
       
       int colCount = rsmd.getColumnCount();
       Map<String, Object> record = null;  
       while(rs.next()){ 
           record = new LinkedHashMap<>();
           for(int colNum = 1 ; colNum <= colCount ; colNum ++){
               record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
           }
           rawData.add(record);
       }
       closeConnection();
       return rawData; 
    }
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Ms_SqlServerDataAccess dbObject = new Ms_SqlServerDataAccess("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/sample" , "app", "app");
        List<Map<String,Object>> list = dbObject.getAllRecords("customer", 5);
        
        for(Map<String,Object> rec : list){
            System.out.println(rec);
    }
        
        
    }

    @Override
    public Map<String, Object> getRecordById(String tableName, String columnName, int id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRecordById(String tableName, String columnName, int id) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
