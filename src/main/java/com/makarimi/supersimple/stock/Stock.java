package com.makarimi.supersimple.stock;

public abstract class Stock {

	private String symbol;
	private double lastDividend=0.0;
	private double parValue=0.0;
    private double tickerPrice = 0.0;
	
	public enum TYPE {
		COMMON, PREFFERED
	}
	
	public Stock(String symbol, double lastDividend, double parValue) {
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.parValue = parValue;
	}

	public String getSymbol() {
		return symbol;
	}

	public TYPE setType(TYPE type) {
		return type;
	}
	
	public double getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}
	
	public double getParValue() {
		return parValue;
	}

	public void setParValue(double parValue) {
		this.parValue = parValue;
	}
	
	public abstract double getDividendYield(double tickerPrice) throws StockException;
	
	public double getPeRatio(double tickerPrice) throws StockException {
		if (this.lastDividend == 0) {
			throw new StockException("getPeRatio - Last dividend value cannot be zero");
			
		}
		return tickerPrice/this.lastDividend;
	}
}