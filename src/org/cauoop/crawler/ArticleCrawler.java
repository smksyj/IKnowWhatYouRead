package org.cauoop.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;

public class ArticleCrawler {
	public String getHtml(String str) {
		try {
			if ( str != null ) {
				return Jsoup.connect(str).get().body().text().replaceAll("(\\p{Punct}|\\\"|\\\\.|¡¤|[Oo][Uu][Tt]|[Ll][Ee][Ff][Tt]|[Uu][Pp][Dd][Aa][Tt][Ee]|[Ww][Rr][Ii][Tt][Ee]|[Aa][Nn][Dd]|[Ww][Ii][Tt][Hh]|¡Ú|¢¾|¢À|¢Ý|[Kk][Ee][Yy]|[Tt][Oo]|[Ii][Nn]|[Aa][Ll][Ll]|[Ss][Ee][Ll][Ee][Cc][Tt])","");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}