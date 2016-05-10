package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity  
@Table(name = "performance_metric")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class PerformanceMetric implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	
	@Id  
	@GeneratedValue  
    @Column(name = "id")
	
	public int id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "fk_caegory_id")
	public int fk_category_id;
	
	public PerformanceMetric(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFk_category_id() {
		return fk_category_id;
	}

	public void setFk_category_id(int fk_category_id) {
		this.fk_category_id = fk_category_id;
	}
	
	
	
	
}