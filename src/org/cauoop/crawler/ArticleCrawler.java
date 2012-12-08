package org.cauoop.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;

public class ArticleCrawler {
	public String getHtml(String str) {
		try {
			return Jsoup.connect(str).get().body().text().replaceAll("(\\p{Punct}|\\\"|\\\\.|¡¤|¡Ú|¢¾|¢À|¢Ý)","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}