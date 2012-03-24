package entity;

import entity.Page;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-24T17:05:32")
@StaticMetamodel(Attachment.class)
public class Attachment_ { 

    public static volatile SingularAttribute<Attachment, Integer> id;
    public static volatile SingularAttribute<Attachment, byte[]> content;
    public static volatile SingularAttribute<Attachment, Page> idPage;
    public static volatile SingularAttribute<Attachment, String> name;

}