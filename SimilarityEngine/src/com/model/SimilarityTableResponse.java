package com.model;

import java.util.List;

public class SimilarityTableResponse {
	public String TableName;
	public List<TableEntry> Entries;

	public String getTableName() {
		return TableName;
	}

	public void setTableName(String tableName) {
		TableName = tableName;
	}

	public List<TableEntry> getEntries() {
		return Entries;
	}

	public void setEntries(List<TableEntry> entries) {
		Entries = entries;
	}
	public SimilarityTableResponse() {
		super();
	}


}
