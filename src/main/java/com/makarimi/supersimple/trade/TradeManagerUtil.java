package com.makarimi.supersimple.trade;

import java.util.Map;

import com.makarimi.supersimple.stock.Stock;
import com.makarimi.supersimple.stock.StockException;

public class TradeManagerUtil {

	public static void validateInterval(long interval) throws StockException {
		if (interval <= 0 || interval > Long.MAX_VALUE) {			
			throw new StockException("validateInterval - Invalid interval value has been specified");
		}
	}
	
	public static ITradeManager addStock(ITradeManager tradeManager, Stock stock) {
		Map<String, Stock> stocks = tradeManager.getStocks();
		stocks.put(stock.getSymbol(), stock);
		return tradeManager;
	}
	
	public static Stock getStockBySymbol(Map<String, Stock> stocks, String symbol) throws StockException {
		Stock stock =  stocks.get(symbol);
		if (stock == null) {
			throw new StockException("calculateStockPriceWithinTradeInterval - Stock symbol " + symbol + " cannot be found");
		}
		return stock;
	}
}
