package com.mobile.android;

public class Item_setting {
	String Title;
	String Url;
	String size;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Item_setting(String title, String url, String size) {
		super();
		Title = title;
		Url = url;
		this.size = size;
	}

}
