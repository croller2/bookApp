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
import java.util.Map;
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
public class AuthorFacade extends AbstractFacade<Author> {

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuthorFacade() {
        super(Author.class);
    }
    
     /**
     * Deletes an author by id
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public void deleteAuthor(String id) throws SQLException, ClassNotFoundException{
        Author author = getEntityManager().find(Author.class, new Integer(id));
        getEntityManager().remove(getEntityManager().merge(author));
    }
    
    /**
     * Returns a single author by ID
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public Author findById(int id) throws SQLException, ClassNotFoundException{
        Author a = new Author();
        try{
            String jpql = "SELECT a FROM Author WHERE a.authorId = :id";
            TypedQuery<Author> q = getEntityManager().createQuery(jpql, Author.class);
            q.setParameter("id", id);
            a = q.getSingleResult();
        }catch(Exception e){
            a = null;
        }
        return new Author();
    }
    
        /**Gets  a list of authors
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Author> findAll(){
        List<Author> authors = new ArrayList<>();
        try{
            String jpql = "SELECT a FROM Author a";
            TypedQuery<Author> q = getEntityManager().createQuery(jpql, Author.class);
            q.setMaxResults(100);
            authors = q.getResultList();
        }catch(Exception ex){
            authors = null;
        }
        return authors;
    }
    
    public void updateAuthor(Author author) throws SQLException, ClassNotFoundException{
        Author a = getEntityManager().find(Author.class, author.getAuthorId());
        a.setAuthorName(author.getAuthorName());
        getEntityManager().merge(a);
    }
    
      public void addNewAuthor(String authorName){
        Author author = new Author();
        author.setAuthorName(authorName);
        author.setAuthorDate(new Date());
        getEntityManager().merge(author);
      }

    
}
