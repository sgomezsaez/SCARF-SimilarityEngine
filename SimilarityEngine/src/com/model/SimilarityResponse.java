package com.model;

public class SimilarityResponse implements Comparable {
	public int appId;
	public float workloadSimilarity;
	public float performanceSimilarity;
	public float globalSimilarity;
	public String url;
	public String dist_url;

	public SimilarityResponse() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public float getWorkloadSimilarity() {
		return workloadSimilarity;
	}

	public void setWorkloadSimilarity(float workloadSimilarity) {
		this.workloadSimilarity = workloadSimilarity;
	}

	public float getPerformanceSimilarity() {
		return performanceSimilarity;
	}

	public void setPerformanceSimilarity(float performanceSimilarity) {
		this.performanceSimilarity = performanceSimilarity;
	}

	public float getGlobalSimilarity() {
		return globalSimilarity;
	}


	public String getDist_url() {
		return dist_url;
	}

	public void setDist_url(String dist_url) {
		this.dist_url = dist_url;
	}

	public void setGlobalSimilarity(float globalSimilarity) {
		this.globalSimilarity = globalSimilarity;
	}

	public SimilarityResponse(int appId, float workloadSimilarity, float performanceSimilarity,
 float globalSimilarity,
			String url, String dist_url) {
		super();
		this.appId = appId;
		this.workloadSimilarity = workloadSimilarity;
		this.performanceSimilarity = performanceSimilarity;
		this.globalSimilarity = globalSimilarity;
		this.url = url;
		this.dist_url = dist_url;
	}

	@Override
	public int compareTo(Object o) {
		if (!(o instanceof SimilarityResponse))
			return 0;
		SimilarityResponse other = (SimilarityResponse) o;
		if (other.globalSimilarity < this.globalSimilarity)
			return -1;
		else if (other.getGlobalSimilarity() > this.globalSimilarity)
			return 1;
		else
			return 0;
	}
}
