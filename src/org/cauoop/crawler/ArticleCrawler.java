package org.cauoop.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ArticleCrawler {
	public String getHtml(String str, String hereText) {
		if ( str == null | str.equals("") ) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getHtml(String str) {
		return getHtml(str, "");
	}

	public String selectGet(String address, String hereLink, String hereText, String deny) {
		String data="";
		int number = 0;
		
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
				System.out.println("link : " + link);
				data = data + getHtml(link,hereText) + " ";
				number++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(number + " " + data);
		return number + " "+ data;
	}

	public String selectGet(String address, String hereLink, String hereText) {
		return selectGet(address, hereLink, hereText, "");
	}

	public String selectGet(String address, String hereText) {
		return selectGet(address, "", hereText, "");
	}

	public String autoGet(String category) {
		if(category.equalsIgnoreCase("politics")) {
			String naver_politics = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=100", ".section_headline", "#articleBody", "sectionList.nhn");
			String daum_politics = selectGet("http://media.daum.net/politics", ".wrap_newsitem", "div#contentsWrapper");
			
			int value = Integer.parseInt(naver_politics.substring(0, naver_politics.indexOf(" ")));
			value += Integer.parseInt(daum_politics.substring(0, daum_politics.indexOf(" ")));
			
			return value + " " +naver_politics + " " + daum_politics;
		} else if(category.equalsIgnoreCase("economy")) {
			String naver_economic = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=101", ".section_headline", "#articleBody", "sectionList.nhn");
			String daum_economic = selectGet("http://media.daum.net/economic", ".wrap_newsitem", "div#contentsWrapper");
			

			int value = Integer.parseInt(naver_economic.substring(0, naver_economic.indexOf(" ")));
			value += Integer.parseInt(daum_economic.substring(0, daum_economic.indexOf(" ")));
			
			return value + " " + naver_economic + daum_economic;
		} else if(category.equalsIgnoreCase("society")) {
			String naver_society = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=102", ".section_headline", "#articleBody", "sectionList.nhn");
			String daum_society = selectGet("http://media.daum.net/society", ".wrap_newsitem", "div#contentsWrapper");
			

			int value = Integer.parseInt(naver_society.substring(0, naver_society.indexOf(" ")));
			value += Integer.parseInt(daum_society.substring(0, daum_society.indexOf(" ")));
			
			return value + " " + naver_society + " " + daum_society;
		} else if(category.equalsIgnoreCase("world") || category.equalsIgnoreCase("foreign") || category.equalsIgnoreCase("international")) {
			String naver_world = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=104", ".section_headline", "#articleBody", "sectionList.nhn");
			String daum_world = selectGet("http://media.daum.net/foreign", ".wrap_newsitem", "div#contentsWrapper");
			

			int value = Integer.parseInt(naver_world.substring(0, naver_world.indexOf(" ")));
			value += Integer.parseInt(daum_world.substring(0, daum_world.indexOf(" ")));
			
			return value + " " + naver_world + " " + daum_world;
		} else if(category.equalsIgnoreCase("entertain")) {
			String naver_entertain = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=106", ".section_headline", "#articleBody", "sectionList.nhn");

			int value = Integer.parseInt(naver_entertain.substring(0, naver_entertain.indexOf(" ")));
			
			return value + " " + naver_entertain;
		} else if(category.equalsIgnoreCase("tech")) {
			String naver_tech = selectGet("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105", ".section_headline", "#articleBody", "sectionList.nhn");
			String daum_tech = selectGet("http://media.daum.net/tech", ".wrap_newsitem", "div#contentsWrapper");

			int value = Integer.parseInt(naver_tech.substring(0, naver_tech.indexOf(" ")));
			value += Integer.parseInt(daum_tech.substring(0, daum_tech.indexOf(" ")));
			
			return value + " " + naver_tech + " " + daum_tech;
		}
		return null;
	}
}