/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.car.bookwebapp.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author chris.roller
 */
public class AuthorService {
    
    public List<Author> getAuthorList(){
        return Arrays.asList(
                new Author(1, "Joseph Heller", new Date()),
                new Author(2, "Mark Twain", new Date()),
                new Author(3, "Shakespeare", new Date())
        );
    }
}
