package com.arman511.extra;

import java.util.ArrayList;

public class SearchTerms {
	private String titleString;
	private String authorString;
	private String keywordString;
	// Constructor
	public SearchTerms(String titleString, String authorString, String keywordString) {
		this.titleString = titleString;
		this.authorString = authorString;
		this.keywordString = keywordString;
		
		
	}

	// Getter methods
	public String getTitleString() {
		return titleString;
	}

	public String getAuthorString() {
		return authorString;
	}

	public String getKeywordString() {
		return keywordString;
	}

	// Setter methods
	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public void setAuthorString(String authorString) {
		this.authorString = authorString;
	}

	public void setKeywordString(String keywordString) {
		this.keywordString = keywordString;
	}

	public String getUrlFragment() {
		ArrayList<String> termStrings = new ArrayList<String>();
		if(!titleString.isBlank()) {
			termStrings.add("title=" + titleString);
		}
		if(!authorString.isBlank()) {
			termStrings.add("author=" + authorString);
			}
		if (!keywordString.isBlank()) {
			termStrings.add("keywords=" + keywordString);
		}
		return String.join("&", termStrings);
	}

	public String searchTitle() {
		ArrayList<String> termStrings = new ArrayList<String>();
		if (!titleString.isBlank()) {
			termStrings.add("Title: " + titleString);
		}
		if (!authorString.isBlank()) {
			termStrings.add("Author: " + authorString);
		}
		if (!keywordString.isBlank()) {
			termStrings.add("Keywords: " + keywordString);
		}
		return String.join(", ", termStrings);

	}
}
