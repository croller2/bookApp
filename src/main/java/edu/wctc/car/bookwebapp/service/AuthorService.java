package edu.wctc.car.bookwebapp.service;

import edu.wctc.car.bookwebapp.model.Author;
import edu.wctc.car.bookwebapp.repository.AuthorRepository;
import edu.wctc.car.bookwebapp.repository.BookRepository;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a special Spring-enabled service class that delegates work to 
 * various Spring managed repository objects using special spring annotations
 * to perform dependency injection, and special annotations for transactions.
 * It also uses SLF4j to provide logging features.
 * 
 * Don't confuse the Spring @Respository annotation with the repository
 * classes (AuthorRepository, BookRespository). The annotation here is used 
 * solely to tell Spring to translate any exception messages into more
 * user friendly text. Any class annotated that way will do that.
 * 
 * @author jlombardo
 */
@Service
@Transactional(readOnly = true)
public class AuthorService {

    private transient final Logger LOG = LoggerFactory.getLogger(AuthorService.class);

    @Inject
    private AuthorRepository authorRepo;

    @Inject
    private BookRepository bookRepo;

    public AuthorService() {
    }

    public List<Author> findAll() {
        return authorRepo.findAll();
    }
    
    public List<Author> findAllEagerly() {
        List<Author> authors = authorRepo.findAll();
        for(Author a : authors) {
            a.getBookSet().size();
        }
        
        return authors;
    }
    
    /**
     * This custom method is necessary because we are using Hibernate which
     * does not load lazy relationships (in this case Books).
     * @param id
     * @return 
     */
    public Author findByIdAndFetchBooksEagerly(String id) {
        Integer authorId = new Integer(id);
        Author author = authorRepo.findOne(authorId);

        // Eagerly fetches books within a transaction
        author.getBookSet().size();
        
        return author;
    }

    public Author findById(String id) {
        return authorRepo.findOne(new Integer(id));
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     */
    @Transactional
    public void remove(Author author) {
        LOG.debug("Deleting author: " + author.getAuthorName());
        authorRepo.delete(author);
    }

    /**
     * Spring performs a transaction with readonly=false. This
     * guarantees a rollback if something goes wrong.
     * @param author 
     */
    @Transactional
    public Author edit(Author author) {
        return authorRepo.saveAndFlush(author);
    }
    
    @Transactional
    public void deleteById(String id){
        int aId = new Integer(id);
        authorRepo.delete(aId);
    }
    
    public void create(Author a){
        authorRepo.save(a);
    }
}
