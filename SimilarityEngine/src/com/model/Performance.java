package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "performance")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Performance implements Serializable {
	
	private static final long serialVersionUID = 1L;  
	@Id
	@GeneratedValue
	@Column(name = "id")
	public int id;

	public Performance() {
		super();
	}

	public Performance(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}