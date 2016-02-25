package com.makarimi.supersimple.stock;

public enum StockTestConstants {

	DELTA(0.01);
	
	private final double value;

	StockTestConstants(double value) {
		this.value = value;
	}

	public double value() {
		return value;
	}
}
