package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Entity  
@Table(name = "table_entry")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TableEntry implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	
	@Id  
	@GeneratedValue  
    @Column(name = "id")
	public int id;
	

    @Column(name = "column_name")
	public String column_name;
	
    @Column(name = "row_name")	
	public String row_name;
    
    @Column(name = "similarity_measure")	
	public float similarity_measure;
    
    @Column(name = "fk_table_id")	
	public int fk_table_id;
	
	
	public TableEntry(int id, String column_name, String row_name, float similarity_measure, int fk_table_id) {
		super();
		this.id = id;
		this.column_name = column_name;
		this.row_name = row_name;
		this.similarity_measure = similarity_measure;
		this.fk_table_id = fk_table_id;
	}
	
	public TableEntry() {
		
		this.id = -1;
		this.column_name = "";
		this.row_name = "";
		this.similarity_measure = 0;
		this.fk_table_id = 0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getRow_name() {
		return row_name;
	}
	public void setRow_name(String row_name) {
		this.row_name = row_name;
	}
	public float getSimilarity_measure() {
		return similarity_measure;
	}
	public void setSimilarity_measure(float similarity_measure) {
		this.similarity_measure = similarity_measure;
	}
	public int getFk_table_id() {
		return fk_table_id;
	}
	public void setFk_table_id(int fk_table_id) {
		this.fk_table_id = fk_table_id;
	}
	
	
}
