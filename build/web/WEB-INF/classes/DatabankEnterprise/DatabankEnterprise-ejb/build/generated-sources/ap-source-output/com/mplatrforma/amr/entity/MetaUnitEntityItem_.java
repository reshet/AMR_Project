package com.mplatrforma.amr.entity;

import com.mplatrforma.amr.entity.MetaUnitEntityItem;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-21T22:41:46")
@StaticMetamodel(MetaUnitEntityItem.class)
public class MetaUnitEntityItem_ { 

    public static volatile SingularAttribute<MetaUnitEntityItem, Long> id;
    public static volatile SingularAttribute<MetaUnitEntityItem, List> tagged_entities_ids;
    public static volatile SingularAttribute<MetaUnitEntityItem, String> v_value;
    public static volatile SingularAttribute<MetaUnitEntityItem, HashMap> mapped_values;
    public static volatile SingularAttribute<MetaUnitEntityItem, List> tagged_entities_identifiers;
    public static volatile SingularAttribute<MetaUnitEntityItem, Long> id_metaunitentity;
    public static volatile CollectionAttribute<MetaUnitEntityItem, MetaUnitEntityItem> subitems;

}