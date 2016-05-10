package com.model;

import java.util.List;

public class KnowledgeResponse {
	public int appId;
	public Workload workload;
	public List<PerformanceMetricDescriptor> lmetricd;

	public KnowledgeResponse() {
		super();
	}

	public KnowledgeResponse(int appId, Workload workload, List<PerformanceMetricDescriptor> lmetricd) {
		super();
		this.appId = appId;
		this.workload = workload;
		this.lmetricd = lmetricd;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public Workload getWorkload() {
		return workload;
	}

	public void setWorkload(Workload workload) {
		this.workload = workload;
	}

	public List<PerformanceMetricDescriptor> getLmetricd() {
		return lmetricd;
	}

	public void setLmetricd(List<PerformanceMetricDescriptor> lmetricd) {
		this.lmetricd = lmetricd;
	}

}
