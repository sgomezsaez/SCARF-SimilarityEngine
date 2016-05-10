package com.controller;
/*
 * File: RestController.java
 * Description:  This file contains implementation of the functions that define the request supported by the API.
 * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.model.KnowledgeResponse;
import com.model.PerformanceMetricDescriptor;
import com.model.SimilarityResponse;
import com.model.SimilarityTableResponse;
import com.model.TableEntry;
import com.model.ViableTopologyDistribution;
import com.model.Workload;
import com.services.DataServices;
import com.similarityengine.GlobalSimilarity;
import com.utils.ParserXML;
  
@Controller  
@RequestMapping("/")
public class RestController {  
  
 @Autowired  
 DataServices dataServices;  
  
 //static final Logger logger = Logger.getLogger(RestController.class);  
 //static final Logger logger = Logger. 
 
	/*
	 * This functions receives an XML via POST request, extracts the information
	 * about the similar applications returned by PERTOS, workload and
	 * performance characteristics given by the developer and returns the list
	 * of similar applications and their similarity values.
	 */

	@RequestMapping(value = "/application-discoverability", method = RequestMethod.POST)
	public @ResponseBody List<SimilarityResponse> discoverSimAps(@RequestBody String xml) {
	 try {
			// Workload Described by Developer and extracted from the XML
			Workload workloadQuery = ParserXML.getWorkloadFromXML(xml);
			// Performance Described by Developer
			ArrayList<PerformanceMetricDescriptor> lmetricsQuery = ParserXML.getPerformanceromXML(xml);
			// List of Apps with Similar Alpha extracted from the input XML and
			// returned by PERTOS
			ArrayList<Integer> lAlpha = ParserXML.getAlphaTFromXML(xml);
			// Final List of Similar apps and similarity value returned as
			// response
			List<SimilarityResponse> lSimResponse = new ArrayList<SimilarityResponse>();
			// Workload Similarity Calculations per each App with similar Alpha
			// topology, key represents the ID of the application
			TreeMap<Integer, Float> lWorkloadSim = dataServices.getWorkloadSim(lAlpha, workloadQuery);
			// Performance Similarity Calculations per each App with similar
			// Alpha topology
			TreeMap<Integer, Float> lPerformanceSim = dataServices.getSimilarTopologiesIDs(lAlpha, lmetricsQuery);
			// URLS and solutions of similar apps
			List<ViableTopologyDistribution> lViableTURL = dataServices.getViaTopologyURL(lAlpha);

			// Limit of similar applications,
			int knnLim = 10;
			int limFor = 0;
			if (lViableTURL.size() > knnLim)
				limFor = knnLim;
			else
				limFor = lViableTURL.size();

			int k = 0;
			for (int key : lWorkloadSim.keySet()) {

				if (k < limFor) {

					lSimResponse.add(new SimilarityResponse(key, lWorkloadSim.get(key), lPerformanceSim.get(key),
							GlobalSimilarity.computeSingleGlobalSimilarity(lWorkloadSim.get(key),
									lPerformanceSim.get(key)),
							lViableTURL.get(k).getUrl(), lViableTURL.get(k).getDist_url()));

					k++;
				}
			}
			// Sorting elements of the map according to their similarity value
			Collections.sort(lSimResponse);
			return lSimResponse;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	 
 }

	/*
	 * This function returns the workload and performance characteristics of the
	 * application with the ID given in the URL of the GET request
	 */
	@RequestMapping(value = "/application-knowledge/{id}", method = RequestMethod.GET)
	public @ResponseBody List<KnowledgeResponse> getKnowledge(@PathVariable("id") int id) {
		List<KnowledgeResponse> lknow = new ArrayList<KnowledgeResponse>();
		try {
			/*
			 * Invoking the function that returns the knowledge of the
			 * application
			 */
			lknow = dataServices.getAppKnowledge(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lknow;

	}

	/*
	 * This function invokes the functions that allow to store the workload,
	 * performance and solutions of a new application in the database (Persist
	 * knowledge)
	 */

	@RequestMapping(value = "/application-knowledge", method = RequestMethod.POST)
	public ResponseEntity<?> persistKnowledge(@RequestBody String xml, UriComponentsBuilder b) {


		try {
			// Extracting the ID returned by PERTOS to store the application
			// with the same ID in the Case Base
			int idAlpha = ParserXML.getAlphaID(xml);
			// Getting the workload characteristics
			 Workload wNew = ParserXML.getWorkloadFromXML(xml);
			 wNew.setTime_interval("monthly");
			// Getting the performance characteristics
			 ArrayList<PerformanceMetricDescriptor> lmetricsNew =
 ParserXML.getPerformanceromXML(xml);
			// Getting the URL of the viable topology stored in the Winery
			// repository
			ViableTopologyDistribution viaT = ParserXML.getSolutionFromXML(xml);
			// Invoking the function that persists the knowledge in the databas
			int appid = dataServices.persistKnowledge(idAlpha, wNew, lmetricsNew, viaT);
			 
			// Creating the HTTP response and setting the location of the
			// resource
			UriComponents uriComponents = b.path("/application-knowledge/{id}").buildAndExpand(appid);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriComponents.toUri());
			// Returning the HTTP CREATED message status
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/*
	 * This function invokes the methods to retrieve the list of similarity
	 * tables from the knowledge base
	 */
	@RequestMapping(value = "/similarity-tables", method = RequestMethod.GET)
	public @ResponseBody List<SimilarityTableResponse> retrieveSimilariyTables() {

		try {
			/* Invoking the service that retrieves the similarity tables */
			return dataServices.getSimilarityTables();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	/*
	 * This function invokes the methods to update a similarity table in the
	 * knowledge base
	 */

	@RequestMapping(value = "/similarity-table", method = RequestMethod.PUT)
	public ResponseEntity<?> updateSimilarityTable(@RequestBody String xml) {

		try {
			// Getting the similarity table entries
			ArrayList<TableEntry> lEntries = ParserXML.getSimTablesEntries(xml);
			int idSimTable = ParserXML.getSimTableId(xml);
			dataServices.updateSimilarityTables(idSimTable, lEntries);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * This function invokes the methods to update the knowledge of an
	 * application in the knowledge base
	 */

	@RequestMapping(value = "/application-knowledge", method = RequestMethod.PUT)
	public ResponseEntity<?> updateAppKnowledge(@RequestBody String xml) {

		try {
			// Getting the Application ID
			int idApp = ParserXML.getAlphaID(xml);
			// Getting the Metrics from the XML
			ArrayList<PerformanceMetricDescriptor> lPm = ParserXML.getPerformanceromXML(xml);
			// Getting the Workload
			Workload w = ParserXML.getWorkloadFromXML(xml);
			// Invoking the service
			dataServices.updateKnowledge(idApp, lPm, w);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/similarity-table", method = RequestMethod.POST)
	public ResponseEntity<?> createSimilarityTable(@RequestBody String xml) {

		try {
			ArrayList<TableEntry> lEntries = ParserXML.getSimTablesEntries(xml);
			String nameT = ParserXML.getSimTableName(xml);
			dataServices.createSimilarityTable(nameT, lEntries);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
 
}  
  
