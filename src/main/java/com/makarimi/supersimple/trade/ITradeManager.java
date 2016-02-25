package com.makarimi.supersimple.trade;

import java.util.List;
import java.util.Map;

import com.makarimi.supersimple.stock.Stock;
import com.makarimi.supersimple.stock.StockException;

/**
 * Trade Manager Interface: records trades, calculates stock prices and GBCE share indexe.
 * @author makarimi
 *
 */
public interface ITradeManager {
	
	/**record a trade, with timestamp, quantity of shares, buy or sell indicator and price
	 * @param symbol
	 * @param quantity
	 * @param type
	 * @return 
	 * @throws StockException
	 */
	public boolean recordTrade(String symbol, int quantity, Trade.TYPE type) throws StockException;
	
	/** Gets a list of all trades recorded within a given interval for the given stock
	 * @param stock
	 * @param interval
	 * @return
	 * @throws StockException 
	 */
	public List<Trade> getTradesWithinInterval(String stockSymbol, long interval) throws StockException;
	
	/** Calculates Stock Price based on trades recorded within a given interval
	 * @param stock
	 * @param interval
	 * @return
	 * @throws StockException 
	 */
	public double calculateStockPriceWithinTradeInterval(String stockSymbol, long interval) throws StockException;
	
	/** Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 * @return
	 * @throws StockException 
	 */
	public double calculateGBCEAllShareIndex() throws StockException;

	/**
	 * Get a map of stock symbols to stock objects
	 * @return
	 */
	public Map<String, Stock> getStocks();
	/**
	 * Get a list of all trades
	 * @return
	 */
	public List<Trade> getTrades();
}
