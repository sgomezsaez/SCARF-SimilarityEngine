package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;  

@Entity  
@Table(name = "app_description")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  

public class AppDescription implements Serializable{
	 
	private static final long serialVersionUID = 1L;  
	
	@Id  

    @Column(name = "id")  
	public int id;
	
	@Column(name = "fk_solution_id")
	public int fk_solution_id;
	
	@Column(name = "fk_qosspec_id")
	public int fk_qosspec_id;
	
	public AppDescription() {
		super();
		this.id = 0;
		this.fk_solution_id = 0;
		this.fk_qosspec_id = 0;
	}	
	
	public AppDescription(int id, int fk_solution_id, int fk_qosspec_id) {
		super();
		this.id = id;
		this.fk_solution_id = fk_solution_id;
		this.fk_qosspec_id = fk_qosspec_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFk_solution_id() {
		return fk_solution_id;
	}
	public void setFk_solution_id(int fk_solution_id) {
		this.fk_solution_id = fk_solution_id;
	}
	public int getFk_qosspec_id() {
		return fk_qosspec_id;
	}
	public void setFk_qosspec_id(int fk_qosspec_id) {
		this.fk_qosspec_id = fk_qosspec_id;
	}
	
	
}