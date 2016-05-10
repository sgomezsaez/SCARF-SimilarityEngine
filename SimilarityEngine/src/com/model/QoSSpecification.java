package com.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity  
@Table(name = "qos_specification")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 

public class QoSSpecification implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	
	@Id  
	@GeneratedValue  
    @Column(name = "id")
	public int id;
	
	@Column(name = "fk_workload_id")
	public int fk_workload_id;
	
	@Column(name = "fk_performance_id")
	public int fk_performance_id;
	
	
	public QoSSpecification() {
		super();
		this.id = 0;
		this.fk_workload_id = 0;
		this.fk_performance_id = 0;
	}
	
	
	public QoSSpecification(int id, int fk_workload_id, int fk_performance_id) {
		super();
		this.id = id;
		this.fk_workload_id = fk_workload_id;
		this.fk_performance_id = fk_performance_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFk_workload_id() {
		return fk_workload_id;
	}
	public void setFk_workload_id(int fk_workload_id) {
		this.fk_workload_id = fk_workload_id;
	}
	public int getFk_performance_id() {
		return fk_performance_id;
	}
	public void setFk_performance_id(int fk_performance_id) {
		this.fk_performance_id = fk_performance_id;
	}
	
	

}