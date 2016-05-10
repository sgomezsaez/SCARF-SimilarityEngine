package com.utils;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.model.PerformanceMetricDescriptor;
import com.model.TableEntry;
import com.model.ViableTopologyDistribution;
import com.model.Workload;

/*
 * Class: ParserXML.java
 * This class implements methods that help to parse the received XML data
 * and return the respective objects
 * */
public class ParserXML {
	final static float UNDEFINED = -200;

	/*
	 * This function parses the received XML and returns a list of topologies
	 * that are functionally similar (returned by PERTOS)
	 */
	public static ArrayList<Integer> getAlphaTFromXML(String xml) throws Exception {
		ArrayList<Integer> lAlpha = new ArrayList<Integer>();
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		NodeList li = doc.getElementsByTagName("app_id");
		for (int i = 0; i < li.getLength(); i++) {
			lAlpha.add(Integer.parseInt(getCharacterDataFromElement((Element) li.item(i))));
		}

		return lAlpha;

	}

	/*
	 * This function parses the received XML the text node specified under the
	 * tag appAlphaID This ID comes from PERTOS and we need it to store the QoS
	 * info under the same ID
	 */

	public static int getAlphaID(String xml) throws Exception {
		int idAlpha = -1;
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		NodeList li = doc.getElementsByTagName("appAlphaID");
		if (getCharacterDataFromElement((Element) li.item(0)) != "")
			idAlpha = (Integer.parseInt(getCharacterDataFromElement((Element) li.item(0))));
		System.out.println("****ID APP" + idAlpha + "\n");

		return idAlpha;

	}

	/*
	 * This function parses the received XML and returns a ViablDistribution
	 * object
	 */

	public static ViableTopologyDistribution getSolutionFromXML(String xml) throws Exception {
		ViableTopologyDistribution viaT = new ViableTopologyDistribution();
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();

		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		NodeList li = doc.getElementsByTagName("name");
		// Element element = (Element) li.item(0);
		/// System.out.println("Name: " + getCharacterDataFromElement(element));
		viaT.setDescription(getCharacterDataFromElement((Element) doc.getElementsByTagName("name").item(0)));
		viaT.setUrl(getCharacterDataFromElement((Element) doc.getElementsByTagName("view_url").item(0)));
		String distURL = getCharacterDataFromElement((Element) doc.getElementsByTagName("dist_url_host").item(0));
		distURL = distURL.concat("&nsmu=");
		distURL = distURL
				.concat(getCharacterDataFromElement((Element) doc.getElementsByTagName("dist_url_nsmu").item(0)));
		distURL = distURL.concat("&muid=");
		distURL = distURL
				.concat(getCharacterDataFromElement((Element) doc.getElementsByTagName("dist_url_muid").item(0)));
		viaT.setDist_url(distURL);

		return viaT;

	}

	/*
	 * This function parses the received XML and returns a workload object
	 */

	public static Workload getWorkloadFromXML(String xml) throws Exception {
		Workload w = new Workload();
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();

		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);

		NodeList li = doc.getElementsByTagName("pattern");

		// Setting th workload characteristics from the text nodes
		w.setPattern(getCharacterDataFromElement((Element) doc.getElementsByTagName("pattern").item(0)));
		w.setArrival_rate(getCharacterDataFromElement((Element) doc.getElementsByTagName("arrival").item(0)));
		w.setBehavioral_model(getCharacterDataFromElement((Element) doc.getElementsByTagName("behavioral").item(0)));
		if (getCharacterDataFromElement((Element) doc.getElementsByTagName("avg_transactions").item(0)).length() > 0) {
			w.setAvg_transactions_second(Float.parseFloat(
					getCharacterDataFromElement((Element) doc.getElementsByTagName("avg_transactions").item(0))));
		} else {
			w.setAvg_transactions_second(UNDEFINED);

		}

		if (getCharacterDataFromElement((Element) doc.getElementsByTagName("avg_users").item(0)).length() > 0) {
		w.setAvg_usr_number(
				Float.parseFloat(getCharacterDataFromElement((Element) doc.getElementsByTagName("avg_users").item(0))));
		} else {
			w.setAvg_usr_number(UNDEFINED);
		}

