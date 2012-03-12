package entity;

import entity.Page;
import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2012-03-12T08:35:56")
@StaticMetamodel(Attachment.class)
public class Attachment_ { 

    public static volatile SingularAttribute<Attachment, Integer> id;
    public static volatile SingularAttribute<Attachment, Serializable> content;
    public static volatile SingularAttribute<Attachment, Page> idPage;

}