package com.mresearch.databank.shared;

import java.io.Serializable;
public class MetaUnitMultivaluedStructureDTO extends MetaUnitMultivaluedDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1903172919395606840L;
	
	public MetaUnitMultivaluedStructureDTO(){
            super();
        }
	public MetaUnitMultivaluedStructureDTO(Long id,String un_name,String desc)
        {
            super(id,un_name,desc);
        }
	@Override
	protected MetaUnitMultivaluedDTO doGetClone()
	{
		MetaUnitMultivaluedStructureDTO dto = new MetaUnitMultivaluedStructureDTO(this.getId(), this.getDesc(), this.getUnique_name());
		dto.setIsCatalogizable(this.isIsCatalogizable());
		dto.setIsSplittingEnabled(this.isIsSplittingEnabled());
		dto.setSub_meta_units(this.getSub_meta_units());
		dto.setTagged_entities(this.getTagged_entities());
		return dto;
	}

    /**
     * @return the tagged_entities
     */

    /**
     * @return the isSplittingEnabled
     */
    /**
     * @return the isMultiselected
     */
}
