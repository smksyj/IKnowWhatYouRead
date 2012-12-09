package org.cauoop.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArticleCrawler {
	public String getHtml(String str, String hereText) {
		if ( str == null ) {
			return null;
		}

		try {
			Document doc = Jsoup.connect(str).get();
			String page;
			if(hereText.isEmpty()) {
				page = doc.body().text();
			} else {
				page = doc.select(hereText).text();
			}
			return page.replaceAll("\\p{Punct}","");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getHtml(String str) {
		return getHtml(str, "");
	}

	public String selectGet(String address, String hereLink, String hereText, String deny) {
		String data="";
		try {
			Elements newsHeadlines;
			if(hereLink.isEmpty()) {
				newsHeadlines = Jsoup.connect(address).get().select("a[href]");
			} else {
				newsHeadlines = Jsoup.connect(address).get().select(hereLink).select("a[href]");
			}
			for(Element l:newsHeadlines) {
				String link = l.attr("abs:href");
				if(!deny.isEmpty()&&link.contains(deny)) {
					continue;
				}
				data = data + getHtml(link,hereText) + " ";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(data);
		return data;
	}

	public String selectGet(String address, String hereLink, String hereText) {
		return selectGet(address, hereLink, hereText, "");
	}

	public String selectGet(String address, String hereText) {
		return selectGet(address, "", hereText, "");
	}

	public String autoGet(String category) {
		if(category.equalsIgnoreCase("정치")) {
			String naver_politics = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=100", ".section_headline", "sectionList.nhn");
			String daum_politics = selectGet("http://media.daum.net/politics", ".wrap_newsitem", "");
			return naver_politics + " " + daum_politics;
		} else if(category.equalsIgnoreCase("경제")) {
			String naver_economic = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=101", ".section_headline", "sectionList.nhn");
			String daum_economic = selectGet("http://media.daum.net/economic", ".wrap_newsitem", "");
			return naver_economic + daum_economic;
		} else if(category.equalsIgnoreCase("사회")) {
			String naver_society = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=102", ".section_headline", "sectionList.nhn");
			String daum_society = selectGet("http://media.daum.net/society", ".wrap_newsitem", "");
			return naver_society + " " + daum_society;
		} else if(category.equalsIgnoreCase("world") || category.equalsIgnoreCase("foreign") || category.equalsIgnoreCase("international")) {
			String naver_world = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104", ".section_headline", "sectionList.nhn");
			String daum_world = selectGet("http://media.daum.net/foreign", ".wrap_newsitem", "");
			return naver_world + " " + daum_world;
		} else if(category.equalsIgnoreCase("문화")) {
			String naver_entertain = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=106", ".section_headline","sectionList.nhn");
			return naver_entertain;
		}
		return null;
	}
}