		return w;

	}

	/*
	 * This function parses the received XML and returns a list of performance
	 * metric descriptors
	 */
	public static ArrayList<PerformanceMetricDescriptor> getPerformanceromXML(String xml) throws Exception {
		ArrayList<PerformanceMetricDescriptor> lm = new ArrayList<PerformanceMetricDescriptor>();
		// The metrics that we are using and how they are specified in the XML
		String[] metric_ids = { "response_time", "throughput", "processing_time", "avg_read_speed", "avg_write_speed",
				"avg_migration_time", "latency", "backup_time", "bandwith", "processor_speed", "storage_size",
				"memory_allocation_vm", "number_vm", "number_processors", "io_operations", "network_utilization",
				"memory_utilization", "disk_utilization", "cpu_utilization", "vm_utilization", "number_vm_perserver",
				"resource_acquisition_time", "resource_provisioning_time", "deployment_time", "resource_release_time",
				"vm_startup_time", "cloud_service_uptime", "cloud_resource_uptime", "meantime_between_failures",
				"meantime_repair" };

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();

		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);

		for (int i = 0; i < metric_ids.length; i++) {
			// Using the elements of the defined list to avoid writing element
			// by element
			NodeList li = doc.getElementsByTagName(metric_ids[i]);
			NodeList li2 = ((Element) li.item(0)).getChildNodes();
			Node current = null;
			PerformanceMetricDescriptor pm = new PerformanceMetricDescriptor();
			pm.setFk_metric_id(i + 1);
			for (int j = 0; j < li2.getLength(); j++) {
				current = li2.item(j);
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) current;
					if (element.getTagName().equals("min") && getCharacterDataFromElement(element).length() > 0) {
						pm.setMin(Float.parseFloat(getCharacterDataFromElement(element)));

					} else if (element.getTagName().equals("max")
							&& getCharacterDataFromElement(element).length() > 0) {
						pm.setMax(Float.parseFloat(getCharacterDataFromElement(element)));

					} else if (element.getTagName().equals("avg")
							&& getCharacterDataFromElement(element).length() > 0) {
						pm.setMean(Float.parseFloat(getCharacterDataFromElement(element)));
					} else if (element.getTagName().equals("st") && getCharacterDataFromElement(element).length() > 0) {
						pm.setSt_deviation(Float.parseFloat(getCharacterDataFromElement(element)));
					}

				}

			}
			// System.out.println(pm.fk_metric_id + " " + pm.getMax() + "\n");
			lm.add(pm);

		}

		return lm;

	}

	// This function allows to obtain the text between two tags
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	// This function is used just to round a floating value
	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public static ArrayList<TableEntry> getSimTablesEntries(String xml) throws Exception {
		ArrayList<TableEntry> lEntries = new ArrayList<TableEntry>();
		int idTable = -1;
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		Node current = null;
		// Obtaining similarity table ID
		NodeList li = doc.getElementsByTagName("simTableID");
		if (getCharacterDataFromElement((Element) li.item(0)) != "")
			idTable = (Integer.parseInt(getCharacterDataFromElement((Element) li.item(0))));

		// Obtaining value of the entries and creating objects
		NodeList li2 = doc.getElementsByTagName("simTableEntry");

		for (int i = 0; i < li2.getLength(); i++) {
			NodeList li3 = ((Element) li2.item(i)).getChildNodes();
			TableEntry te = new TableEntry();
			te.setFk_table_id(idTable);
			for (int j = 0; j < li3.getLength(); j++) {
				current = li3.item(j);
				if (current.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) current;
					if (element.getTagName().equals("column") && getCharacterDataFromElement(element).length() > 0) {
						te.setColumn_name(getCharacterDataFromElement(element));

					} else if (element.getTagName().equals("row")
							&& getCharacterDataFromElement(element).length() > 0) {
						te.setRow_name(getCharacterDataFromElement(element));

					} else if (element.getTagName().equals("simValue")
							&& getCharacterDataFromElement(element).length() > 0) {
						te.setSimilarity_measure(Float.parseFloat(getCharacterDataFromElement(element)));
					}

				}

			}
			lEntries.add(te);
		}
		return lEntries;
	}

	/* Returns the ID of the Similarity Table especified in the XML */
	public static int getSimTableId(String xml) throws Exception {

		int idTable = -1;
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		// Obtaining similarity table ID
		NodeList li = doc.getElementsByTagName("simTableID");
		if (getCharacterDataFromElement((Element) li.item(0)) != "")
			idTable = (Integer.parseInt(getCharacterDataFromElement((Element) li.item(0))));
		return idTable;
	}

	/* Returns the name of the similarity table depicted in the xml */
	public static String getSimTableName(String xml) throws Exception {

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		Document doc = db.parse(is);
		// Obtaining similarity table ID
		NodeList li = doc.getElementsByTagName("simTableName");
		if (getCharacterDataFromElement((Element) li.item(0)) != "")
			return getCharacterDataFromElement((Element) li.item(0));
		return "";
	}
}
