package com.makarimi.supersimple.stock;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StockFactoryTest {

	private static StockFactory factory;
	
	@BeforeClass
	public static void setup(){
		factory = new StockFactory();
	}
	
	@Test
	public void createCommonStock() throws StockException {
		Stock commonStock = factory.createStock("GIN", Stock.TYPE.COMMON, 2, 0, 7);
		assertNotNull(commonStock);
	}
	
	@Test
	public void createPreferredStock() throws StockException {
		Stock prefferedStock = factory.createStock("GIN", Stock.TYPE.PREFFERED, 2, 6, 7);
		assertNotNull(prefferedStock);
	}
	
	@Test (expected = StockException.class)
	public void createStockNullType() throws StockException {
		Stock stock = factory.createStock("GIN", null, 2, 6, 7);
		assertNull(stock);
	}
	
	@AfterClass
	public static void tearDown() {
		factory = null;
	}
}
