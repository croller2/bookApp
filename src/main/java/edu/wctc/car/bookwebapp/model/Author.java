package edu.wctc.car.bookwebapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jlombardo
 */

@Entity
@Table(name="author")
public class Author implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookAuthorId")
    private Set<Book> bookSet;

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "author_id")
    private Integer authorId;
    
    @Size(max=80)
    @Column(name="author_name")
    private String authorName;
    
    @Column(name="author_date")
    @Temporal(TemporalType.DATE)
    private Date dateAdded;

    public Author() {
    }

    public Author(Integer authorId) {
        setAuthorId(authorId);
    }

    public Author(Integer authorId, String authorName, Date dateAdded) {
        setAuthorId(authorId);
        setAuthorName(authorName);
        setDateAdded(dateAdded);
    }

    public final Integer getAuthorId() {
        return authorId;
    }

    public final void setAuthorId(Integer authorId) {
        if(authorId != null && authorId > 0){
            this.authorId = authorId;            
        }
    }

    public final String getAuthorName() {
        return authorName;
    }

    public final void setAuthorName(String authorName) {
        if(authorName != null && authorName.length()>0){
            this.authorName = authorName;            
        }
    }

    public final Date getDateAdded() {
        return dateAdded;
    }

    public final void setDateAdded(Date dateAdded) {
        if(dateAdded != null){
            this.dateAdded = dateAdded;            
        }
    }

    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.authorId);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (!Objects.equals(this.authorId, other.authorId)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }

    @XmlTransient
    public Set<Book> getBookSet() {
        return bookSet;
    }

    public void setBookSet(Set<Book> bookSet) {
        this.bookSet = bookSet;
    }
    
    
}
