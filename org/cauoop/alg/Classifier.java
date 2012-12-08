package org.cauoop.alg;

import java.util.ArrayList;
import java.util.List;

public class Classifier {
	public List<String> filtering(int[][] categoryAndFrequency) {
		int sum = 0;
		
		ArrayList<Integer> poli = new ArrayList<Integer>();
		poli.add(20);
		poli.add(30);
		ArrayList<Integer> IT = new ArrayList<Integer>();
		IT.add(30);
		IT.add(10);
		ArrayList<Integer> enter = new ArrayList<Integer>();
		enter.add(40);
		enter.add(60);

		List<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		temp.add(poli);
		temp.add(IT);
		temp.add(enter);
		int[] categoryCount = new int[temp.size()];

		for ( int i = 0; i < temp.size(); i++ ) {
			categoryCount[i] = 1;

			for ( int j = 0; j < temp.get(0).size(); j++ ) {
				categoryCount[i] *= temp.get(i).get(j);
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
}
