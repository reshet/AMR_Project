package com.mresearch.databank.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.ResearchFilesDTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.SocioResearchFilesDTO;
import com.mresearch.databank.shared.VarDTO_Detailed;

public interface AdminSocioResearchServiceAsync {

	void updateResearch(SocioResearchDTO research,
			AsyncCallback<SocioResearchDTO> callback);
	void deleteResearch(long id, AsyncCallback<Boolean> callback);
	void parseSPSS(long blobkey, long length, AsyncCallback<Void> callback);
	void addOrgImpl(OrgDTO dto, AsyncCallback<String> callback);
	void updateResearchGrouped(SocioResearchDTO research,
			AsyncCallback<SocioResearchDTO> callback);
	void generalizeVar(long var_id, ArrayList<Long> gen_var_ids,
			AsyncCallback<VarDTO_Detailed> callback);
	void addSSE(String clas, String kind, String value,
			AsyncCallback<Boolean> callback);
	void updateFileAccessor(long research_id, ResearchFilesDTO dto,
			AsyncCallback<Boolean> callback);
	void addFileToAccessor(long id_research, long id_file, String desc,
			String category, AsyncCallback<Boolean> callback);
	void deleteFileFromAccessor(long id_research, long id_file,
			AsyncCallback<Boolean> callback);
	
	
}
