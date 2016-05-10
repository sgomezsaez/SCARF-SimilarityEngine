package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.model.AppDescription;
import com.model.KnowledgeResponse;
import com.model.Performance;
import com.model.PerformanceMetricDescriptor;
import com.model.QoSSpecification;
import com.model.SimilarityTable;
import com.model.SimilarityTableResponse;
import com.model.TableEntry;
import com.model.ViableTopologyDistribution;
import com.model.Workload;
import com.similarityengine.SimilarityLogic;;

/*
 * File: DataDaoImpl.java
 * Implementation of the functions invoked by the  Data Services layer and that interact with the database
 * */

public class DataDaoImpl implements DataDao {  
  
	 //SessionFactory  object to open communication with Database
	 @Autowired  
	 SessionFactory sessionFactory;  
	 
	 //Session object to open communication with Database
	 Session session = null;  
	//Object to process transactions
	 Transaction tx = null;  
	 JdbcTemplate jdbcTemplateObject;
	 

	/*
	 * @param listID: List of IDs of functionally similar applications (returned
	 * by PERTOS)
	 * 
	 * @param queryDescriptors: List of performance metrics of the case being
	 * queried.
	 *
	 * This function queries from the Knowledge Base the performance
	 * characteristics of the applications specified under the IDs stored in the
	 * list listID and returns a Map of the similarity values between each
	 * application and the queried performance.The key of each entry of the map
	 * corresponds to the ID of the application in the database (table
	 * app_description)
	 * 
	 */
	@Override
	public TreeMap<Integer, Float> getSimilarTopologiesIDs(ArrayList<Integer> listID,
			ArrayList<PerformanceMetricDescriptor> queryDescriptors) throws Exception {
		// Opening session object
		session = sessionFactory.openSession();
		// Declaring map that will contain the returned answer
		TreeMap<Integer, Float> resultSet = new TreeMap<Integer, Float>();
		// Iterating the list of functional similar application IDs
		for (int i = 0; i < listID.size(); i++) {
			int id = listID.get(i);
			// Query to get the list of metrics descriptors from the Database
			String hql = "FROM PerformanceMetricDescriptor " + "WHERE fk_performance_id IN ("
					+ "SELECT fk_performance_id FROM QoSSpecification " + "WHERE fk_performance_id IN ( "
					+ "SELECT fk_qosspec_id from AppDescription " + "WHERE id = :id)) ORDER BY fk_metric_id ASC";
			/// Creating the query
			Query query = session.createQuery(hql);
			// Specifying the id parameter (obtained from the list)
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			// Executing the query
			List<PerformanceMetricDescriptor> listMetricD = query.list();
			// If metrics are received from the knowledge base, then compute
			// similarity of performance
			if (listMetricD.size() > 0) {
				// Invoking the function that computes the similarity of the
				// performance characteristics of
				// the query and the current case
				float similarity = SimilarityLogic.computeSimilarityPerformance(listMetricD, queryDescriptors);
				// Adding the result to the map, the key corresponds to the ID
				// of the similar application
				// and the similarity the previously computed value
				resultSet.put(id, similarity);
			}
		}

		session.close();
		return resultSet;
		 }

	/*
	 * @param listID: List of IDs of functionally similar applications (returned
	 * by PERTOS)
	 * 
	 * @param workloadCase: List of workload characteristics of the case being
	 * queried.
	 * 
	 * This function queries from the Knowledge Base the workload
	 * characteristics of the applications specified under the IDs stored in the
	 * list listID and returns a Map of the similarity values between each
	 * application and the queried performance.The key of each entry of the map
	 * corresponds to the ID of the application in the database (table
	 * app_description)
	 */
	 
