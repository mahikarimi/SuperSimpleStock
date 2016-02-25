package com.makarimi.supersimple.trade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.makarimi.supersimple.stock.Stock;
import com.makarimi.supersimple.stock.StockException;
import com.makarimi.supersimple.stock.StockFactory;
import com.makarimi.supersimple.stock.StockTestConstants;
public class TradeManagerTest {

	private TradeManagerImpl tradeManager;
	private static StockFactory stockFactory;
	private static Stock teaStock, popStock, aleStock, ginStock, joeStock;
	
	@BeforeClass
	public static void setup() throws StockException {
		stockFactory = new StockFactory();
		
		teaStock = stockFactory.createStock("TEA", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		teaStock.setTickerPrice(10);
		
		popStock = stockFactory.createStock("POP", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		popStock.setTickerPrice(15);
		
		aleStock = stockFactory.createStock("ALE", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		aleStock.setTickerPrice(20);
		
		ginStock = stockFactory.createStock("GIN", Stock.TYPE.PREFFERED, 1.0, 3.0, 7.0);
		ginStock.setTickerPrice(25);
		
		joeStock = stockFactory.createStock("JOE", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		joeStock.setTickerPrice(30);
	}

	@Before
	public void init() throws StockException{
		tradeManager = new TradeManagerImpl();
		
		TradeManagerUtil.addStock(tradeManager, teaStock);
		TradeManagerUtil.addStock(tradeManager, popStock);
		TradeManagerUtil.addStock(tradeManager, aleStock);
		TradeManagerUtil.addStock(tradeManager, ginStock);
		TradeManagerUtil.addStock(tradeManager, joeStock);
	}

	@Test (expected = StockException.class)
	public void recordTradeInvalidSymbol() throws StockException {
		boolean recorded = tradeManager.recordTrade("TEST", 5, Trade.TYPE.BUY);
		assertTrue(recorded);
	}

	@Test
	public void recordSellTrade() throws StockException {
		boolean recorded = tradeManager.recordTrade("GIN", 5, Trade.TYPE.SELL);
		assertTrue(recorded);
	}
	
	@Test
	public void recordBuyTrade() throws StockException {
		boolean recorded = tradeManager.recordTrade("GIN", 5, Trade.TYPE.BUY);
		assertTrue(recorded);
	}
	
	@Test 
	public void getTradesDWithinInterval() throws StockException {
		tradeManager.recordTrade("GIN", 5, Trade.TYPE.BUY);
		tradeManager.recordTrade("GIN", 6, Trade.TYPE.SELL);
		List<Trade> trades = tradeManager.getTradesWithinInterval("GIN", 15);
		assertTrue(trades.size() == 2);
	}
	
	@Test (expected = StockException.class)
	public void getTradesWithin0Interval() throws StockException {
		List<Trade> trades = tradeManager.getTradesWithinInterval("GIN", 0);
		assertTrue(trades.size() == 2);
	}
	
	@Test (expected = StockException.class)
	public void getTradesDWithinNegativeInterval() throws StockException {
		List<Trade> trades = tradeManager.getTradesWithinInterval("GIN",-1);
		assertTrue(trades.size() == 2);
	}
	
	@Test
	public void calculateStockPriceBasedOnLast15Mins() throws StockException {
		tradeManager.recordTrade("TEA", 5, Trade.TYPE.BUY);
		tradeManager.recordTrade("TEA", 6, Trade.TYPE.SELL);
		double price = tradeManager.calculateStockPriceWithinTradeInterval("TEA", 15);
		assertEquals(10.0, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateStockPriceBasedOnLast15MinsNullStock() throws StockException {
		double price = tradeManager.calculateStockPriceWithinTradeInterval("", 15);
		assertEquals(10, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateStockPriceBasedOnLast15MinNoTradesForStock() throws StockException {
		double price = tradeManager.calculateStockPriceWithinTradeInterval("ALE", 15);
		assertEquals(10, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateStockPriceZeroInterval() throws StockException {
		double price = tradeManager.calculateStockPriceWithinTradeInterval("ALE", 0);
		assertEquals(10, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateStockPriceNegativeInterval() throws StockException {
		double price = tradeManager.calculateStockPriceWithinTradeInterval("ALE", -4);
		assertEquals(10, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateStockPriceNegativeLargerThanMaxInterval() throws StockException {
		double price = tradeManager.calculateStockPriceWithinTradeInterval("ALE", 2*Long.MAX_VALUE);
		assertEquals(10, price, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateGBCEAllShareIndexNoStocks() throws StockException {
		double allShareIndex = new TradeManagerImpl().calculateGBCEAllShareIndex();
		assertEquals(18.63, allShareIndex, StockTestConstants.DELTA.value());
	}
	
	@Test 
	public void calculateGBCEAllShareIndexAtLeastOneZeroStockPrice() throws StockException {
		ITradeManager tradeManager = new TradeManagerImpl();
		Stock teaStock1 = stockFactory.createStock("TEA", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		teaStock1.setTickerPrice(0);
		Stock teaStock2 = stockFactory.createStock("TEA2", Stock.TYPE.COMMON, 1.0, 0, 7.0);
		teaStock2.setTickerPrice(10);
		
		tradeManager = TradeManagerUtil.addStock(tradeManager, teaStock1);
		tradeManager = TradeManagerUtil.addStock(tradeManager, teaStock2);
		
		double allShareIndex = tradeManager.calculateGBCEAllShareIndex();
		assertEquals(0.0, allShareIndex, StockTestConstants.DELTA.value());
	}
	
	@Test
	public void calculateGBCEAllShareIndex() throws StockException {
		double allShareIndex = tradeManager.calculateGBCEAllShareIndex();
		assertEquals(18.63, allShareIndex, StockTestConstants.DELTA.value());
	}
	
	@Test
	public void getStockBySymbolExists() throws StockException {
		Stock stock = TradeManagerUtil.getStockBySymbol(tradeManager.getStocks(),"GIN");
		assertNotNull(stock);
	}
	
	@Test (expected = StockException.class)
	public void getStockBySymbolDoesNotExists() throws StockException {
		Stock stock = TradeManagerUtil.getStockBySymbol(tradeManager.getStocks(), "GIN2");
		assertNull(stock);
	}
	
	@AfterClass
	public static void tearDown() {
		stockFactory = null;
		teaStock = null;
		joeStock = null;
		aleStock = null;
		ginStock = null;
		popStock = null;
	}
}
