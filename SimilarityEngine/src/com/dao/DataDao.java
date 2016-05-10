package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.model.KnowledgeResponse;
import com.model.PerformanceMetricDescriptor;
import com.model.SimilarityTableResponse;
import com.model.TableEntry;
import com.model.ViableTopologyDistribution;
import com.model.Workload;
  
/*
 * File: DataDao.java
 * Definition of the function invoked by the  Data Services layer and that interact with the database. 
 * Check DataDaoImpl.java for details 
 * */

public interface DataDao {  
  
	public TreeMap<Integer, Float> getSimilarTopologiesIDs(ArrayList<Integer> listID,
		 ArrayList<PerformanceMetricDescriptor> queryDescriptors) throws Exception;

	public float getEntrySimValue(String row, String column) throws Exception;

	public TreeMap<Integer, Float> getWorkloadSim(ArrayList<Integer> listID, Workload workloadCAse) throws Exception;
 
	public TreeMap<Integer, List<TableEntry>> getSimilarityTableWorkload()
		 throws Exception;

	public List<ViableTopologyDistribution> getViaTopologyURL(ArrayList<Integer> listID) throws Exception;

	public List<KnowledgeResponse> getAppKnowledge(int id) throws Exception;

	public int persistKnowledge(int id, Workload w, List<PerformanceMetricDescriptor> lp,
			ViableTopologyDistribution viaT)
			throws Exception;

	public List<SimilarityTableResponse> getSimilarityTables() throws Exception;

	public int updateSimilarityTables(int idSimTable, ArrayList<TableEntry> lEntries) throws Exception;

	public int updateKnowledge(int idApp, ArrayList<PerformanceMetricDescriptor> lPm, Workload w) throws Exception;

	public int createSimilarityTable(String nameT, ArrayList<TableEntry> lEntries) throws Exception;

}  
  

