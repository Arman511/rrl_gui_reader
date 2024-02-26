package com.arman511.extra;

public class SearchResultBook {
	private String codeString;
	private String titleString;
	private float rating;
	private int pages;
	private int chapters;
	private int views;

	// Default constructor
	public SearchResultBook() {
	}

	// Parameterized constructor
	public SearchResultBook(String codeString, String titleString, float rating, int pages, int chapters, int views) {
		this.codeString = codeString;
		this.titleString = titleString;
		this.rating = rating;
		this.pages = pages;
		this.chapters = chapters;
		this.views = views;
	}

	// Getter and Setter methods for codeString
	public String getCodeString() {
		return codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	// Getter and Setter methods for titleString
	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	// Getter and Setter methods for rating
	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	// Getter and Setter methods for pages
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	// Getter and Setter methods for chapters
	public int getChapters() {
		return chapters;
	}

	public void setChapters(int chapters) {
		this.chapters = chapters;
	}

	// Getter and Setter methods for views
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
}