	 @Override
	 public TreeMap<Integer,Float> getWorkloadSim(ArrayList<Integer> listID, 
			 Workload workloadCase) throws Exception{
		 session = sessionFactory.openSession();
		 TreeMap<Integer,Float> resultSet = new TreeMap<Integer,Float>();
		// Obtaining similarity tables
		 TreeMap<Integer,List<TableEntry>> lTables = this.getSimilarityTableWorkload();		 
		 
		// For each entry of the received list, the workload table is queried
		// and the
		// workload similarity is computed
		 for(int i=0; i<listID.size(); i++){
			 int id=listID.get(i);		 
		     String hql = "FROM Workload "
				 		+ "WHERE id IN ("
				 		+ "SELECT fk_workload_id FROM QoSSpecification "
				 		+ "WHERE id IN ( "
				 		+ "SELECT fk_qosspec_id from AppDescription "
				 		+ "WHERE id = :id))";
			 //Query query = sessionFactory.getCurrentSession().createQuery(hql);
		     Query query = session.createQuery(hql);
			// Specifying the id parameter (obtained from the list)
		     query.setParameter("id",id);
		     @SuppressWarnings("unchecked")
		     List<Workload> listWorkloadCB = query.list();
			// If workload characteristics are returned, then compute similarity
			if (listWorkloadCB.size() > 0) {
				// Invoking the function that computes the similarity of the
				// workload characteristics of
				// the query and the current case, including the list of
				// similarity tables
				float similarity = SimilarityLogic.computeSimilarityWorkload(listWorkloadCB, workloadCase, lTables);
				// Adding the result to the map, the key corresponds to the ID
				// of the similar application
				// and the similarity the previously computed value
				resultSet.put(id, similarity);
			}
		 }
		 
	     session.close(); 		 
		 return resultSet;
		 
	 }
	 
	/*
	 * @param row: Name of the similarity table row (e.g. gamma)
	 * 
	 * @param column: Name of the similarity able column (e.g. normal).
	 * 
	 * This function queries the database for the similarity table of the given
	 * column and row
	 */

	@Override
	public float getEntrySimValue(String column, String row) throws Exception {
		session = sessionFactory.openSession();
		// Querying the table_entry table of the knowledge base
		String hql = "FROM TableEntry " + "WHERE column_name = '" + column + "' AND row_name = '" + row + "'";

		Query query = session.createQuery(hql);

		System.out.println(query.toString());

		@SuppressWarnings("unchecked")
		List<TableEntry> lentry = query.list();
		session.close();
		// If the answer contains at least one element, then the first one is
		// returned as answer

		if (lentry.size() > 0)
			return lentry.get(0).getSimilarity_measure();

		// If the query doesn't return any answers, then -1 is returned (-1 is
		// dissimilar)
		return -1;

	}

	/*
	 * This functions queries the existent and currently used similarity tables
	 * and returns them in a map where the key is the ID that identifies them ID
	 * 1: Similarity Table for Pattern ID 2: Similarity Table for Arrival Rate
	 * Distribution ID 3: Similarity Table for Behavioral Model Modify table
	 * 'similarity_table' if you want to change this, for now these are the IDs
	 * used when computing similarity of the correspondant characteristics.
	 */

	 @Override
	 public TreeMap<Integer,List<TableEntry>> getSimilarityTableWorkload()
			 throws Exception{
		      session = sessionFactory.openSession();
		      TreeMap<Integer,List<TableEntry>> resultSet = new TreeMap<Integer,List<TableEntry>>();
		      
		      String hql1 = "FROM TableEntry "
				 		+ "WHERE fk_table_id =1";
		      Query query = session.createQuery(hql1);
		      @SuppressWarnings("unchecked")
		      List<TableEntry> lTable1 = query.list(); 
			  
		      String hql2 = "FROM TableEntry "
				 		+ "WHERE fk_table_id =2";
		      Query query2 = session.createQuery(hql2);
		      @SuppressWarnings("unchecked")
		      List<TableEntry> lTable2 = query2.list(); 
		      
		      String hql3 = "FROM TableEntry "
				 		+ "WHERE fk_table_id =3";
		      Query query3 = session.createQuery(hql3);
		      @SuppressWarnings("unchecked")
		      List<TableEntry> lTable3 = query3.list(); 
		      
		      resultSet.put(1,lTable1);
		      resultSet.put(2,lTable2);
		      resultSet.put(3,lTable3);
		// session.close();
		      return resultSet;
				
			 }

	/*
	 * @param listID: List of applications
	 * 
	 * This function queries the database for the Winery Repository URLs of the
	 * viable topologies per each of the received apps. This URLs are used when
	 * returning the list of similar applications in order to render them in
	 * Winery
	 */

