/** 
 * Copyright 2010 Daniel Guermeur and Amy Unruh
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   See http://connectrapp.appspot.com/ for a demo, and links to more information 
 *   about this app and the book that it accompanies.
 */
package com.mresearch.databank.server;


import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.mplatforma.amr.service.remote.AdminSocioResearchBeanRemote;
import com.mplatforma.amr.service.remote.UserSocioResearchBeanRemote;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.UserAccountDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

/**
 * the FriendsService communicates Friend data via RPC between client and server.
 */
@SuppressWarnings("serial")
public class AdminSocioResearchServiceImpl extends RemoteServiceServlet implements
    AdminSocioResearchService {

	
	private static AdminSocioResearchBeanRemote eao;
	static
	{
		Object obj = new String("some");
		try {
		  InitialContext ic = new InitialContext();
		  System.out.println("start lookup");
		  final String jndiName = "java:global/DatabankEnterprise-ejb/AdminSocioResearchRemoteBean";
		  obj = ic.lookup(jndiName);
		  System.out.println("lookup returned " + obj);
		  eao = (AdminSocioResearchBeanRemote) obj;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Boolean deleteResearch(long id) {
		return eao.deleteResearch(id);
	}


	@Override
	public SocioResearchDTO updateResearch(SocioResearchDTO research) {
		return eao.updateResearch(research);
	}

	@Override
	public VarDTO_Detailed generalizeVar(long var_id,
			ArrayList<Long> gen_var_ids) {
	    UserAccountDTO watching_user = (UserAccountDTO) this.getThreadLocalRequest().getSession().getAttribute("user");
		return eao.generalizeVar(var_id, gen_var_ids,watching_user);
	}
	

	@Override
	public SocioResearchDTO updateResearchGrouped(SocioResearchDTO research) {
		return eao.updateResearchGrouped(research);
	}

	
	
	@Override
	public void parseSPSS(long blobkey, long length) {
		 eao.parseSPSS(blobkey, length);
	}

	
	@Override
	public String addOrgImpl(OrgDTO dto) {
		return eao.addOrgImpl(dto);
	}

	@Override
	public Boolean addFileToAccessor(long id_research, long id_file,
			String desc, String category) {
		return eao.addFileToAccessor(id_research, id_file, desc, category);
	}

	@Override
	public Boolean deleteFileFromAccessor(long id_research, long id_file) {
		return eao.deleteFileFromAccessor(id_research, id_file);
	}

	@Override
	public Boolean addSSE(String clas, String kind, String value) {
		return eao.addSSE(clas, kind, value);
	}

	
	@Override
	public Boolean updateFileAccessor(long research_id, ResearchFilesDTO dto) {
		return eao.updateFileAccessor(research_id, dto);
	}

	
	
	
/*	
  public FriendsServiceImpl() {
    AppMisc.populateDataStoreOnce();
  }
*/
} // end class
