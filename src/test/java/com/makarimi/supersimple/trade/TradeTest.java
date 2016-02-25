package com.makarimi.supersimple.trade;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.makarimi.supersimple.stock.CommonStock;
import com.makarimi.supersimple.stock.Stock;

public class TradeTest {

	private Trade trade;
	private static Stock stock;
	
	@BeforeClass
	public static void setup() {
		stock = new CommonStock("TEA", 100.0, 5.0);
	}
	
	@Before
	public void init() {
		trade = new Trade(stock,Trade.TYPE.BUY, 10, 100);
	}
	
	@Test
	public void getTradeProperties() {
		assertEquals(Trade.TYPE.BUY, trade.getType());
		assertEquals(10, trade.getQuantity());
		assertEquals(100, trade.getPrice(), 0);
	}
	
	@Test
	public void setTradeProperties() {
		trade.setPrice(45.0);
		trade.setQuantity(600);
		trade.setType(Trade.TYPE.SELL);
		assertEquals(Trade.TYPE.SELL, trade.getType());
		assertEquals(600, trade.getQuantity());
		assertEquals(45, trade.getPrice(), 0);
	}
	
	@AfterClass
	public static void tearDown() {
		stock = null;
	}
}
