package org.cauoop.alg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Classifier {
	public List<String> filtering(List<LinkedList<String>> wordStatistic) {
		int sum = 0;

		
		ArrayList<Integer> poli = new ArrayList<Integer>();
		poli.add(200);
		poli.add(10);
		
		ArrayList<Integer> IT = new ArrayList<Integer>();
		IT.add(50);
		IT.add(0);
		ArrayList<Integer> enter = new ArrayList<Integer>();
		enter.add(2);
		enter.add(3);

		ArrayList<Integer> numOfReadingArticle = new ArrayList<Integer>();
		numOfReadingArticle.add(100);
		numOfReadingArticle.add(50);
		numOfReadingArticle.add(1);
		
		List<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		temp.add(poli);
		temp.add(IT);
		temp.add(enter);
		double[] categoryCount = new double[temp.size()];
		int[] wordSum = new int[temp.get(0).size()];
		
		//첫번째 get이 카테고리 갯수 i, 두번째 get이 단어갯수 j
		for ( int i = 0; i < temp.size(); i++ ) {
			for(int j= 0 ; j < temp.get(0).size() ; j++){
				wordSum[j] += temp.get(i).get(j);
			}
		}
		
		for ( int i = 0; i < temp.size(); i++ ) {
			categoryCount[i] = 1;
		
			
			for ( int j = 0; j < temp.get(0).size(); j++ ) {
				
				categoryCount[i] *= (double)(temp.get(i).get(j))/numOfReadingArticle.get(i);
			}
		}

		for (int i = 0; i < categoryCount.length; i++) {
			System.out.println(categoryCount[i]);
		}

		for ( int i = 0; i < categoryCount.length; i++ ) {
			sum += categoryCount[i];
		}

		System.out.println("sum : " + sum);

		for ( int i = 0; i < categoryCount.length; i++ ) {
			System.out.println("percentage : " + categoryCount[i]/(double)sum);
		}		

		return null;
	}
	public static void main(String[] args) {
		Classifier a= new Classifier();
		int[][] aa = new int[1][1];
		a.filtering(aa);
	}
}
