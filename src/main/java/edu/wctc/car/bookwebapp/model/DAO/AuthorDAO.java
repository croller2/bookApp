/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model.DAO;

import edu.wctc.car.bookwebapp.model.Author;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.IDataAccess;
import edu.wctc.car.bookwebapp.model.DatabaseAccessObjects.MySqlDataAccess;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author chris.roller
 */
public class AuthorDAO implements IAuthorDAO {
    private String driverClass;
    private String url;
    private String password;
    private String userName;
    private IDataAccess db;
    private final String AUTHOR_TABLE = "authors";
    private final String AUTHOR_NAME = "author_name";
    private final String AUTHOR_DATE = "author_date";
    private final String AUTHOR_ID = "author_id";
            
    public AuthorDAO(
            String driverClass, String url, 
            String userName, String password, 
            IDataAccess db) {
        
        setDriverClass(driverClass);
        setUrl(url);
        setUserName(userName);
        setPassword(password);
        this.db = db;
        db.setDriverClass(driverClass);
        db.setPassword(password);
        db.setUrl(url);
        db.setUserName(userName);
    }

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

    @Override
    public final IDataAccess getDb() {
        return db;
    }

    @Override
    public final void setDb(IDataAccess db) {
        if(db != null){
            this.db = db;            
        }
    }
    
    @Override
    public final Author getAuthorById(int id) throws SQLException, ClassNotFoundException{
        Author author = null;
        Map<String,Object> record = null;
        if(id > 0){
            record = db.getRecordById(AUTHOR_TABLE, AUTHOR_ID, id);   
        } 
        
        if(!record.isEmpty()){
            author = new Author();
            
            Object objRecId = record.get(AUTHOR_ID);
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            
            Object objName = record.get(AUTHOR_NAME);
            String authorName = objName == null ? "" : objName.toString();
            
            Object objDateAdded = record.get(AUTHOR_DATE);
            Date authorDateAdded = objDateAdded == null ? null : (Date)objDateAdded;
            
            author.setAuthorId(recId);    
            author.setDateAdded(authorDateAdded);
            author.setAuthorName(authorName);
            
        }
        return author;
    }
    /**
     * Gets all authors
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException{
        List<Author> authors = new Vector<>();
        List<Map<String,Object>> rawData = db.getAllRecords(AUTHOR_TABLE, 0);
        Author author = null;
        for(Map<String,Object> rec : rawData){
            author = new Author();
            
            Object objRecId = rec.get(AUTHOR_ID);
            Integer recId = objRecId == null ? 0 : Integer.parseInt(objRecId.toString());
            
            Object objName = rec.get(AUTHOR_NAME);
            String authorName = objName == null ? "" : objName.toString();
            
            Object objDateAdded = rec.get(AUTHOR_DATE);
            Date authorDateAdded = objDateAdded == null ? null : (Date)objDateAdded;
            
            author.setAuthorId(recId);    
            author.setDateAdded(authorDateAdded);
            author.setAuthorName(authorName);
            
            authors.add(author);
        }       
        return authors;
    }
    
    /**
     * Deletes an author by ID
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final int deleteAuthorById(Integer id) throws SQLException, ClassNotFoundException{
       if(id == null || id < 1){
           throw new IllegalArgumentException("id must be an integer greater than 1");
       }
       return db.deleteRecordById(AUTHOR_TABLE, AUTHOR_ID, id);
       
    }
    
    /**
     * Updates an author
     * @param author
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
   @Override
    public final int updateAuthorById(Map<String,Object> author) throws SQLException, ClassNotFoundException {
        int recordsUpdated = 0;
        Map<String,Object> authorValues = author;
        if(authorValues != null){
            ArrayList<String> columnNames = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            if(authorValues.get(AUTHOR_NAME) != null){
                    values.add(authorValues.get(AUTHOR_NAME));
                    columnNames.add(AUTHOR_NAME);
                }
                if(authorValues.get(AUTHOR_DATE) != null){
                    columnNames.add(AUTHOR_DATE);              
                    values.add(authorValues.get(AUTHOR_DATE));    
                }
            recordsUpdated = db.updateRecord(AUTHOR_TABLE, columnNames, values, AUTHOR_ID, authorValues.get(AUTHOR_ID));   
        }
        return recordsUpdated;
    }
    /**
     * Adds a new author
     * @param author
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public final void addNewAuthor(Map<String,Object> author) throws SQLException, ClassNotFoundException {
        Map<String,Object> authorValues = author;
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();
        if(authorValues != null){
            
                if(authorValues.get(AUTHOR_NAME) != null){
                    values.add(authorValues.get(AUTHOR_NAME));
                    columnNames.add(AUTHOR_NAME);
                }
                if(authorValues.get(AUTHOR_DATE) != null){
                    columnNames.add(AUTHOR_DATE);              
                    values.add(authorValues.get(AUTHOR_DATE));    
                }
            

            db.insertNewRecord(AUTHOR_TABLE, columnNames, values);   
        }
    }
    /**
     * Class level testing
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException 
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        IDataAccess db = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        AuthorDAO adao = new AuthorDAO("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin", db);
       // author.setAuthorId(11);
       // author.setAuthorName("Mark Twain 15");
       // Date dateAdded = new Date();
       // DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
        //dateAdded = format.parse("09-09-2017");
        //author.setDateAdded(dateAdded);        
        Map<String,Object> author = new HashMap<String,Object>();
        author.put("author_name", "Hello");
        author.put("author_id", 20);
        adao.updateAuthorById(author);
        //adao.deleteAuthorById(19);
        System.out.println(author);
        //List<Author> authorList =  adao.getListOfAuthors();
        //for(Author auth : authorList){
        //    System.out.println(auth);
       //}
    }

 
}
