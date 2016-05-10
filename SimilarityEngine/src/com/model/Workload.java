package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity  
@Table(name = "workload")  
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  
public class Workload implements Serializable{
	
	private static final long serialVersionUID = 1L;  
	
	@Id  
	@GeneratedValue  
    @Column(name = "id")
	public  int id;
	
	@Column(name = "pattern")
	public  String pattern;
	
	@Column(name = "arrival_rate")
	public String arrival_rate;
	
	@Column(name = "behavioral_model")
	public String behavioral_model;
	
	@Column(name = "avg_usr_number")
	public float avg_usr_number;
	
	@Column(name = "avg_transactions_second")
	public float avg_transactions_second;
	
	@Column(name = "time_interval")
	public String time_interval;
	
	public Workload(String pattern, String arrival_rate, String behavioral_model, float avg_usr_number,
			float avg_transactions_second) {
		super();
		
		this.pattern = pattern;
		this.arrival_rate = arrival_rate;
		this.behavioral_model = behavioral_model;
		this.avg_usr_number = avg_usr_number;
		this.avg_transactions_second = avg_transactions_second;
		//this.time_interval = time_interval;
	}
	
	
	public Workload() {
		super();
		this.id = 0;
		this.pattern = "";
		this.arrival_rate = "";
		this.behavioral_model = "";
		this.avg_usr_number = 0;
		this.avg_transactions_second = 0;
		this.time_interval = "";
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public String getArrival_rate() {
		return arrival_rate;
	}


	public void setArrival_rate(String arrival_rate) {
		this.arrival_rate = arrival_rate;
	}


	public String getBehavioral_model() {
		return behavioral_model;
	}


	public void setBehavioral_model(String behavioral_model) {
		this.behavioral_model = behavioral_model;
	}


	public float getAvg_usr_number() {
		return avg_usr_number;
	}


	public void setAvg_usr_number(float avg_usr_number) {
		this.avg_usr_number = avg_usr_number;
	}


	public float getAvg_transactions_second() {
		return avg_transactions_second;
	}


	public void setAvg_transactions_second(float avg_transactions_second) {
		this.avg_transactions_second = avg_transactions_second;
	}


	public String getTime_interval() {
		return time_interval;
	}


	public void setTime_interval(String time_interval) {
		this.time_interval = time_interval;
	}
	
	public boolean empty() {
		if (pattern.length() > 0 && behavioral_model.length() > 0 && arrival_rate.length() > 0
				&& avg_transactions_second == -200 && avg_usr_number == -200) {
			return true;
		}

		return false;
	}
	
}