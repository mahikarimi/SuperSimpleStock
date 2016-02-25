package com.makarimi.supersimple.stock;

@SuppressWarnings("serial")
public class StockException extends Exception {

	String msg = null;
	
	public StockException(String msg) {
		super(msg);
	}

}
