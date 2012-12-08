package org.cauoop.test;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

class UrlTest{
	public void getHtml(String str){
		URL url = null;
		HttpURLConnection connection  = null;
		String host = str;
		try {
			url = new URL(host);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		LinkedList<String> lines = new LinkedList<String>();
		String readLine = null;
		
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.connect();
			String type = connection.getHeaderField("Content-Type");
			System.out.println("type = [" + type + "]");
			int charsetStartIndex = type.indexOf("=");
			
			String charset = null;
			if ( charsetStartIndex == -1 ) {
				charset = "EUC-KR";
			} else {
				charset = type.substring(type.indexOf("=")+1);	
			}
			
			System.out.println("charset = [" + charset + "]");
			
			InputStreamReader isr = new InputStreamReader(url.openStream(), charset);
			BufferedReader br = new BufferedReader(isr);
			while((readLine = br.readLine()) != null){
				lines.add(readLine);
			}
			for(String line : lines) {
				System.out.println("> " + line);
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		UrlTest ut = new UrlTest();
//		ut.getHtml("http://news.naver.com");
		ut.getHtml("http://naver.com");
	}
}