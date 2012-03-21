/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.service;

import com.mplatforma.amr.service.remote.AdminSocioResearchBeanRemote;
import com.mplatforma.amr.service.remote.RxStorageBeanRemote;
import com.mplatforma.amr.service.remote.UserAccountBeanRemote;

import com.mplatrforma.amr.entity.DatabankStructure;
import com.mplatrforma.amr.entity.MetaUnit;
import com.mplatrforma.amr.entity.MetaUnit3;
import com.mplatrforma.amr.entity.MetaUnitDate;
import com.mplatrforma.amr.entity.MetaUnitDate3;
import com.mplatrforma.amr.entity.MetaUnitDouble;
import com.mplatrforma.amr.entity.MetaUnitDouble3;
import com.mplatrforma.amr.entity.MetaUnitEntityItem;
import com.mplatrforma.amr.entity.MetaUnitFile;
import com.mplatrforma.amr.entity.MetaUnitInteger;
import com.mplatrforma.amr.entity.MetaUnitInteger3;

import com.mplatrforma.amr.entity.MetaUnitMultivalued;
import com.mplatrforma.amr.entity.MetaUnitMultivaluedEntity;
import com.mplatrforma.amr.entity.MetaUnitMultivaluedStructure;
import com.mplatrforma.amr.entity.MetaUnitString;
import com.mplatrforma.amr.entity.MetaUnitString3;
import com.mplatrforma.amr.entity.Organization;
import com.mplatrforma.amr.entity.ResearchFilesAccessor;
import com.mplatrforma.amr.entity.SingleStringEntity;
import com.mplatrforma.amr.entity.SocioResearch;
import com.mplatrforma.amr.entity.UserAccount;
import com.mplatrforma.amr.entity.Var;
import com.mresearch.databank.jobs.IndexResearchJob;
import com.mresearch.databank.jobs.ParseSpssJob;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitDateDTO;
import com.mresearch.databank.shared.MetaUnitDoubleDTO;
import com.mresearch.databank.shared.MetaUnitEntityItemDTO;
import com.mresearch.databank.shared.MetaUnitFileDTO;
import com.mresearch.databank.shared.MetaUnitIntegerDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.MetaUnitStringDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.RxStoredDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.elasticsearch.common.mvel2.optimizers.impl.refl.nodes.ArrayLength;
import org.opendatafoundation.data.FileFormatInfo;
import org.opendatafoundation.data.FileFormatInfo.Format;
import org.opendatafoundation.data.mod.SPSSFile;
import org.opendatafoundation.data.mod.SPSSFileException;
import org.opendatafoundation.data.mod.SPSSNumericVariable;
import org.opendatafoundation.data.mod.SPSSStringVariable;
import org.opendatafoundation.data.mod.SPSSVariable;
import org.w3c.dom.Document;

/**
 *
 * @author reshet
 */
@WebService
@Stateless(mappedName="AdminSocioResearchRemoteBean",name="AdminSocioResearchRemoteBean")
public class AdminSocioResearchSessionBean implements AdminSocioResearchBeanRemote{

    static
    {
         Locale locale = Locale.getDefault();
           System.out.println("Before setting, Locale is = " + locale);
         locale = new Locale("ru","RU");
        //  // Setting default locale  
        // // locale = Locale.ITALY;
         Locale.setDefault(locale);
          System.out.println("After setting, Locale is = " + locale);
    }
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private RxStorageBeanRemote store;
   
    public UserAccountDTO updateAccountResearchState(UserAccountDTO dto) {
        UserAccount account;
        UserAccountDTO returnBack = dto;
        account = em.find(UserAccount.class,dto.getId());
        if (account != null)
        {
                account.updateAccountResearchState(dto);
                returnBack = UserAccount.toDTO(account);
        }
        return returnBack;
    }

     

