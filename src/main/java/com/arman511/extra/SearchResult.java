package com.arman511.extra;

import java.util.ArrayList;

public class SearchResult {
	ArrayList<SearchResultBook> resultArrayList;
	int pages;
	int page;

	public SearchResult(int gPages, int gPage) {
		resultArrayList = new ArrayList<SearchResultBook>();
		page = gPage;
		pages = gPages;
	}

	public void insertFiction(SearchResultBook book) {
		resultArrayList.add(book);
	}
}
