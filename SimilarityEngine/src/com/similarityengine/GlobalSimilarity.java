package com.similarityengine;


import java.util.List;

import com.utils.ParserXML;

/*
 * Class: GlobalSimilarity.java
 * 
 * This class implements the methods that compute the global similarity measures
 * 
 * */
public class GlobalSimilarity {
	

	/*
	 * @localSimList: List of local similarity values
	 * 
	 * This function follows te global similarity measure defined in chapter of
	 * concept and design of the thesis
	 */
	public static float computeSingleGlobalSimilarity(List<Float> localSimList) {
		float acum = 0;
		int cont = 0;
		for (int i = 0; i < localSimList.size(); i++) {
			if (localSimList.get(i) != -1)
				cont++;
		}
		// We count the elements of the list that are valid (!=-1) to obtain
		// the value for the weights
		if (cont != 0) {
			// We want to obtain an equal weight for all attributes,
			float weight = 1.0f / cont;

			// Applying the Minkownski aggregation
			for (int j = 0; j < localSimList.size(); j++) {
				if (localSimList.get(j) != -1) {
					cont++;
					acum = acum + (weight * localSimList.get(j));
				}
			}

			// Rounding result to two decimals
			return ParserXML.round(acum, 2);
		}
		return -1;
	}
	

	/*
	 * @param s1: A real value
	 * 
	 * @param s2: A real value
	 * 
	 * This function is used to compute the global similarity between the
	 * performance and the workload Currently is calculated as an average
	 */
	public static float computeSingleGlobalSimilarity(float s1, float s2) {
		float acum = 0;
		int cont = 2;

		if (s1 != -1 && s2 != -1) {
			acum = s1 + s2;
			return ParserXML.round((acum / cont), 2);
		} else if (s1 == -1 && s2 == -1) {
			return -1;
		}

		else if (s1 == -1 && s2 != -1) {
			return ParserXML.round((s2 / cont), 2);
		} else if (s2 == -1 && s1 != -1) {
			return ParserXML.round((s1 / cont), 2);
		}

		return -1;

	}

}
