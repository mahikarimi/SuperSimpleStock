package com.makarimi.supersimple.stock;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommonStockTest {

	private static Stock commonStock;
	
	@BeforeClass
	public static void setup() {
		commonStock = new CommonStock("TEA", 100.0, 20.0);
	}
	
	@Test
	public void calculateDividendYield() throws StockException {
		double yield = commonStock.getDividendYield(67);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceZero() throws StockException {
		double yield = commonStock.getDividendYield(0);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceNegative() throws StockException {
		double yield = commonStock.getDividendYield(-7);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@AfterClass
	public static void tearDown() {
		commonStock = null;
	}
}
