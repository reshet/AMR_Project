/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utilities.SerializablePNG;

/**
 *
 * @author Kate Nezdoly
 */
@Entity
@Table(name = "PAGE", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Page.findAll", query = "SELECT p FROM Page p"),
    @NamedQuery(name = "Page.findById", query = "SELECT p FROM Page p WHERE p.id = :id")})
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Embedded
    @Column(name = "CONTENT")
    private SerializablePNG content;
    @JoinColumn(name = "ID_BOOK", referencedColumnName = "ID")
    @ManyToOne
    private Book idBook;
    @OneToMany(mappedBy = "idPage")
    private Collection<Attachment> attachmentCollection;

    public Page() {
    }

    public Page(SerializablePNG content, Book idBook, Collection<Attachment> attachmentCollection) {
        this.content = content;
        this.idBook = idBook;
        this.attachmentCollection = attachmentCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SerializablePNG getContent() {
        return content;
    }

    public void setContent(SerializablePNG content) {
        this.content = content;
    }

    public Book getIdBook() {
        return idBook;
    }

    public void setIdBook(Book idBook) {
        this.idBook = idBook;
    }

    @XmlTransient
    public Collection<Attachment> getAttachmentCollection() {
        return attachmentCollection;
    }

    public void setAttachmentCollection(Collection<Attachment> attachmentCollection) {
        this.attachmentCollection = attachmentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Page)) {
            return false;
        }
        Page other = (Page) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Page[ id=" + id + " ]";
    }
}
