package org.cauoop.data;

import java.util.ArrayList;

public class Row {
	public String categoryName;
	public ArrayList<Integer> values;
	
	public Row(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void addValue(int value) {
		this.values.add(value);
	}
	
	public int getValue(int idx) {
		return this.values.get(idx);
	}
}
