package com.tuwq.mobilesafe.bean;

public class SMSInfo {

	public String address;
	public String date;
	public String type;
	public String body;
	
	public SMSInfo(String address, String date, String type, String body) {
		super();
		this.address = address;
		this.date = date;
		this.type = type;
		this.body = body;
	};
	
}
