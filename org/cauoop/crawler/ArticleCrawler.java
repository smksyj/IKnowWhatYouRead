package org.cauoop.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ArticleCrawler {
	public String getHtml(String str) {
		URL url = null;
		HttpURLConnection connection  = null;
		String host = str;
		try {
			url = new URL(host);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
//		LinkedList<String> lines = new LinkedList<String>();
		StringBuilder lines = new StringBuilder();
		String readLine = null;
		
		try {
			connection = (HttpURLConnection)url.openConnection();
			connection.connect();
			String type = connection.getHeaderField("Content-Type");
			//System.out.println("type = [" + type + "]");
			int charsetStartIndex = type.indexOf("=");
			
			String charset = null;
			if ( charsetStartIndex == -1 ) {
				charset = "EUC-KR";
			} else {
				charset = type.substring(type.indexOf("=")+1);	
			}
			
			//System.out.println("charset = [" + charset + "]");
			
			InputStreamReader isr = new InputStreamReader(url.openStream(), charset);
			BufferedReader br = new BufferedReader(isr);
			while((readLine = br.readLine()) != null){
				//lines.add(readLine);
				lines.append(readLine);
			}
			/*
			for(String line : lines) {
				System.out.println("> " + line);
			}
			*/
			//return lines;
			return lines.toString();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
