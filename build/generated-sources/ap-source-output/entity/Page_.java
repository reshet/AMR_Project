package entity;

import entity.Attachment;
import entity.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-25T17:20:27")
@StaticMetamodel(Page.class)
public class Page_ { 

    public static volatile SingularAttribute<Page, Integer> id;
    public static volatile SingularAttribute<Page, byte[]> content;
    public static volatile CollectionAttribute<Page, Attachment> attachmentCollection;
    public static volatile SingularAttribute<Page, Book> idBook;

}