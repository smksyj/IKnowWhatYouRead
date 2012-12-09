package org.cauoop.filter;

public class ArticleFilter {
	private String category = null;
	private String[] split = null;
	
	public ArticleFilter(String word,String line){
		category = word;
		split = line.replaceAll("(\\*|@|#|\\$|_|&|\\!|\\%|\\.|\\,|\\(.*\\)|\\>|-)", "").replace("를 ", " ").replace("는 ", " ").replace("이 ", " ").replace("가 ", " ").replace("은 ", " ").replace("에서 ", " ").replace("을 ", " ").replace("라고 ", " ").replace("라 ", " ").replace("과 ", " ").replace("에 ", " ").replace("하고 ", " ").replace("하는 ", " ").replace("으로 ", " ").split(" ");
	}
	
	public String getCategory(){
		return category;
	}
	
	public String[] getSplit(){
		return split;
	}
}
