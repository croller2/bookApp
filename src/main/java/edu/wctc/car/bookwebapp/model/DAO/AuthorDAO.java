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
    public Author getAuthorById(int id) throws SQLException, ClassNotFoundException{
        Author author = null;
        Map<String,Object> record = db.getRecordById("author", "author_id", id);
        if(!record.isEmpty()){
            author = new Author();
            author.setAuthorId(Integer.parseInt(record.get("author_id").toString()));
            author.setDateAdded((Date)record.get("author_date"));
            author.setAuthorName(record.get("author_name").toString());
        }
        return new Author();
    }
    @Override
    public List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException{
        List<Author> authors = new Vector<>();
        List<Map<String,Object>> rawData = db.getAllRecords("authors", 0);
        Author author = null;
        for(Map<String,Object> rec : rawData){
            author = new Author();
            author.setAuthorId(Integer.parseInt(rec.get("author_id").toString()));
            author.setDateAdded((Date)rec.get("author_date"));
            author.setAuthorName(rec.get("author_name").toString());
            
            authors.add(author);
        }       
        return authors;
    }
    
    @Override
    public void deleteAuthorById(int id) throws SQLException, ClassNotFoundException{
        db.deleteRecordById("authors", "author_id", id);
    }
    
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IDataAccess db = new MySqlDataAccess("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin");
        AuthorDAO adao = new AuthorDAO("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/bookWebApp", "root", "admin", db);
        List<Author> authorList =  adao.getListOfAuthors();
        for(Author author : authorList){
            System.out.println(author);
        }
    }
}
