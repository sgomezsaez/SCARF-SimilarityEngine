package com.services;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.DataDao;
import com.model.KnowledgeResponse;
import com.model.PerformanceMetricDescriptor;
import com.model.SimilarityTableResponse;
import com.model.TableEntry;
import com.model.ViableTopologyDistribution;
import com.model.Workload;  
  
/*
 * File: DataServicesImpl.java
 * Implementation of the services supported by the  REST API, check files DataServices.java for
 * more details of the functions.
 * */

public class DataServicesImpl implements DataServices {  
  
 @Autowired  
 DataDao dataDao;  
   

 @Override
 public TreeMap<Integer,Float> getSimilarTopologiesIDs(ArrayList<Integer> listID, 
		 ArrayList<PerformanceMetricDescriptor> queryDescriptors) throws Exception{
	 return dataDao.getSimilarTopologiesIDs(listID, queryDescriptors);	 
 }
 
 @Override
 public float getEntrySimValue (String row, String column) throws Exception{
	 return dataDao.getEntrySimValue(row, column);
 }
 
 @Override
public TreeMap<Integer,Float> getWorkloadSim(ArrayList<Integer> listID, Workload workloadCase) throws Exception{
	 return dataDao.getWorkloadSim(listID, workloadCase);
 }

	@Override
	public List<ViableTopologyDistribution> getViaTopologyURL(ArrayList<Integer> listID) throws Exception {
		return dataDao.getViaTopologyURL(listID);
	}

	@Override
	public List<KnowledgeResponse> getAppKnowledge(int id) throws Exception {
		return dataDao.getAppKnowledge(id);
	}

	@Override
	public int persistKnowledge(int id, Workload w, List<PerformanceMetricDescriptor> lp,
			ViableTopologyDistribution viaT)
			throws Exception {
		return dataDao.persistKnowledge(id, w, lp, viaT);

	}

	@Override
	public List<SimilarityTableResponse> getSimilarityTables() throws Exception {
		return dataDao.getSimilarityTables();
	}

	@Override
	public int updateSimilarityTables(int idSimTable, ArrayList<TableEntry> lEntries) throws Exception {
		return dataDao.updateSimilarityTables(idSimTable, lEntries);
	}

	@Override
	public int updateKnowledge(int idApp, ArrayList<PerformanceMetricDescriptor> lPm, Workload w) throws Exception {
		return dataDao.updateKnowledge(idApp, lPm, w);

	}

	@Override
	public int createSimilarityTable(String nameT, ArrayList<TableEntry> lEntries) throws Exception {
		return dataDao.createSimilarityTable(nameT, lEntries);

	}
}  