package common;

import javax.servlet.http.HttpServletRequest;

public class PagingDO {
	int rowStart;
	int rowEnd;
	int rowCount;
	int rowMax;		/* row max per page */
	int pageNum;		/* page max per pagination */ 
	int pageStart;
	int pageEnd;
	int pageCount;
	int pageMax;
	
	public String searchKey;
	public String searchVal;
	
	public PagingDO() {
		this.rowMax = 10;
		this.pageMax = 10;
		this.searchKey = "";
		this.searchVal = "";
	}
	
	public PagingDO(int rowMax, int pageMax) {
		this();
		this.rowMax = rowMax;
		this.pageMax = pageMax;
	}
	
	public int getRowStart() {
		return rowStart;
	}
	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}
	public int getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
	public void open(HttpServletRequest req) {
		String page;

		page = req.getParameter("pageNum");
		if (page == null || page == "") 
			page = "1";

		searchKey = req.getParameter("searchKey");
		searchVal = req.getParameter("searchVal");
		if (searchKey == null || searchKey == "" || searchVal == null || searchVal == null) {
			searchKey = ""; 
			searchVal = "";
		}
		System.out.printf("pageNum %s, searchKey %s, searchVal %s\n", page, searchKey, searchVal);

		pageNum = Integer.parseInt(page);
		rowStart = (pageNum - 1) * rowMax + 1;
		rowEnd = (pageNum * rowMax);
	}
	
	public void close(HttpServletRequest req) {
		if (rowCount % rowMax == 0)
			pageCount = rowCount / rowMax; 
		else
			pageCount = (rowCount / rowMax) + 1;
		
		pageStart = pageNum - ((pageNum - 1) % pageMax);
		pageEnd = pageStart + pageMax - 1;
		if (pageEnd > pageCount)
			pageEnd = pageCount;
	
	}
	
	public void setAttribute(HttpServletRequest req) {
		/*
		 * DO NOT PUT PAGING ITSELF
		 * TOO LONG CODE AT JSP 
		 */
		req.setAttribute("rowCount", rowCount);
		req.setAttribute("rowStart", rowStart);
		req.setAttribute("rowEnd", rowEnd);
		req.setAttribute("rowMax", rowMax);
		
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("pageStart", pageStart);
		req.setAttribute("pageEnd", pageEnd);
		req.setAttribute("pageMax", pageMax);
		req.setAttribute("searchKey", searchKey);
		req.setAttribute("searchVal", searchVal);
		
		System.out.printf("rowCount %d, rowStart %d, rowEnd %d, rowMax %d\n", rowCount, rowStart, rowEnd, rowMax);
		System.out.printf("pageNum %d, pageCount %d, pageStart %d, pageEnd %d\n", pageNum, pageCount, pageStart, pageEnd);

		
	}
	
}
