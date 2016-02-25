package com.makarimi.supersimple.stock;

public class CommonStock extends Stock {

	public CommonStock(String symbol, double lastDividend, double parValue) {
		super(symbol,lastDividend, parValue);
	}

	@Override
	public double getDividendYield(double tickerPrice) throws StockException {
		if (tickerPrice <= 0) {
			throw new StockException(String.format("getDividendYield - Ticker price for stock with symbol %s must be a positive value", this.getSymbol()));
		}
		return this.getLastDividend()/tickerPrice;
	}

	@Override
	public String toString() {
		return String.format("Symbol: %s, Last Dividend: %f, Par Value: %f, Ticker Price: %f%n", this.getSymbol(), this.getLastDividend(),
				this.getParValue(), this.getTickerPrice());
	}
}
