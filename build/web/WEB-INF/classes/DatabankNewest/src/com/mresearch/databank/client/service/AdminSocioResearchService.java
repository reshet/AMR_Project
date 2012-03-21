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
package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

@RemoteServiceRelativePath("adminResearchService")
public interface AdminSocioResearchService extends RemoteService {

  //ArrayList<FriendSummaryDTO> getFriendSummaries();

  Boolean deleteResearch(long id);


  SocioResearchDTO updateResearch(SocioResearchDTO research);
  VarDTO_Detailed generalizeVar(long var_id, ArrayList<Long> gen_var_ids);
  SocioResearchDTO updateResearchGrouped(SocioResearchDTO research);

  void parseSPSS(long blobkey, long length);

  String addOrgImpl(OrgDTO dto);

  Boolean addFileToAccessor(long id_research,long id_file,String desc,String category);
  Boolean deleteFileFromAccessor(long id_research,long id_file);
  Boolean addSSE(String clas,String kind,String value);
  Boolean updateFileAccessor(long research_id,ResearchFilesDTO dto);

  //ArrayList<E>
//  SocioResearchDTO addResearch(SocioResearchDTO research);
}