    @Override
    public Boolean deleteResearch(long id) {
        try
        {
            SocioResearch r = em.find(SocioResearch.class, id);
            em.remove(r);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
       // throw new UnsupportedOperationException("Not supported yet.");
    }

     private SocioResearch addResearch(SocioResearchDTO researchDTO) {
        SocioResearch research = null;
        try {
          research = new SocioResearch(researchDTO,em);
          em.persist(research);
          research.updateEntityID(research.getID(),research.getId_search_repres(), em);
         // currentUser.getFriends().add(friend);
        } finally {
        }
        return research;
      }
    @Override
    public SocioResearchDTO updateResearch(SocioResearchDTO rDTO) {
       // throw new UnsupportedOperationException("Not supported yet.");
            if (rDTO.getId() == 0){ // create new
              SocioResearch newResearch = addResearch(rDTO);
              return newResearch.toDTO();
            }

            SocioResearch research = null;
            try {
             
                
              research = em.find(SocioResearch.class, rDTO.getId());
              research.updateFromDTO(rDTO,em);
              addSSE("SocioResearch","gengeath", rDTO.getGen_geathering());
              addSSE("SocioResearch","method", rDTO.getMethod());
              ArrayList<String> concepts = rDTO.getConcepts();
              if(concepts!=null && concepts.size()>0)
              {
                  for(String concept:concepts)
                  {
                          addSSE("SocioResearch","concept", concept);
                  }  
              }
              ArrayList<String> researchers = rDTO.getResearchers();
              if(researchers!=null && researchers.size()>0)
              {
                  for(String researcher:researchers)
                  {
                          addSSE("SocioResearch","researcher", researcher);
                  }
              }
              addSSE("SocioResearch","org_impl", rDTO.getOrg_impl_name());
              addSSE("SocioResearch","org_order", rDTO.getOrg_order_name());
              ArrayList<String> pubs = rDTO.getPublications();
              if(pubs!=null && pubs.size()>0)
              {
                  for(String pub:pubs)
                  {
                          addSSE("SocioResearch","publication", pub);
                  }
              }
              addSSE("SocioResearch","selection_complexity", rDTO.getSel_complexity());
              addSSE("SocioResearch","selection_randomity", rDTO.getSel_randomity());
              
              launchIndexing(rDTO);
              
              int bb = 2;
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
            }
            
            return rDTO;

    }

    
    @Resource(mappedName="jms/myQCF")
    private  QueueConnectionFactory connectionFactory;

    @Resource(mappedName="jms/spss_parse")
    private  Queue queue;
    
//    @Resource(mappedName="jms/ES_index")
//    private  Queue index_queue;
    
    private void launchIndexing(SocioResearchDTO dto)
    {
         try {
            
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, 0);
            QueueSender q_sender = session.createSender(queue);

            ObjectMessage message = session.createObjectMessage();
            message.setStringProperty("title", "command to index SocioResearch");
            // here we create NewsEntity, that will be sent in JMS message
           // ParseSpssJob job = new ParseSpssJob(blobkey, length);
            IndexResearchJob job = new IndexResearchJob(dto.getId());
            message.setObject(job);    
           // message.setJMSDestination(queue);
            q_sender.send(message);
            q_sender.close();
            connection.close();
            //response.sendRedirect("ListNews");

        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public long parseSPSS(long blobkey, long length) {
        
        try {
            
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, 0);
            QueueSender q_sender = session.createSender(queue);

            ObjectMessage message = session.createObjectMessage();
            message.setStringProperty("title", "command to parse SPSS file");
            // here we create NewsEntity, that will be sent in JMS message
            ParseSpssJob job = new ParseSpssJob(blobkey, length);
          
            message.setObject(job);   
          //  message.setJMSDestination(queue);
            q_sender.send(message);
            q_sender.close();
            connection.close();
            //response.sendRedirect("ListNews");

        } catch (JMSException ex) {
            ex.printStackTrace();
        }
            
	return 0;
    }

   
    @Override
    public SocioResearchDTO updateResearchGrouped(SocioResearchDTO rDTO) {
        if (rDTO.getId() == 0){ // create new
            return rDTO;
        }

        SocioResearch research = null;
        try {
          research = em.find(SocioResearch.class, rDTO.getId());
          research.updateFromDTOGrouped(rDTO,em);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            
        }
        return rDTO;
    }

    @Override
    public VarDTO_Detailed generalizeVar(long var_id, ArrayList<Long> gen_var_ids,UserAccountDTO user) {
            VarDTO_Detailed detailed = null;
	    Var dsVar, detached;

	    try {
	      dsVar = em.find(Var.class, var_id);
	      dsVar.setGeneralized_var_ids(gen_var_ids);
	     // UserAccountDTO watching_user = (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
	      detailed = dsVar.toDTO_Detailed(user,em);
	    } finally {
	    }
	    
	return detailed;
    }

    @Override
    public long addOrgImpl(OrgDTO dto) {
          Organization org = null;
	  long org_id = 0;
	    try {
	      org = new Organization(dto);
	      em.persist(org);
	      org_id = org.getId();
	    } finally {
	    }
	 return org_id;
    }

    @Override
    public Boolean addFileToAccessor(long id_research, long id_file, String desc, String category) {
	    SocioResearch dsResearch, detached;
            try {
	      dsResearch = em.find(SocioResearch.class, id_research);
	      ResearchFilesDTO dto = dsResearch.toFilesDTO(em);
	      dto.addFile(category, desc, id_file);
	      dsResearch.updateFileAccessor(dto);
	    } finally {
	    }
	 return true;
    }

    @Override
    public Boolean deleteFileFromAccessor(long id_research, long id_file) {
	    SocioResearch dsResearch, detached;
	    try {
	      dsResearch = em.find(SocioResearch.class, id_research);
	      ResearchFilesDTO dto = dsResearch.toFilesDTO(em);
	      dto.deleteFile(id_file);
	      dsResearch.updateFileAccessor(dto);
	      return store.deleteFile(id_file);
	    } finally {
                return false;
	    }
    }

    @Override
    public Boolean addSSE(String clas, String kind, String value) {
       SingleStringEntity entity = null;
        try {
          // for this version of the app, just get hardwired 'default' user
          //UserAccount currentUser = UserAccount.getDefaultUser(); // detached object
          //currentUser = pm.makePersistent(currentUser); // attach
            if(value!=null && !value.equals(""))
            {
                List<SingleStringEntity> res = SingleStringEntity.getMatchingFull(em, clas, kind, value);
                if(res.isEmpty())
                {
                   entity = new SingleStringEntity();
                   entity.setClas(clas);
                   entity.setKind(kind);
                   entity.setContents(value);
                   em.persist(entity);
                }
            }
            return true;
        } finally {
            return false;
        }
    }

    @Override
    public Boolean updateFileAccessor(long research_id, ResearchFilesDTO dto) {
        SocioResearch research = null;
        ResearchFilesAccessor accessor;
        try {
          research = em.find(SocioResearch.class, research_id);
          accessor = em.find(ResearchFilesAccessor.class,research.getFile_accessor_id());
          accessor.updateFromDTO(dto);
          return true;
        } catch (Exception e) {
              e.printStackTrace();
        } finally {
            return false;
        }
    }

    @Override
    public MetaUnitMultivaluedEntityDTO getDatabankStructure(String db_name) {
        DatabankStructure db;
        try
        {
            db = em.find(DatabankStructure.class, db_name);
            MetaUnitMultivaluedEntity root = db.getRoot();
            MetaUnitMultivaluedEntityDTO dto = toMultivaluedEntityDTO(root,false);
            return dto;
        }
        finally
        {
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private MetaUnitMultivaluedStructureDTO toMultivaluedStructureDTO(MetaUnitMultivaluedStructure m)
    {
        MetaUnitMultivaluedStructureDTO dto = new MetaUnitMultivaluedStructureDTO();
        try
        {
            dto.setId(m.getId());
            dto.setDesc(m.getDescription());
            dto.setUnique_name(m.getUnique_name());
            dto.setIsCatalogizable(m.getIsCatalogizable()!=0);
            dto.setIsSplittingEnabled(m.getIsSplittingEnabled()!=0);
            
            ArrayList<Long> tagged_ent = new ArrayList<Long>();
            if(m.getTagged_entities()!=null)
            for(Long ent:m.getTagged_entities())
            {
                tagged_ent.add(ent);
            }
            dto.setTagged_entities(tagged_ent);
            ArrayList<MetaUnitDTO> sub_meta_units_dtos = new ArrayList<MetaUnitDTO>();
            if(m.getSub_meta_units()!= null)
            for(MetaUnit unit:m.getSub_meta_units())
            {
                if(unit != null)
                {
                    if(unit instanceof MetaUnitMultivaluedStructure)
                    {
                        MetaUnitDTO dt;
                        //DONE FOR SPLIITING DOWNLOAD OF DEEP TREE
                        if(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0)
                        {
                            dt = new MetaUnitMultivaluedStructureDTO(unit.getId(),unit.getUnique_name(),unit.getDescription());
                            ((MetaUnitMultivaluedDTO)dt).setIsCatalogizable(((MetaUnitMultivalued)unit).getIsCatalogizable()!=0);
                            ((MetaUnitMultivaluedDTO)dt).setIsSplittingEnabled(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0);
                        }
                        else
                        {
                            dt = toMultivaluedStructureDTO((MetaUnitMultivaluedStructure)unit);
                        }
                        if(dt!=null)
                        sub_meta_units_dtos.add(dt);
                    }
                    else if(unit instanceof MetaUnitMultivaluedEntity)
                    {
                        MetaUnitDTO dt;
                        //DONE FOR SPLIITING DOWNLOAD OF DEEP TREE
                        if(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0)
                        {
                            dt = new MetaUnitMultivaluedEntityDTO(unit.getId(),unit.getUnique_name(),unit.getDescription());
                            ((MetaUnitMultivaluedDTO)dt).setIsCatalogizable(((MetaUnitMultivalued)unit).getIsCatalogizable()!=0);
                            ((MetaUnitMultivaluedDTO)dt).setIsSplittingEnabled(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0);
                            ((MetaUnitMultivaluedEntityDTO)dt).setIsMultiselected(((MetaUnitMultivaluedEntity)unit).getIsMultiSelected()!=0);
                            Collection<MetaUnitEntityItem> items = ((MetaUnitMultivaluedEntity)unit).getItems();
                            ArrayList<String> names = new ArrayList<String>();
                            ArrayList<Long> ids = new ArrayList<Long>();
                            for(MetaUnitEntityItem item:items)
                            {
                                names.add(item.getValue());
                                ids.add(item.getId());
                            }
                            ((MetaUnitMultivaluedEntityDTO)dt).setItem_ids(ids);
                            ((MetaUnitMultivaluedEntityDTO)dt).setItem_names(names);
                        }
                        else
                        {
                            dt = toMultivaluedEntityDTO((MetaUnitMultivaluedEntity)unit,false);
                        }
                        if(dt!=null)
                        sub_meta_units_dtos.add(dt);
                    }
                    else        
                    {
                        sub_meta_units_dtos.add(unit.toDTO());
                    }
                }
            }
            dto.setSub_meta_units(sub_meta_units_dtos);

            return dto;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }finally
        {
            return dto;
        }
    }
    
    private MetaUnitMultivalued updateMultivaluedUnitFromDTO(MetaUnitDTO dto)
    {
        MetaUnitMultivalued struct = null;
        if(dto instanceof MetaUnitMultivaluedStructureDTO)
        {
            struct = em.find(MetaUnitMultivaluedStructure.class, dto.getId());
            if(struct == null)
            {
                struct = new MetaUnitMultivaluedStructure();
                em.persist(struct);
            }
        }
        if(dto instanceof MetaUnitMultivaluedEntityDTO)
        {
            struct = em.find(MetaUnitMultivaluedEntity.class, dto.getId());
             if(struct == null)
            {
                struct = new MetaUnitMultivaluedEntity();
                ((MetaUnitMultivaluedEntity)struct).setIsMultiSelected(((MetaUnitMultivaluedEntityDTO)dto).isIsMultiselected()?1:0);
                em.persist(struct);
            }
        }

            MetaUnitMultivaluedDTO m_dto = (MetaUnitMultivaluedDTO)dto;
            
            struct.setIsCatalogizable(m_dto.isIsCatalogizable()?1:0);
            struct.setIsSplittingEnabled(m_dto.isIsSplittingEnabled()?1:0);
            struct.setTagged_entities(m_dto.getTagged_entities());
            struct.setUnique_name(dto.getUnique_name());
            struct.setDescription(dto.getDesc());
            int index = 0;
            if(m_dto.getSub_meta_units()!=null)
            for(int j = 0;j< m_dto.getSub_meta_units().size();j++)
            {
                MetaUnitDTO dt = m_dto.getSub_meta_units().get(j);
                if(struct.sub_meta_units_contains(dt.getId()))
                {
                    MetaUnit m_unit = struct.sub_meta_units_get(dt.getId());
                    int index_old = struct.sub_meta_units_get_order(dt.getId());
                    if(index != index_old)
                    {
                        ArrayList<MetaUnit> u = new ArrayList<MetaUnit>();
                        for(MetaUnit un:struct.getSub_meta_units())
                        {
                            u.add(un);
                        }
                        MetaUnit m_unit_sw = u.get(index);
                        u.set(index, m_unit);
                        u.set(index_old, m_unit_sw);
                        struct.setSub_meta_units(u);
                    }
                    
                    m_unit.updateFromDTO(dt);
                    if(m_unit instanceof MetaUnitMultivalued)
                    {
                        updateMultivaluedUnitFromDTO(dt);
                    }
       
                }else
                {
                    MetaUnit new_unit = null;
                    
                    if(dt instanceof MetaUnitDateDTO){new_unit = new MetaUnitDate();new_unit.updateFromDTO(dt);}
                    if(dt instanceof MetaUnitDoubleDTO){new_unit = new MetaUnitDouble();new_unit.updateFromDTO(dt);}
                    if(dt instanceof MetaUnitIntegerDTO){new_unit = new MetaUnitInteger();new_unit.updateFromDTO(dt);}
                    if(dt instanceof MetaUnitStringDTO){new_unit = new MetaUnitString();new_unit.updateFromDTO(dt);}
                    if(dt instanceof MetaUnitFileDTO){new_unit = new MetaUnitFile();new_unit.updateFromDTO(dt);}
                    if(dt instanceof MetaUnitMultivaluedStructureDTO)
                        {new_unit = new MetaUnitMultivaluedStructure();new_unit.updateFromDTO(dt);updateMultivaluedUnitFromDTO(dt);}
                    if(dt instanceof MetaUnitMultivaluedEntityDTO)
                        {new_unit = new MetaUnitMultivaluedEntity();new_unit.updateFromDTO(dt);updateMultivaluedUnitFromDTO(dt);}
                    
                    struct.getSub_meta_units().add(new_unit);
                }
                index++;
            }
            em.merge(struct);
            return struct;
           // MetaUnitMultivaluedStructure str = em.find(MetaUnitMultivaluedStructure.class, dto.getId());
    }
    private MetaUnitEntityItemDTO toEntityItemDTO(MetaUnitEntityItem item)
    {
        MetaUnitEntityItemDTO dto = new MetaUnitEntityItemDTO(item.getId(), item.getValue(), item.getMapped_values());
        ArrayList<Long> ent_ids = new ArrayList<Long>();
        for(Long id:item.getTagged_entities_ids())
        {
            ent_ids.add(id);
        }
        
        ArrayList<String> ent_idents = new ArrayList<String>();
        for(String ident:item.getTagged_entities_identifiers())
        {
            ent_idents.add(ident);
        }
        dto.setTagged_entities_ids(ent_ids);
        dto.setTagged_entities_identifiers(ent_idents);
        
        ArrayList<MetaUnitEntityItemDTO> subitems = new ArrayList<MetaUnitEntityItemDTO>();
        if(item.getSubitems()!=null)
            for(MetaUnitEntityItem it:item.getSubitems())
            {
                subitems.add(toEntityItemDTO(it));
            }
        dto.setSubitems(subitems);
        return dto;
    }
        private MetaUnitEntityItemDTO toEntityItemDTO_Light(MetaUnitEntityItem item)
    {
        MetaUnitEntityItemDTO dto = new MetaUnitEntityItemDTO(item.getId(), item.getValue(), item.getMapped_values());
        ArrayList<Long> ent_ids = new ArrayList<Long>();
        if(item.getTagged_entities_ids()!=null)
        for(Long id:item.getTagged_entities_ids())
        {
            ent_ids.add(id);
        }
        
        ArrayList<String> ent_idents = new ArrayList<String>();
         if(item.getTagged_entities_identifiers()!=null)
        for(String ident:item.getTagged_entities_identifiers())
        {
            ent_idents.add(ident);
        }
        dto.setTagged_entities_ids(ent_ids);
        dto.setTagged_entities_identifiers(ent_idents);
        
        ArrayList<MetaUnitEntityItemDTO> subitems = new ArrayList<MetaUnitEntityItemDTO>();
        dto.setSubitems(subitems);
        return dto;
    }
    private ArrayList<MetaUnitEntityItemDTO> toEntitySubitemsDTOs(MetaUnitEntityItem item)
    {
        ArrayList<MetaUnitEntityItemDTO> subitems = new ArrayList<MetaUnitEntityItemDTO>();
        if(item.getSubitems()!=null)
            for(MetaUnitEntityItem it:item.getSubitems())
            {
                subitems.add(toEntityItemDTO_Light(it));
            }
        return subitems;
    }    
    private MetaUnitMultivaluedEntityDTO toMultivaluedEntityDTO(MetaUnitMultivaluedEntity m,boolean flattened_items)
    {
        MetaUnitMultivaluedEntityDTO dto = new MetaUnitMultivaluedEntityDTO();
        try
        {
            dto.setId(m.getId());
            dto.setDesc(m.getDescription());
            dto.setUnique_name(m.getUnique_name());
            dto.setIsCatalogizable(m.getIsCatalogizable()!=0);
            dto.setIsSplittingEnabled(m.getIsSplittingEnabled()!=0);
            dto.setIsMultiselected(m.getIsMultiSelected()!=0);
            Collection<MetaUnitEntityItem> items = m.getItems();
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<Long> ids = new ArrayList<Long>();
            for(MetaUnitEntityItem item:items)
            {
                names.add(item.getValue());
                ids.add(item.getId());
                if(item.getSubitems()!=null && item.getSubitems().size()>0 && flattened_items)
                {
                    for(MetaUnitEntityItem it:item.getSubitems())
                    {
                        names.add(".  "+it.getValue());
                        ids.add(it.getId());
                    }
                }
            }
            dto.setItem_ids(ids);
            dto.setItem_names(names);
            ArrayList<Long> tagged_ent = new ArrayList<Long>();
            if(m.getTagged_entities()!=null)
            for(Long ent:m.getTagged_entities())
            {
                tagged_ent.add(ent);
            }
            dto.setTagged_entities(tagged_ent);
            ArrayList<MetaUnitDTO> sub_meta_units_dtos = new ArrayList<MetaUnitDTO>();
            if(m.getSub_meta_units()!= null)
            for(MetaUnit unit:m.getSub_meta_units())
            {
                if(unit != null)
                {
                    if(unit instanceof MetaUnitMultivaluedEntity)
                    {
                        MetaUnitDTO dt;
                        //DONE FOR SPLIITING DOWNLOAD OF DEEP TREE
                        if(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0)
                        {
                            dt = new MetaUnitMultivaluedEntityDTO(unit.getId(),unit.getUnique_name(),unit.getDescription());
                            ((MetaUnitMultivaluedDTO)dt).setIsCatalogizable(((MetaUnitMultivalued)unit).getIsCatalogizable()!=0);
                            ((MetaUnitMultivaluedDTO)dt).setIsSplittingEnabled(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0);
                            ((MetaUnitMultivaluedEntityDTO)dt).setIsMultiselected(((MetaUnitMultivaluedEntity)unit).getIsMultiSelected()!=0);
                             Collection<MetaUnitEntityItem> items2 = ((MetaUnitMultivaluedEntity)unit).getItems();
                            ArrayList<String> names2 = new ArrayList<String>();
                            ArrayList<Long> ids2 = new ArrayList<Long>();
                            for(MetaUnitEntityItem item:items2)
                            {
                                names2.add(item.getValue());
                                ids2.add(item.getId());
                            }
                            ((MetaUnitMultivaluedEntityDTO)dt).setItem_ids(ids2);
                            ((MetaUnitMultivaluedEntityDTO)dt).setItem_names(names2);

                            
                        }
                        else
                        {
                            dt = toMultivaluedEntityDTO((MetaUnitMultivaluedEntity)unit,flattened_items);
                        }
                        if(dt!=null)
                        sub_meta_units_dtos.add(dt);
                    }else
                    if(unit instanceof MetaUnitMultivaluedStructure)
                    {
                        MetaUnitDTO dt;
                        //DONE FOR SPLIITING DOWNLOAD OF DEEP TREE
                        if(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0)
                        {
                            dt = new MetaUnitMultivaluedStructureDTO(unit.getId(),unit.getUnique_name(),unit.getDescription());
                            ((MetaUnitMultivaluedDTO)dt).setIsCatalogizable(((MetaUnitMultivalued)unit).getIsCatalogizable()!=0);
                            ((MetaUnitMultivaluedDTO)dt).setIsSplittingEnabled(((MetaUnitMultivalued)unit).getIsSplittingEnabled()!=0);
                        }
                        else
                        {
                            dt = toMultivaluedStructureDTO((MetaUnitMultivaluedStructure)unit);
                        }
                        if(dt!=null)
                        sub_meta_units_dtos.add(dt);
                    }else
                    {
                        sub_meta_units_dtos.add(unit.toDTO());
                    }
                }
            }
            dto.setSub_meta_units(sub_meta_units_dtos);

            return dto;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }finally
        {
            return dto;
        }
    }

    @Override
    public MetaUnitMultivaluedStructureDTO getMetaUnitMultivaluedStructureDTO(long id) {
      MetaUnitMultivaluedStructure unit;
        try
        {
            unit = em.find(MetaUnitMultivaluedStructure.class, id);
            MetaUnitMultivaluedStructureDTO dto = toMultivaluedStructureDTO(unit);
            return dto;
        }
        finally
        {
        }
    }

    @Override
    public Boolean addMetaUnit(long parent_id, MetaUnitDTO dto) {
        MetaUnitMultivalued unit;
        MetaUnit new_unit = null;
        if(dto instanceof MetaUnitDateDTO){new_unit = new MetaUnitDate();new_unit.updateFromDTO(dto);}
        if(dto instanceof MetaUnitDoubleDTO){new_unit = new MetaUnitDouble();new_unit.updateFromDTO(dto);}
        if(dto instanceof MetaUnitIntegerDTO){new_unit = new MetaUnitInteger();new_unit.updateFromDTO(dto);}
        if(dto instanceof MetaUnitStringDTO){new_unit = new MetaUnitString();new_unit.updateFromDTO(dto);}
        if(dto instanceof MetaUnitFileDTO){new_unit = new MetaUnitFile();new_unit.updateFromDTO(dto);}
        if(dto instanceof MetaUnitMultivaluedStructureDTO){new_unit= updateMultivaluedUnitFromDTO(dto);}
        if(dto instanceof MetaUnitMultivaluedEntityDTO){new_unit= updateMultivaluedUnitFromDTO(dto);}
        
        try
        {
            unit = em.find(MetaUnitMultivalued.class, parent_id);    
            unit.getSub_meta_units().add(new_unit);
            em.persist(unit);
            return true;
        }
        finally
        {
        }
    }



    @Override
    public void updateMetaUnitStructure(MetaUnitDTO dto) {
        MetaUnit unit;
        try
        {
            unit = em.find(MetaUnit.class, dto.getId());    
            unit.updateFromDTO(dto);
            if(unit instanceof MetaUnitMultivalued) updateMultivaluedUnitFromDTO(dto);
            em.persist(unit);
            //return true;
        }
        finally
        {
        }
    }

    @Override
    public void addEntityItem(Long entity_id, String value, HashMap<String, String> filling) {
        MetaUnitMultivaluedEntity entity = em.find(MetaUnitMultivaluedEntity.class, entity_id);
        MetaUnitEntityItem item = new MetaUnitEntityItem(value);
        item.setMapped_values(filling);
        entity.getItems().add(item);
        em.persist(entity);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteMetaUnit(Long id, Long unit_parent_id) {
        MetaUnit unit;
        MetaUnitMultivalued punit;
        try
        {
            unit = em.find(MetaUnit.class, id);    
            punit = em.find(MetaUnitMultivalued.class, unit_parent_id);
            punit.getSub_meta_units().remove(unit);
            em.persist(punit);
            em.remove(unit);
        }
        finally
        {
        }
    }

    @Override
    public void editEntityItem(Long id, String value, HashMap<String, String> filling) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id);
        item.setValue(value);
        item.setMapped_values(filling);
    }

    @Override
    public HashMap<String, String> getEntityItem(Long id) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id);
        return item.getMapped_values();
    }

