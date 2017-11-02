/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class AuthorService implements Serializable {
    
    
    public AuthorService() {
    }
    
    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        if(em != null){
            this.em = em;    
        }

    }

    

    /**Gets  a list of authors
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final List<Author> getAuthorList() throws SQLException, ClassNotFoundException{
        List<Author> authors = new ArrayList<>();
        try{
            String jpql = "SELECT a FROM Author a";
            TypedQuery<Author> q = getEm().createQuery(jpql, Author.class);
            q.setMaxResults(100);
            authors = q.getResultList();
        }catch(Exception ex){
            authors = null;
        }
        return authors;
    }
    /**
     * Returns a single author by ID
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final Author getAuthorById(int id) throws SQLException, ClassNotFoundException{
        Author a = new Author();
        try{
            String jpql = "SELECT a FROM Author WHERE a.authorId = :id";
            TypedQuery<Author> q = getEm().createQuery(jpql, Author.class);
            q.setParameter("id", id);
            a = q.getSingleResult();
        }catch(Exception e){
            a = null;
        }
        return new Author();
    }
    /**
     * Deletes an author by id
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final int deleteAuthor(String id) throws SQLException, ClassNotFoundException{
        int deletedResults = 0;
        try {
            String jpql = "DELETE a FROM Author a WHERE a.authorId = :id";
            Query query = getEm().createQuery(jpql);
            query.setParameter("id", Integer.parseInt(id));
            deletedResults = query.executeUpdate();
        } catch (NumberFormatException ex) {
            deletedResults = -1;
        }
        return deletedResults;
    }
    /**
     * Adds a new author
     * @param newAuthor
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final void addAuthor(Map<String,Object> newAuthor) throws SQLException, ClassNotFoundException{
            
    }
    

    /**
     * Updates an author 
     * @param updatedAuthor
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public final void updateAuthor(Map<String,Object> author) throws SQLException, ClassNotFoundException{
       //return adao.updateAuthorById(author);
    }
    /**
     * Class level testing method
     * @param args
     * @throws SQLException
     * @throws ClassNotFoundException 
     */

    
    
}
