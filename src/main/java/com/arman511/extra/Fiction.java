package com.arman511.extra;

import java.util.ArrayList;

public class Fiction {
	private String titleString;
	private String codeString;
	private ArrayList<Chapter> chapters;

	public Fiction(String title, String code, ArrayList<Chapter> gChapters) {
		this.setChapters(gChapters);
		this.setCodeString(code);
		this.setTitleString(title);
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public String getCodeString() {
		return codeString;
	}

	public void setCodeString(String codeString) {
		this.codeString = codeString;
	}

	public ArrayList<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<Chapter> chapters) {
		this.chapters = chapters;
	}
}
