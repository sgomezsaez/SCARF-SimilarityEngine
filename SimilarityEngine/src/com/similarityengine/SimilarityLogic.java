package com.similarityengine;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.model.PerformanceMetricDescriptor;
import com.model.TableEntry;
import com.model.Workload;

/*
 * Class: SimilarityLogic.java
 * This class implements functions that compute the resulting Performance and Workload
 * similarities
 * */
public class SimilarityLogic {
	
	/*
	 * @param lMetricDescCB: List of metric descriptor of a stored case
	 * 
	 * @param lMetricDescQuery: List of metric descriptors of the case being
	 * queried
	 * 
	 * This function calls the local similarity functions that compute the
	 * similarity between two single metric descriptors, adds them to a new list
	 * and sends them to the function that computes the global similarity
	 * function.
	 */
	public static float computeSimilarityPerformance(
			List<PerformanceMetricDescriptor> lMetricDescCB,
			List<PerformanceMetricDescriptor> lMetricDescQuery){
		
		float similarity=-1;
		// List that stores the computed similarity values
		List<Float> lLocalSim = new ArrayList <Float>();
		// If the lists are of the same size then iterate over them (elements
		// are order by metric
		// ID, the foreign key that tells to which metric the element
		// corresponds)
		if(lMetricDescCB.size() == lMetricDescQuery.size()){
			for(int i=0; i<lMetricDescQuery.size();i++){
				
				// If the metrics are the same then compute local similarity
				if (lMetricDescCB.get(i).getFk_metric_id() == lMetricDescQuery.get(i).getFk_metric_id()) {
					// The result is added to the local a list
					lLocalSim.add(LocalSimilarity.computeLocalSimMetric(lMetricDescCB.get(i), lMetricDescQuery.get(i)));
				}
			}
			// If the lists are of different size (this is done to avoid null
			// pointer exception
		} else if (lMetricDescCB.size() < lMetricDescQuery.size()) {

			for (int i = 0; i < lMetricDescCB.size(); i++) {
				// As before, compute local similarity if they correspond to the
				// same metric
				if (lMetricDescCB.get(i).getFk_metric_id() == lMetricDescQuery.get(i).getFk_metric_id()) {

					lLocalSim.add(LocalSimilarity.computeLocalSimMetric(lMetricDescCB.get(i), lMetricDescQuery.get(i)));
				} else {
					for (int k = 0; k < lMetricDescQuery.size(); k++) {
						if (lMetricDescCB.get(i).getFk_metric_id() == lMetricDescQuery.get(k).getFk_metric_id()) {

							lLocalSim.add(LocalSimilarity.computeLocalSimMetric(lMetricDescCB.get(i),
									lMetricDescQuery.get(k)));
						}
					}

				}
			}

		}
		// The list with local similarity values is send as parameter to
		// compute the global similarity
		// and obtain the performance similarity
		similarity = GlobalSimilarity.computeSingleGlobalSimilarity(lLocalSim);
		return similarity;
		
	}
	
	/*
	 * @param listWorkloadCB: Workloads of a retrieved case
	 * 
	 * @param workloadCase: Workload of the case being queried
	 * 
	 * @param LTables: List of Similarity tables
	 * 
	 * This function iterates over the list of workloads of ONE cases retrieved
	 * from the CB and computes similarity between them, for the moment only the
	 * first one is taken into consideration (implemented like this because an
	 * app can have many workloads)
	 */

	public static float computeSimilarityWorkload(
List<Workload> listWorkloadCB, Workload workloadCase,
			TreeMap<Integer, List<TableEntry>> lTables) throws Exception {
		
		float similarity=-1;		
		List<Float> lLocalSim = new ArrayList <Float>();
		
		for(int i=0; i<listWorkloadCB.size();i++){
			
			lLocalSim.add(LocalSimilarity.computeLocalWorkloadSim(listWorkloadCB.get(i), workloadCase, lTables));
		}
		if (lLocalSim.size()>0)	
			return lLocalSim.get(0);
		
		return similarity;
	}
	
		
}
