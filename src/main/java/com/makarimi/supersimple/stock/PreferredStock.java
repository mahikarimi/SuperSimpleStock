package com.makarimi.supersimple.stock;

public class PreferredStock extends Stock {

	private double fixedDividend;

	public PreferredStock(String symbol, double lastDividend, double fixedDividend, double parValue) {
		super(symbol, lastDividend, parValue);
		this.setFixedDividend(fixedDividend);
	} 

	@Override
	public double getDividendYield(double tickerPrice) throws StockException {
		if (tickerPrice <= 0) {
			throw new StockException(String.format("getDividendYield: ticker price for stock with symbol: %s must be a positive value", this.getSymbol()));
		}
		
		double fixedDividendYield = this.getFixedDividend();
		double parValue = this.getParValue();
		
		if (fixedDividendYield < 0) {
			throw new StockException(String.format("getDividendYield: fixedDividendYield for stock with symbol: %s cannot be a negative value", this.getSymbol()));
		}
		
		if (parValue < 0) {
			throw new StockException(String.format("getDividendYield: parValue for stock with symbol: %s cannot be a negative value", this.getSymbol()));
		}
		
		return ((fixedDividendYield/(double)100)*parValue)/tickerPrice;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	@Override
	public String toString() {
		return String.format("Symbol: %s ,Last Dividend: %f ,Par Value: %f ,Ticker Price: %f, Fixed Dividend:%f%n", this.getSymbol(), this.getLastDividend(),
				this.getParValue(), this.getTickerPrice(), this.getFixedDividend());
	}
}
