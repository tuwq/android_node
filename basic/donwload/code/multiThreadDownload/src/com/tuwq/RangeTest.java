package com.tuwq;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RangeTest {
	public static void main(String[] args) {
		String path = "http://192.168.147.3:9001/img/news.xml";
		try {
			URL url = new URL(path);
			HttpURLConnection openConnection = (HttpURLConnection)url.openConnection();
			openConnection.setRequestMethod("GET");
			openConnection.setConnectTimeout(10000);
			openConnection.setRequestProperty("Range", "bytes=25-40");
			if (openConnection.getResponseCode() == 206) {
				InputStream inputStream = openConnection.getInputStream();
				String result = Utils.getStringFromStream(inputStream);
				System.out.println(result);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
