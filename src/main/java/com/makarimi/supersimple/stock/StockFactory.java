package com.makarimi.supersimple.stock;

public class StockFactory {
	public Stock createStock(String symbol, Stock.TYPE type, double lastDividend, double fixedDividend, double parValue ) throws StockException {
		if (type == null) {
			throw new StockException("createStock - stock type cannot be null");
		}
		
		Stock stock = null;
		
		switch(type) {
			case COMMON:
				stock = new CommonStock(symbol, lastDividend, parValue);
				break;
			case PREFFERED:
				stock = new PreferredStock(symbol, lastDividend, fixedDividend, parValue);
				break;
			}
		return stock;
	}
}
