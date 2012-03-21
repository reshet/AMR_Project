package com.mresearch.databank.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class MetaUnitStringDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4182411257288781624L;
     //private Class c_type;
	public MetaUnitStringDTO()
	{
		super();
	}        
	public MetaUnitStringDTO(Long id,String desc,String un_name)
        {
            super();
            super.setId(id);
            super.setDesc(desc);
            super.setUnique_name(un_name);
        }
		
    /**
     * @return the isCatalogizable
     */
}
