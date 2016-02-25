package com.makarimi.supersimple.trade;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.makarimi.supersimple.stock.CommonStock;
import com.makarimi.supersimple.stock.PreferredStock;
import com.makarimi.supersimple.stock.Stock;
import com.makarimi.supersimple.stock.StockException;

public class TradeManagerUtilTest {

	private static TradeManagerImpl tradeManager;

	@Before
	public void setup() {
		tradeManager = new TradeManagerImpl();
	}
	@Test (expected = StockException.class)
	public void testValidateIntervalNegative() throws StockException {
		TradeManagerUtil.validateInterval(-1);
	}

	@Test (expected = StockException.class)
	public void testValidateIntervalZero() throws StockException {
		TradeManagerUtil.validateInterval(0);
	}
	
	@Test
	public void testValidateInterval() throws StockException {
		TradeManagerUtil.validateInterval(7);
	}
	
	@Test
	public void testAddStock() {
		Stock stock1 = new CommonStock("GIN", 4, 6);
		Stock stock2 = new PreferredStock("TEA", 4, 6, 6);
		TradeManagerUtil.addStock(tradeManager, stock1);
		TradeManagerUtil.addStock(tradeManager, stock2);
		assertTrue(tradeManager.getStocks().size() == 2);
	}
	
	@Test
	public void testgetStockBySymbol() throws StockException {
		Stock stock1 = new CommonStock("ALE", 4, 6);
		TradeManagerUtil.addStock(tradeManager, stock1);
		Stock stock = TradeManagerUtil.getStockBySymbol(tradeManager.getStocks(), "ALE");
		assertNotNull(stock);
	}
	
	@Test (expected = StockException.class)
	public void testGetStockBySymbolNonExistent() throws StockException {
		Stock stock = TradeManagerUtil.getStockBySymbol(tradeManager.getStocks(), "GINN");
		assertNull(stock);
	}
}
