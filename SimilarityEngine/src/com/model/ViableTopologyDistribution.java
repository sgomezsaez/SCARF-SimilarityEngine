package com.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "viable_topology_distribution")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ViableTopologyDistribution implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	public int id;

	@Column(name = "description")
	public String description;

	@Column(name = "url")
	public String url;

	@Column(name = "dist_url")
	public String dist_url;

	public ViableTopologyDistribution(String description, String url, String dist_url) {
		super();
		this.description = description;
		this.url = url;
		this.dist_url = dist_url;
	}

	public ViableTopologyDistribution() {
		super();
	}

	public String getDist_url() {
		return dist_url;
	}

	public void setDist_url(String dist_url) {
		this.dist_url = dist_url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