    @Override
    public MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO(long id) {
        MetaUnitMultivaluedEntity unit = em.find(MetaUnitMultivaluedEntity.class,id);
        return toMultivaluedEntityDTO(unit,false);
    }

    @Override
    public void deleteEntityItem(Long id, Long entity_id) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id);
        MetaUnitMultivaluedEntity ent = em.find(MetaUnitMultivaluedEntity.class, entity_id);
        ent.getItems().remove(item);
        em.persist(ent);
        em.remove(item);
    }

    @Override
    public ArrayList<String> getEntityItemSubitemsNames(Long id_item) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id_item);
        Collection<MetaUnitEntityItem> items = item.getSubitems();
        ArrayList<String> arr = new ArrayList<String>();
        for(MetaUnitEntityItem it:items)
        {
            arr.add(it.getValue());
        }
        return arr;
    }

    @Override
    public ArrayList<Long> getEntityItemSubitemsIDs(Long id_item) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id_item);
        Collection<MetaUnitEntityItem> items = item.getSubitems();
        ArrayList<Long> arr = new ArrayList<Long>();
        for(MetaUnitEntityItem it:items)
        {
            arr.add(it.getId());
        }
        return arr;
    }

    @Override
    public ArrayList<Long> getEntityItemTaggedEntitiesIDs(Long id_item) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id_item);
        List<Long> ids = item.getTagged_entities_ids();
        ArrayList<Long> arr = new ArrayList<Long>();
        for(Long id:ids)
        {
            arr.add(id);
        }
        return arr;
    }

    @Override
    public ArrayList<String> getEntityItemTaggedEntitiesIdentifiers(Long id_item) {
        MetaUnitEntityItem item = em.find(MetaUnitEntityItem.class,id_item);
        List<String> ids = item.getTagged_entities_identifiers();
        ArrayList<String> arr = new ArrayList<String>();
        for(String id:ids)
        {
            arr.add(id);
        }
        return arr;
    }

    @Override
    public void addSubEntityItem(Long parent_id, String value, HashMap<String, String> filling) {
        MetaUnitEntityItem p_item = em.find(MetaUnitEntityItem.class, parent_id);
        MetaUnitEntityItem item = new MetaUnitEntityItem(value);
        item.setMapped_values(filling);
        p_item.getSubitems().add(item);
        em.persist(p_item);
    }

    @Override
    public MetaUnitEntityItemDTO getEntityItemDTO(Long id) {
        MetaUnitEntityItem p_item = em.find(MetaUnitEntityItem.class, id);
        return toEntityItemDTO_Light(p_item);
    }

    @Override
    public ArrayList<MetaUnitEntityItemDTO> getEntityItemSubitemsDTOs(Long id) {
         MetaUnitEntityItem p_item = em.find(MetaUnitEntityItem.class, id);
        return toEntitySubitemsDTOs(p_item);
    }

    @Override
    public MetaUnitMultivaluedEntityDTO getMetaUnitMultivaluedEntityDTO_FlattenedItems(long id) {
        MetaUnitMultivaluedEntity unit = em.find(MetaUnitMultivaluedEntity.class,id);
        return toMultivaluedEntityDTO(unit,true);
    }

    @Override
    public void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO old,MetaUnitEntityItemDTO nev){
        
        MetaUnitEntityItem item_old = em.find(MetaUnitEntityItem.class,old.getId());
        item_old.setTagged_entities_identifiers(old.getTagged_entities_identifiers());
        item_old.setTagged_entities_ids(old.getTagged_entities_ids());
        em.persist(item_old);
        
        MetaUnitEntityItem item_new = em.find(MetaUnitEntityItem.class,nev.getId());
        item_new.setTagged_entities_identifiers(nev.getTagged_entities_identifiers());
        item_new.setTagged_entities_ids(nev.getTagged_entities_ids());
        em.persist(item_new);
        
    }

    @Override
    public void updateMetaUnitEntityItemLinks(MetaUnitEntityItemDTO dto) {
         MetaUnitEntityItem item_old = em.find(MetaUnitEntityItem.class,dto.getId());
        item_old.setTagged_entities_identifiers(dto.getTagged_entities_identifiers());
        item_old.setTagged_entities_ids(dto.getTagged_entities_ids());
        em.persist(item_old);
    }
    
    
}