	@Override
	public List<ViableTopologyDistribution> getViaTopologyURL(ArrayList<Integer> listID) throws Exception {
		List<ViableTopologyDistribution> lurl = new ArrayList<ViableTopologyDistribution>();
		session = sessionFactory.openSession();
		for (int i = 0; i < listID.size(); i++) {
			int id = listID.get(i);
			String hql1 = "FROM ViableTopologyDistribution " 
 + "WHERE id IN (SELECT fk_solution_id  FROM "
					+ "AppDescription WHERE id=:id)";
			Query query = session.createQuery(hql1);
			query.setParameter("id",id);
			@SuppressWarnings("unchecked")
			List<ViableTopologyDistribution> lTable1 = query.list();
			if (lTable1.size() > 0) {
			lurl.add(lTable1.get(0));
			//lurl.add(lTable1.get(0).getUrl());
			}
		}
		session.close();
		return lurl;
	}

	/*
	 * @param listID: List of applications
	 * 
	 * This function queries the knowledge base and retrieves the workload
	 * characteristics and performance metrics descriptors of the given
	 * application ID. The answer is wrapped in a KnowledgeResponse Object which
	 * is defined under the package com.model and is composed of an ID (app ID),
	 * Workload Object and a list of the metric descriptors
	 */
	@Override
	public List<KnowledgeResponse> getAppKnowledge(int id) throws Exception {
		List<KnowledgeResponse> lknow = new ArrayList<KnowledgeResponse>();
		session = sessionFactory.openSession();
		//for (int i = 0; i < listID.size(); i++) {
			//int id=listID.get(i);
		// Querying the workload characteristics
			String hql = "FROM Workload "
			 		+ "WHERE id IN ("
			 		+ "SELECT fk_workload_id FROM QoSSpecification "
			 		+ "WHERE id IN ( "
			 		+ "SELECT fk_qosspec_id from AppDescription "
			 		+ "WHERE id = :id))";
			  Query query = session.createQuery(hql);
			  query.setParameter("id",id);
			  @SuppressWarnings("unchecked")
			  List<Workload> listWorkloadCB = query.list();
		// Querying the performance metric descriptors
			  String hql2 = "FROM PerformanceMetricDescriptor "
				 		+ "WHERE fk_performance_id IN ("
				 		+ "SELECT fk_performance_id FROM QoSSpecification "
				 		+ "WHERE fk_performance_id IN ( "
				 		+ "SELECT fk_qosspec_id from AppDescription "
+ "WHERE id = :id)) ORDER BY fk_metric_id ASC";
			 //Query query = sessionFactory.getCurrentSession().createQuery(hql);
		     Query query2 = session.createQuery(hql2);
		query2.setParameter("id", id);
		     @SuppressWarnings("unchecked")
		List<PerformanceMetricDescriptor> listMetricD = query2.list();
		// Setting the queried objects to the response object
		lknow.add(new KnowledgeResponse(id, listWorkloadCB.get(0), listMetricD));
		
		//}
		
		session.close();
		return lknow;

	}

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
	 * This function executes the necessary SQL statements to create a new
	 * application and its workload, metrics and viable distribution entries
	 */

