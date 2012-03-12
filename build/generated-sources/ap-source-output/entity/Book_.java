package entity;

import entity.Customer;
import entity.Page;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-03-12T08:35:56")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile CollectionAttribute<Book, Customer> customerCollection;
    public static volatile SingularAttribute<Book, String> name;
    public static volatile CollectionAttribute<Book, Page> pageCollection;

}