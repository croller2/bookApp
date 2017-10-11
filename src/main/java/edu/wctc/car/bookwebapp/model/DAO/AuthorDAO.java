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
        Map<String,Object> record = db.getRecordById(AUTHOR_TABLE, AUTHOR_ID, id);
        if(!record.isEmpty()){
            author = new Author();
            author.setAuthorId(Integer.parseInt(record.get(AUTHOR_ID).toString()));
            author.setDateAdded((Date)record.get(AUTHOR_DATE));
            author.setAuthorName(record.get(AUTHOR_NAME).toString());
        }
        return new Author();
    }
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
    
    @Override
    public final int deleteAuthorById(Integer id) throws SQLException, ClassNotFoundException{
       if(id == null || id < 1){
           throw new IllegalArgumentException("id must be an integer greater than 1");
       }
       return db.deleteRecordById(AUTHOR_TABLE, AUTHOR_ID, id);
       
    }
    
   @Override
    public final int updateAuthorById(Author author) throws SQLException, ClassNotFoundException {
        int recordsUpdated = 0;
        if(author != null){
            ArrayList<String> columnNames = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            columnNames.add(AUTHOR_NAME);
            columnNames.add(AUTHOR_DATE);
            values.add(author.getAuthorName());
            values.add(author.getDateAdded());
            recordsUpdated = db.updateRecord(AUTHOR_TABLE, columnNames, values, AUTHOR_ID, author.getAuthorId());   
        }
        return recordsUpdated;
    }
    
    @Override
    public final void addNewAuthor(Author author) throws SQLException, ClassNotFoundException {
        if(author != null){
            ArrayList<String> columnNames = new ArrayList<>();
            ArrayList<Object> values = new ArrayList<>();
            columnNames.add(AUTHOR_DATE);
            columnNames.add(AUTHOR_NAME);
            values.add(author.getDateAdded());
            values.add(author.getAuthorName());
            db.insertNewRecord(AUTHOR_TABLE, columnNames, values);   
        }
    }
    
  
    public static void main(String[] args) throws SQLException, ClassNotFoundException, ParseException {
        IDataAccess db = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        AuthorDAO adao = new AuthorDAO("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin", db);
        Author author = new Author();
        author.setAuthorName("Mark Twain 1");
        Date dateAdded = new Date();
        DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
        dateAdded = format.parse("09-09-2017");
        author.setDateAdded(dateAdded);        
        adao.addNewAuthor(author);
        List<Author> authorList =  adao.getListOfAuthors();
        for(Author auth : authorList){
            System.out.println(auth);
        }
    }

 
}
