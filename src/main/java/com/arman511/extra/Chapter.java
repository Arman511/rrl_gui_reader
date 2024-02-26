package com.arman511.extra;

public class Chapter {
	private String titleString;
	private String urlString;
	private int order;

	public Chapter(String gtitleString, String gurlString, int gorder) {
		this.setTitleString(gtitleString);
		this.setUrlString(gurlString);
		this.setOrder(gorder);
		// TODO Auto-generated constructor stub
	}

	public String getTitleString() {
		return titleString;
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
