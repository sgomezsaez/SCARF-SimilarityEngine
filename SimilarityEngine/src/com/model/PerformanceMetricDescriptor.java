package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity  
@Table(name = "performance_metric_descriptor")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class PerformanceMetricDescriptor implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	final static float UNDEFINED = -200;
	@Id  
	@GeneratedValue  
    @Column(name = "id")
	public int id;
	
	@Column(name = "min")
	public float min;
	
	@Column(name = "max")
	public float max;
	
	@Column(name = "mean")
	public float mean;
	
	@Column(name = "st_deviation")
	public float st_deviation;
	
	@Column(name = "fk_performance_id")
	public int fk_performance_id;
	
	@Column(name = "fk_metric_id")
	public int fk_metric_id;
	
	public PerformanceMetricDescriptor(float min, float max, float mean, float st_deviation,
			int fk_performance_id, int fk_metric_id) {
		super();
		
		this.min = min;
		this.max = max;
		this.mean = mean;
		this.st_deviation = st_deviation;
		this.fk_performance_id = fk_performance_id;
		this.fk_metric_id = fk_metric_id;
	}
	
	public PerformanceMetricDescriptor() {
		super();
		this.id = -1;
		this.min = UNDEFINED;
		this.max = UNDEFINED;
		this.mean = UNDEFINED;
		this.st_deviation = UNDEFINED;
		this.fk_performance_id = -1;
		this.fk_metric_id = -1;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public float getMean() {
		return mean;
	}
	public void setMean(float mean) {
		this.mean = mean;
	}
	public float getSt_deviation() {
		return st_deviation;
	}
	public void setSt_deviation(float st_deviation) {
		this.st_deviation = st_deviation;
	}
	public int getFk_performance_id() {
		return fk_performance_id;
	}
	public void setFk_performance_id(int fk_performance_id) {
		this.fk_performance_id = fk_performance_id;
	}
	public int getFk_metric_id() {
		return fk_metric_id;
	}
	public void setFk_metric_id(int fk_metric_id) {
		this.fk_metric_id = fk_metric_id;
	}
	
	
	
}