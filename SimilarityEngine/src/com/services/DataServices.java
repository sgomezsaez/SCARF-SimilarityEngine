package com.services;

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
 * File: DataServices.java
 * Definition of the services invoked by the  REST API (RestController.java)
 * */

public interface DataServices {  

	/*
	 * @param listID: List of IDs of functionally similar applications (returned
	 * by PERTOS)
	 * 
	 * @param queryDescriptors: List of performance metrics of the case being
	 * queried This function invokes the methods of the DAO that computes the
	 * similarity value of the performance between the metrics described in the
	 * list queryDescriptors and the metrics of the applications with IDs
	 * contained in the variable listID
	 */
	public TreeMap<Integer, Float> getSimilarTopologiesIDs(ArrayList<Integer> listID,
		 ArrayList<PerformanceMetricDescriptor> queryDescriptors) throws Exception;

	/*
	 * @param row: Name of the similarity table row (e.g. gamma)
	 * 
	 * @param column: Name of the similarity able column (e.g. normal) This
	 * function invokes the methods of the DAO that obtains the similarity value
	 * of the entry with column and row names specified as parameters (e.g.
	 * obtains the similarity value of the entry with column name normal and row
	 * name gamma)
	 */

	public float getEntrySimValue(String row, String column) throws Exception;

	/*
	 * @param listID: List of IDs of functionally similar applications (returned
	 * by PERTOS)
	 * 
	 * @param workloadCase: List of workload characteristics of the case being
	 * queried This function invokes the methods of the DAO that computes the
	 * similarity value of the workload between the characteristics described in
	 * the list queryDescriptorsobject workload Case and the characteristics of
	 * the applications with IDs contained in the variable listID
	 */
	public TreeMap<Integer, Float> getWorkloadSim(ArrayList<Integer> listID, Workload workloadCase) 
		 throws Exception;

	/*
	 * @param listID: List of applications
	 * 
	 * This function invokes the methods of the DAO that obtains the stored URLs
	 * (URL of Winery repository) of the viable distributions of the
	 * applications specified by the IDs stored in the list received as
	 * parameter.
	 */

	public List<ViableTopologyDistribution> getViaTopologyURL(ArrayList<Integer> listID) throws Exception;

	/*
	 * @param listID: List of applications
	 * 
	 * This function invokes the methods of the DAO that obtains the stored URLs
	 * (URL of Winery repository) of the viable distributions of the
	 * applications specified by the IDs stored in the list received as
	 * parameter.
	 */

	public List<KnowledgeResponse> getAppKnowledge(int id) throws Exception;

	/*
	 * @param id: ID of the application to be stored (obtained from PERTOS for
	 * matching reasons)
	 * 
	 * @param w: Workload of the application to be stored
	 * 
	 * @param lp: List of performance metrics descriptors to be stored
	 * 
	 * @param viaT: Name of the solutions and URLS in the repository of Winery
	 * of the viable distributions of the application
	 * 
	 * This function invokes the methods of the DAO that stores in the database
	 * the performance, workload and solution of a depicted application.
	 */

	public int persistKnowledge(int id, Workload w, List<PerformanceMetricDescriptor> lp,
			ViableTopologyDistribution viaT)
			throws Exception;

	/*
	 * This function invokes the methods of the DAO that retrieves from the
	 * database the information of the similarity tables.
	 */
	public List<SimilarityTableResponse> getSimilarityTables() throws Exception;

	/*
	 * This function invokes the methods of the DAO that updates the similarity
	 * value of the table entries contained in the list sent as parameter
	 */
	public int updateSimilarityTables(int idSimTable, ArrayList<TableEntry> lEntries) throws Exception;

	/*
	 * This function invokes the methods of the DAO that updates the knowledge
	 * of an app
	 */

	public int updateKnowledge(int idApp, ArrayList<PerformanceMetricDescriptor> lPm, Workload w) throws Exception;

	public int createSimilarityTable(String nameT, ArrayList<TableEntry> lEntries) throws Exception;

}  