	@Override
	public int persistKnowledge(int id, Workload w, List<PerformanceMetricDescriptor> lp,
			ViableTopologyDistribution viaT)
			throws Exception {
		
		session = sessionFactory.openSession();
		// We need to start a transaction to keep the integrity of the data
		tx = session.beginTransaction();
		// Storing the Workload in the database
		session.save(w);
		// Storing a new Performance in the database
		session.save(new Performance());
		String hqlPerf = "SELECT id FROM Performance ORDER BY id DESC";
		Query queryPerf = session.createQuery(hqlPerf);
		queryPerf.setMaxResults(1);
		// Obtaining the last created Performance ID
		int idPerf = (Integer) queryPerf.list().get(0);
		// iterating over the list of metrics descriptors- -200 means that the
		// value was not set
		// and if any of them is different to that value then save it
		for (int i = 0; i < lp.size(); i++) {
			if (lp.get(i).getMax() != -200 || lp.get(i).getMin() != -200 || lp.get(i).getMean() != -200
					|| lp.get(i).getSt_deviation() != -200) {
				// Setting the ID of the Performance which is the latest created
				lp.get(i).setFk_performance_id(idPerf);
				session.save(lp.get(i));
			}
		}

		// Getting the ID of the last created Workload
		String hql = "SELECT id FROM Workload ORDER BY id DESC";
		Query query = session.createQuery(hql);
		query.setMaxResults(1);
		int idWork = (Integer) query.list().get(0);

		// Creating a new QoS entry in the database and setting the IDs of the
		// newly created
		// Workload and Performance
		QoSSpecification qos = new QoSSpecification();
		qos.setFk_workload_id(idWork);
		qos.setFk_performance_id(idPerf);
		session.save(qos);

		// Getting the ID of the last created QoSSpecification Object
		String hqlQoS = "SELECT id FROM QoSSpecification ORDER BY id DESC";
		Query queryQoS = session.createQuery(hqlQoS);
		queryQoS.setMaxResults(1);
		int idQoS = (Integer) queryQoS.list().get(0);

		// Storing the data of the viable distribution
		session.save(viaT);
		String hqlVia = "SELECT id FROM ViableTopologyDistribution ORDER BY id DESC";
		// getting the ID of the latest created entry
		Query queryVia = session.createQuery(hqlVia);
		queryVia.setMaxResults(1);
		int idVia = (Integer) queryVia.list().get(0);

		// Setting the attributes of the AppDescription and saving them into the
		// DB
		AppDescription app = new AppDescription();
		app.setId(id);
		app.setFk_qosspec_id(idQoS);
		app.setFk_solution_id(idVia);
		session.save(app);

		tx.commit();
		session.close();

		return 0;
	}

	/*
	 * This function queries the database the list of existing similarity
	 * tables.
	 */

	@Override
	public List<SimilarityTableResponse> getSimilarityTables() throws Exception {
		List<SimilarityTableResponse> lSimTablesResponse = new ArrayList<SimilarityTableResponse>();
		session = sessionFactory.openSession();

		// Obtaining the list of all existing similarity tables
		String hql = "FROM SimilarityTable";
		Query query = session.createQuery(hql);

		@SuppressWarnings("unchecked")

		List<SimilarityTable> listTables = query.list();
		// Obtaining the entries of each similarity table
		for (int i = 0; i < listTables.size(); i++) {
			String hql2 = "FROM TableEntry WHERE fk_table_id = :id";
			// Query query =
			// sessionFactory.getCurrentSession().createQuery(hql);
			Query query2 = session.createQuery(hql2);
			query2.setParameter("id", listTables.get(i).id);

			SimilarityTableResponse tr = new SimilarityTableResponse();
			tr.setTableName(listTables.get(i).getName());
			@SuppressWarnings("unchecked")
			List<TableEntry> te = query2.list();
			tr.setEntries(te);
			lSimTablesResponse.add(tr);
		}
		session.close();
		return lSimTablesResponse;

	}

	/*
	 * @param nameT: Name of the table
	 * 
	 * @param lEntries: List of entry elements
	 * 
	 * This function creates a new similarity table
	 */
	@Override
	public int createSimilarityTable(String nameT, ArrayList<TableEntry> lEntries) throws Exception {
		session = sessionFactory.openSession();
		// We need to start a transaction to keep the integrity of the data
		tx = session.beginTransaction();

		// Storing a new Performance in the database
		SimilarityTable st = new SimilarityTable();
		st.setName(nameT);
		session.save(st);
		String hqlPerf = "SELECT id FROM SimilarityTable ORDER BY id DESC";
		Query queryPerf = session.createQuery(hqlPerf);
		queryPerf.setMaxResults(1);
		// Obtaining the last created Performance ID
		int idST = (Integer) queryPerf.list().get(0);

		for (int i = 0; i < lEntries.size(); i++) {
			TableEntry te = lEntries.get(i);
			te.setFk_table_id(idST);
			session.save(te);
		}
		tx.commit();
		session.close();

		return 0;

	}

