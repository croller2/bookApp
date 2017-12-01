/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author chris.roller
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }
    
    
    public void addNewBook(String bookId, String title, String isbn, String authorId , Date bookPublishDate){
        Book book = null;
        Integer authId = new Integer(authorId);
        if(bookId == null || bookId.isEmpty()){
            book = new Book();
      
        }else{
            book = new Book(new Integer(bookId));     
        }
        book.setBookName(title);
        book.setBookIsbn(isbn);
        book.setBookPublishDate(bookPublishDate);     
        Author author = getEntityManager().find(Author.class, new Integer(authorId));
        book.setAuthor(author);
        getEntityManager().merge(book);
    }
            /**
     * Deletes an author by id
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public int deleteBook(String id) throws SQLException, ClassNotFoundException{
        int deletedResults = 0;
        try {
            String jpql = "DELETE b FROM Book b WHERE b.bookId = :id";
            Query query = getEntityManager().createQuery(jpql);
            query.setParameter("id", Integer.parseInt(id));
            deletedResults = query.executeUpdate();
        } catch (NumberFormatException ex) {
            deletedResults = -1;
        }
        return deletedResults;
    }
    
        /**
     * Returns a single author by ID
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Book findById(int id) throws SQLException, ClassNotFoundException{
        Book b = new Book();
        try{
            String jpql = "SELECT b FROM Book WHERE b.bookId = :id";
            TypedQuery<Book> q = getEntityManager().createQuery(jpql, Book.class);
            q.setParameter("id", id);
            b = q.getSingleResult();
        }catch(Exception e){
            b = null;
        }
        return b;
    }
    
     /**Gets  a list of books
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Book> findAll(){
        List<Book> books = new ArrayList<>();
        try{
            String jpql = "SELECT b FROM Book b";
            TypedQuery<Book> q = getEntityManager().createQuery(jpql, Book.class);
            q.setMaxResults(100);
            books = q.getResultList();
        }catch(Exception ex){
            books = null;
        }
        return books;
    }
    
}
