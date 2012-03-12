package entity;

import entity.Book;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-03-12T08:35:56")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Integer> id;
    public static volatile CollectionAttribute<Customer, Book> bookCollection;
    public static volatile SingularAttribute<Customer, String> login;
    public static volatile SingularAttribute<Customer, String> password;

}