package org.fbertos.persistence.search;

public class QueryPagination {
    private Integer page;
    private Integer itemsPerPage;
   
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public QueryPagination(Integer page, Integer itemsPerPage) {
		this.page = 0;
		this.itemsPerPage = 100;
		
		if (page != null) this.page = page;
		if (itemsPerPage != null) this.itemsPerPage = itemsPerPage;
	}
}
