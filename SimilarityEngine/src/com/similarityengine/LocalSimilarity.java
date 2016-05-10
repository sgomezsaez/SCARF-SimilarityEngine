package com.similarityengine;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.model.PerformanceMetricDescriptor;
import com.model.TableEntry;
import com.model.Workload;

/*
 * Class: LocalSimilarity.java
 * This class implements statics methods that compute the different local similarity
 * measures that are being employed
 * 
 * */
public class LocalSimilarity {
	// This constant is used to identify not specified values
	final static float UNDEFINED = -200;

	/*
	 * @param caseCB: value of a retrieved case
	 * 
	 * @param query: value of the queried case This function implements the
	 * difference and similarity functions specified in the chapter of concept
	 * and design of the thesis between two continuos values
	 */
	public static float computeSingleLocalSim(Float caseCB, Float query){

		float difference = 0;
		float similarity =-1;
		// Some validations
		if (caseCB != null && query != null && caseCB != UNDEFINED && query != UNDEFINED) {

			// Applying the defined rules for the difference function
			if (caseCB > 0  && query > 0 ){
				difference = (float) (Math.log(caseCB) - Math.log(query));  			
			}
			else if(caseCB < 0  && query < 0){
				difference = (float) (-1*(Math.log((caseCB*-1))) + Math.log((query*-1)));			
			} else if (caseCB == 0 || query == 0) {
					difference = caseCB - query;
			}else{
				// -1 is for undefined, this should be unreacheable in any case
				// since
				// validation for undefined values are done previously but
				// anyway
				difference = -1;			
			}
			
			// If a value of difference exists then apply similarity function
			if(difference != -1){
				similarity = (float) Math.exp(-1* (Math.abs(difference)));
				
			}	
		}

		return similarity;
	}
	
	/*
	 * @param caseCB: Performance metric descriptors of a retrieved case
	 * 
	 * @param query: Performance query descriptors of the queried case This
	 * function returns the similarity value for a single metric. Per each
	 * descriptor the function computeSingleLocalSim is called, the results are
	 * added to a list and finally aggregated using the global measure
	 */
	public static float computeLocalSimMetric(PerformanceMetricDescriptor caseCB, 
											  PerformanceMetricDescriptor query){
		float sim =-1;
		
		ArrayList<Float> lSimList = new ArrayList<Float>();
		// Computing similarity per each single value
		lSimList.add(computeSingleLocalSim(caseCB.min,query.min));
		lSimList.add(computeSingleLocalSim(caseCB.max,query.max));
		lSimList.add(computeSingleLocalSim(caseCB.mean,query.mean));
		lSimList.add(computeSingleLocalSim(caseCB.st_deviation,query.st_deviation));
		
		// Aggregating every value through the global function
		sim = GlobalSimilarity.computeSingleGlobalSimilarity(lSimList);		
		return sim;
	}
	
	/*
	 * @param workloadCase: Workload of a retrieved case
	 * 
	 * @param workloadQuery: Workload of the case being queried
	 * 
	 * @param lTables: List of similarity tables
	 * 
	 * This functions calculates the similarity between two workloads, the
	 * similarity tables are used when required, otherwise just the model for
	 * continuous values is applied
	 */
	public static float computeLocalWorkloadSim (Workload workloadCase, Workload workloadQuery, 
			TreeMap<Integer,List<TableEntry>> lTables) throws Exception{
		float sim =-1;
		
		//DataServices dataServices = new DataServicesImpl();
		ArrayList<Float> lSimList = new ArrayList<Float>();
		
		// COmputing similarity with similarity table
	    lSimList.add(computeSingleLocalSimTable(workloadCase.getPattern(), 
	    		workloadQuery.getPattern(),lTables.get(1)));
	    lSimList.add(computeSingleLocalSimTable(workloadCase.getArrival_rate(), 
	    		workloadQuery.getArrival_rate(),lTables.get(2)));
	    lSimList.add(computeSingleLocalSimTable(workloadCase.getBehavioral_model(), 
	    		workloadQuery.getBehavioral_model(),lTables.get(3)));
		// Computing similarity for continuous values
		lSimList.add(computeSingleLocalSim(workloadCase.getAvg_usr_number(),workloadQuery.getAvg_usr_number()));
		lSimList.add(computeSingleLocalSim(workloadCase.getAvg_transactions_second(),workloadQuery.getAvg_transactions_second()));
		// Computing global similarity to obtain single value
		sim = GlobalSimilarity.computeSingleGlobalSimilarity(lSimList);
		
		return sim;		
		
	}
	
	/*
	 * @param column: Name of the column of the sim table
	 * 
	 * @param row: Name of the row of the sim table
	 * 
	 * @param simTable: Similarity Table being used
	 * 
	 * This functions return the similarity value for a row and a colummn
	 * 
	 */
	public static float computeSingleLocalSimTable(String column, String row,
			List<TableEntry> simTable){
		for (int i=0; i<simTable.size(); i++){
			TableEntry entry = simTable.get(i);
			if (entry.getColumn_name().equalsIgnoreCase(column) &&
					entry.getRow_name().equals(row)){
				
				return entry.similarity_measure;				
			}
		}
		return -1;
	}
}
