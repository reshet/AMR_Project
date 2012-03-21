package com.mplatrforma.amr.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-21T22:41:46")
@StaticMetamodel(RxBlobStored.class)
public class RxBlobStored_ { 

    public static volatile SingularAttribute<RxBlobStored, Long> id;
    public static volatile SingularAttribute<RxBlobStored, Long> filesize;
    public static volatile SingularAttribute<RxBlobStored, byte[]> contents;
    public static volatile SingularAttribute<RxBlobStored, String> description;
    public static volatile SingularAttribute<RxBlobStored, String> filename;

}