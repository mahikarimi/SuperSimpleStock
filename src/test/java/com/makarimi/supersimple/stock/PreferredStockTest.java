package com.makarimi.supersimple.stock;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PreferredStockTest {

	private PreferredStock prefferedStock;
	private PreferredStock prefferedStockInvalidValues;
	
	@Before
	public void setup() {
		prefferedStock = new PreferredStock("TEA", 5, 2, 200);
		prefferedStockInvalidValues = new PreferredStock("TEA", -5, -2, -200);
	}
	
	@Test
	public void setAndGetFixedDividend() {
		prefferedStock.setFixedDividend(6);
		assertEquals(6, prefferedStock.getFixedDividend(), StockTestConstants.DELTA.value());
	}
	
	@Test
	public void calculateDividendYield() throws StockException {
		double yield = prefferedStock.getDividendYield(1000);
		assertEquals(0.004, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceZero() throws StockException {
		double yield = prefferedStock.getDividendYield(0);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceNegative() throws StockException {
		double yield = prefferedStock.getDividendYield(-2);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceNegativeFixedDividend() throws StockException {
		double yield = prefferedStockInvalidValues.getDividendYield(2);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
	
	@Test (expected = StockException.class)
	public void calculateDividendYieldTickerPriceNegativeParValue() throws StockException {
		prefferedStockInvalidValues.setFixedDividend(5);
		prefferedStock.setParValue(-7);
		double yield = prefferedStockInvalidValues.getDividendYield(2);
		assertEquals(1.49, yield, StockTestConstants.DELTA.value());
	}
}
