package org.fbertos.persistence.search;

public class QueryFilter {
	private String query;
	private QueryOrder order;
	private QueryPagination pagination;
	
	public QueryFilter(String query, QueryOrder order, QueryPagination pagination) {
		super();
		this.query = query;
		this.order = order;
		this.pagination = pagination;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public QueryOrder getOrder() {
		return order;
	}
	
	public void setOrder(QueryOrder order) {
		this.order = order;
	}
	
	public QueryPagination getPagination() {
		return pagination;
	}
	
	public void setPagination(QueryPagination pagination) {
		this.pagination = pagination;
	}
}
