package org.cauoop.IKWYR;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.cauoop.alg.Classifier;
import org.cauoop.crawler.ArticleCrawler;
import org.cauoop.data.WordDatabase;
import org.cauoop.filter.ArticleFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestProgram extends JFrame {
	public static void main(String[] args) throws IOException {
		ArticleCrawler crawler = new ArticleCrawler();
		
		Document document = Jsoup.connect("http://news.naver.com/main/president2012/news/read.nhn?mid=hot&sid1=154&cid=909465&iid=573039&oid=001&aid=0005978319&ptype=011").get();
		Elements select = document.select("#articleBody");
		
		ArticleFilter filter = new ArticleFilter("정치", select.text());
		
		WordDatabase database = new WordDatabase();
		
//		database.learningInsert(new LinkedList<String>(Arrays.asList(filter.getSplit())), filter.getCategory());
		
		List<String> wordList = new LinkedList<>();
		wordList.add("박근혜");
		wordList.add("문재인");
		
		List<LinkedList<String>> wordStatistic = database.wordStatistic(wordList);
		
		for (LinkedList<String> linkedList : wordStatistic) {
			for (String string : linkedList) {
				System.out.println(string);
			}
		}
		
		Classifier classifier = new Classifier();
//		classifier.filtering(wordStatistic);
	}
}
