package com.makarimi.supersimple.trade;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.makarimi.supersimple.stock.Stock;
import com.makarimi.supersimple.stock.StockException;
import com.makarimi.supersimple.trade.Trade.TYPE;

public class TradeManagerImpl implements ITradeManager {

	private List<Trade> trades;
	private Map<String, Stock> stocks;

	public TradeManagerImpl() {
		this.trades = new LinkedList<Trade>();
		this.stocks = new HashMap<String, Stock>();
	}

	public TradeManagerImpl(List<Trade> trades, Map<String, Stock> stocks) {
		this.trades = trades;
		this.stocks = stocks;
	}

	@Override
	public boolean recordTrade(String stockSymbol, int quantity, TYPE type) throws StockException {
		Stock stock = TradeManagerUtil.getStockBySymbol(stocks, stockSymbol);
		Trade trade = new Trade(stock, type, quantity, stock.getTickerPrice());
		trades.add(trade);
		return true;
	}

	@Override
	public double calculateStockPriceWithinTradeInterval(String stockSymbol, long interval) throws StockException {
		TradeManagerUtil.validateInterval(interval);
		Stock stock = TradeManagerUtil.getStockBySymbol(stocks, stockSymbol);

		double price = 0;
		double sumTradePrices = 0;
		double sumQuantity = 0;

		List<Trade> tradesWithinInterval = getTradesWithinInterval(stockSymbol, interval);

		if (tradesWithinInterval.isEmpty()) {
			throw new StockException(String.format(
					"calculateStockPriceWithinTradeInterval - No trades were recorded in the last %d mins for stock with symbol: %s",
					interval, stock.getSymbol()));
		}

		for (Trade trade : tradesWithinInterval) {
			if (trade.getStock().getSymbol().equals(stock.getSymbol())) {
				sumTradePrices += trade.getPrice() * trade.getQuantity();
				sumQuantity += trade.getQuantity();
			}
		}

		if (sumQuantity == 0) {
			throw new StockException("calculateStockPriceWithinTradeInterval - Stock quantity sum cannot be 0");
		}
		price = sumTradePrices / sumQuantity;
		return price;
	}

	@Override
	public List<Trade> getTradesWithinInterval(String stockSymbol, long interval) throws StockException {

		TradeManagerUtil.validateInterval(interval);
		Stock stock = TradeManagerUtil.getStockBySymbol(stocks, stockSymbol);

		List<Trade> tradesWithinInterval = new LinkedList<Trade>();
		long currentTime = System.currentTimeMillis();
		long startTime = currentTime - (interval * 60 * 1000);

		for (Trade trade : trades) {
			Stock tradeStock = trade.getStock();
			if (tradeStock != null && tradeStock.getSymbol() != null && tradeStock.getSymbol().equals(stock.getSymbol())
					&& (trade.getTimstamp() >= startTime) && (trade.getTimstamp() <= currentTime)) {
				tradesWithinInterval.add(trade);
			}
		}
		return tradesWithinInterval;
	}

	@Override
	public double calculateGBCEAllShareIndex() throws StockException {
		double allShareIndex = 0;
		double product = 1;
		int stockListSize = stocks.size();

		if (stockListSize == 0) {
			throw new StockException("calculateGBCEAllShareIndex - No stock prices are available.");
		}
		
		for (Entry<String, Stock> entry : stocks.entrySet()) {
			Stock stock = entry.getValue();
			double tickerPrice = stock.getTickerPrice();
			if (tickerPrice == 0.0) {
				return 0.0;
			}
			product *= tickerPrice;
		}

		double power = 1 / (double) stockListSize;
		allShareIndex = Math.pow(product, power);
		return allShareIndex;
	}

	@Override
	public Map<String, Stock> getStocks() {
		return this.stocks;
	}

	@Override
	public List<Trade> getTrades() {
		return this.trades;
	}
}