	/*
	 * @param lEntries: List of similarity table entries
	 * 
	 * This function updates the similarity value of an existing table entry
	 */
	@Override
	public int updateSimilarityTables(int idSimTable, ArrayList<TableEntry> lEntries) throws Exception {
		session = sessionFactory.openSession();
		int result = 0;
		// We need to start a transaction to keep the integrity of the data
		tx = session.beginTransaction();
		for (int i = 0; i < lEntries.size(); i++) {
			String hql = "UPDATE TableEntry SET similarity_measure = :sm " + "WHERE column_name = '"
					+ lEntries.get(i).getColumn_name() + "' AND " + "row_name = '" + lEntries.get(i).getRow_name()
					+ "' AND fk_table_id = :idTable";

			Query query = session.createQuery(hql);
			query.setParameter("sm", lEntries.get(i).getSimilarity_measure());
			query.setParameter("idTable", idSimTable);
			// number of rows affected
			result = query.executeUpdate();

		}

		tx.commit();
		session.close();
		return result;
	}

	/*
	 * @param idApp: ID of the App being updated
	 * 
	 * @param lPm: List of metric descriptors to be updated
	 * 
	 * This function updates the performance metrics of an application
	 */
	@Override
	public int updateKnowledge(int idApp, ArrayList<PerformanceMetricDescriptor> lPm, Workload w) throws Exception {
		session = sessionFactory.openSession();
		int result = 0;
		// We need to start a transaction to keep the integrity of the data
		tx = session.beginTransaction();
		// Getting the performance ID
		String hql = "FROM QoSSpecification WHERE fk_performance_id IN (SELECT fk_qosspec_id FROM "
				+ "AppDescription WHERE id = :id)";
		Query query = session.createQuery(hql);
		query.setParameter("id", idApp);
		@SuppressWarnings("unchecked")
		// Executing the query
		List<QoSSpecification> lQoS = query.list();
		if (lQoS.size() > 0) {
			// Getting the Performance ID
			int performanceID = lQoS.get(0).fk_performance_id;

			for (int i = 0; i < lPm.size(); i++) {
				String hql2 = "FROM PerformanceMetricDescriptor "
						+ "WHERE fk_performance_id= :perID AND fk_metric_id = :mID";
				Query query2 = session.createQuery(hql2);
				query2.setParameter("perID", performanceID);
				query2.setParameter("mID", lPm.get(i).getFk_metric_id());
				@SuppressWarnings("unchecked")
				List<PerformanceMetricDescriptor> lpmd = query2.list();
				if (lpmd.size() > 0) {
					PerformanceMetricDescriptor pmd = lpmd.get(0);
					pmd.setMin(lPm.get(i).getMin());
					pmd.setMax(lPm.get(i).getMax());
					pmd.setMean(lPm.get(i).getMean());
					pmd.setSt_deviation(lPm.get(i).getSt_deviation());
					session.update(pmd);
				} else {
					PerformanceMetricDescriptor pmd = new PerformanceMetricDescriptor();
					if (lPm.get(i).getMax() != -200 || lPm.get(i).getMin() != -200 || lPm.get(i).getMean() != -200
							|| lPm.get(i).getSt_deviation() != -200) {
						pmd.setFk_performance_id(performanceID);
						pmd.setFk_metric_id(lPm.get(i).getFk_metric_id());
						pmd.setMin(lPm.get(i).getMin());
						pmd.setMax(lPm.get(i).getMax());
						pmd.setMean(lPm.get(i).getMean());
						pmd.setSt_deviation(lPm.get(i).getSt_deviation());
						session.save(pmd);
					}
				}

			}

		}
		// If there is info in the workload
		if (!w.empty()) {
			// Getting the Workload ID
			int workloadID = lQoS.get(0).fk_workload_id;
			String hql3 = "UPDATE Workload SET pattern= :npattern, "
					+ "arrival_rate= :nar, behavioral_model= :nbm, avg_usr_number= :naun, "
					+ "avg_transactions_second= :nats WHERE id= :wID";

			Query query3 = session.createQuery(hql3);
			query3.setParameter("npattern", w.getPattern());
			query3.setParameter("nar", w.getArrival_rate());
			query3.setParameter("nbm", w.getBehavioral_model());
			query3.setParameter("naun", w.getAvg_usr_number());
			query3.setParameter("nats", w.getAvg_transactions_second());
			query3.setParameter("wID", workloadID);
			// number of rows affected
			result = query3.executeUpdate();
		}

		tx.commit();
		session.close();
		return result;
	}
  
}  