package com.makarimi.supersimple.stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockTest {
	
	private static Stock stock;
	private static final double tickerPrice = 10;
	
	@BeforeClass
	public static void setup() {
		stock = new CommonStock("GIN", 1.0,1.0);
	}
	
	@Test
	public void createStock() {
		assertNotNull(stock);
		assertEquals("GIN" , stock.getSymbol());
		assertEquals(1, stock.getLastDividend(), StockTestConstants.DELTA.value());
		assertEquals(1, stock.getParValue(), StockTestConstants.DELTA.value());
	}
	
	@Test
	public void testGetConstants() {
		StockTestConstants[] constants = StockTestConstants.values();
		assertNotNull(constants);
		assertTrue(constants.length >= 1);
	}
	
	@Test
	public void testException() {
		StockException ex = new StockException("TEST EXCEPTION");
		assertEquals(ex.getMessage(), "TEST EXCEPTION");
	}
	
	@Test
	public void getPeRatio() throws StockException {
		double peRatio = stock.getPeRatio(tickerPrice);
		assertEquals(10, peRatio, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void getPeRatioZeroDividendZero() throws StockException {
		Stock stock1 = new CommonStock("GIN", 0, 1.0);
		double peRatio = stock1.getPeRatio(0);
		assertEquals(10, peRatio, StockTestConstants.DELTA.value());
	}
	
	@AfterClass
	public static void tearDown() {
		stock = null;
	}
}
