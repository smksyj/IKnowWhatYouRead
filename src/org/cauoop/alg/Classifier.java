package org.cauoop.alg;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cauoop.data.Result;

public class Classifier {
	public Classifier() {
	}
	
	public List<Result> filtering(List<LinkedList<String>> wordStatistic, List<String> numOfReadingArticle) {
		List<Result> ret = new LinkedList<Result>();
		double sum = 0;
		
		double[] categoryCount = new double[wordStatistic.size()];
		int[] wordSum = new int[wordStatistic.get(0).size()];
		
		//첫번째 get이 카테고리 갯수 i, 두번째 get이 단어갯수 j
		for ( int i = 0; i < wordStatistic.size(); i++ ) {
			for(int j= 0 ; j < wordStatistic.get(0).size() ; j++){
				wordSum[j] += Integer.parseInt(wordStatistic.get(i).get(j));
			}
		}
		
		for ( int i = 0; i < wordStatistic.size(); i++ ) {
			categoryCount[i] = 1;		
			
			for ( int j = 0; j < wordStatistic.get(0).size(); j++ ) {
				if(Integer.parseInt(wordStatistic.get(i).get(j))==0){
					categoryCount[i] *= (double)1/Double.parseDouble(numOfReadingArticle.get(i));
				}else{
					categoryCount[i] *= (double)(Integer.parseInt(wordStatistic.get(i).get(j)))/Double.parseDouble(numOfReadingArticle.get(i));
				}
			}
		}

		for (int i = 0; i < categoryCount.length; i++) {
			System.out.println(categoryCount[i]);
		}

		for ( int i = 0; i < categoryCount.length; i++ ) {
			sum += categoryCount[i];
		}

		System.out.println("sum : " + sum);

		// TODO : modify this code
		for ( int i = 0; i < categoryCount.length; i++ ) {
			//ret.add(categoryCount[i]/(double)sum);
		}

		return null;
	}
}