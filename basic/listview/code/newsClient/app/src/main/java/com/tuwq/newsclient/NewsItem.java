package com.tuwq.newsclient;

public class NewsItem {
	public String title;
	public String description;
	public String img;
	public String type;
	public String comment;
	@Override
	public String toString() {
		return "NewsItem [title=" + title + ", description=" + description
				+ ", img=" + img + ", type=" + type + ", comment=" + comment
				+ "]";
	}
	
	
}
