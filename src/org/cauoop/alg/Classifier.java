package org.cauoop.alg;

import java.util.LinkedList;
import java.util.List;

public class Classifier {
	public Classifier() {
	}
	
	public List<String> classification(List<LinkedList<String>> wordStatistic, List<String> numOfReadingArticle) {
		double sum = 0;
		double[] categoryCount = new double[wordStatistic.size()];
		//int[] wordSum = new int[wordStatistic.get(0).size()];
//		int[] wordSum = new int[wordStatistic.size()];
		LinkedList<String> result = new LinkedList<String>();
				
		for ( int i = 0; i < wordStatistic.size(); i++ ) {
			categoryCount[i] = 1;
			
			for ( int j = 1; j < wordStatistic.get(0).size(); j++ ) {
				if(Integer.parseInt(wordStatistic.get(i).get(j))==0){
					categoryCount[i] += (double)1/Double.parseDouble(numOfReadingArticle.get(i*2+1));
				}else{
//					categoryCount[i] *= (double)(Integer.parseInt(wordStatistic.get(i).get(j)))/Double.parseDouble(numOfReadingArticle.get(i*2+1));
					categoryCount[i] += (double)(Integer.parseInt(wordStatistic.get(i).get(j)))/Double.parseDouble(numOfReadingArticle.get(i*2+1));
				}
			}
		}
//
//		for (int i = 0; i < categoryCount.length; i++ ) {
//			System.out.println(numOfReadingArticle.get(i*2) + " : " + categoryCount[i]);
//		}

		for ( int i = 0; i < categoryCount.length; i++ ) {
			sum += categoryCount[i];
		}
//
//		System.out.println("sum : " + sum);
//
		for ( int i = 0; i < categoryCount.length; i++ ) {
//			System.out.println(numOfReadingArticle.get(i*2)+" : " + (categoryCount[i]/(double)sum)*100 +"%");
			result.add(numOfReadingArticle.get(i*2)+" : " + (categoryCount[i]/(double)sum)*100 + "%");
		}		

		return result;
	}
	
	
}