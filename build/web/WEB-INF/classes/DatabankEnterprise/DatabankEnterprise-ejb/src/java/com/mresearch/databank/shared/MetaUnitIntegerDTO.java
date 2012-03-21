package com.mresearch.databank.shared;

import java.io.Serializable;

public class MetaUnitIntegerDTO extends MetaUnitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6082354918447747417L;
     //private Class c_type;
	public MetaUnitIntegerDTO()
	{
		super();
	}
	public MetaUnitIntegerDTO(Long id,String desc,String un_name)
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
