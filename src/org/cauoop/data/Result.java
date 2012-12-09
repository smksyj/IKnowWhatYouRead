package org.cauoop.data;

public class Result {
	private String category;
	private double ratio;
	
	public Result(String category, double ratio) {
		this.category = category;
		this.ratio = ratio;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public double getRatio() {
		return this.ratio;
	}
}
