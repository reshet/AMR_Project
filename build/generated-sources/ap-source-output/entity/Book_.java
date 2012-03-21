package entity;

import entity.Customer;
import entity.Page;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-22T01:49:54")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, Integer> id;
    public static volatile SingularAttribute<Book, Customer> idCustomer;
    public static volatile SingularAttribute<Book, String> name;
    public static volatile ListAttribute<Book, Page> pageCollection;

}