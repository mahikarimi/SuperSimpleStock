package com.makarimi.supersimple.trade;

import com.makarimi.supersimple.stock.Stock;

public class Trade {

	private final long timstamp;
	private int quantity;
	private double price;
	private TYPE type;
	private Stock stock;
	public enum TYPE {
		SELL, BUY
	}
	
	public Trade(Stock stock, TYPE type, int quantity, double price) {
		this.stock = stock;
		this.timstamp = System.currentTimeMillis();
		this.type = type;
		this.quantity = quantity;
		this.price = price;
	}
	
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getTimstamp() {
		return timstamp;
	}
	
	@Override
	public String toString() {
		return String.format("Type: %s, Quantity: %d, StockSymbol: %s, Time: %d%n", this.getType(), this.getQuantity(),
				this.stock == null ? "unknown stock" : stock.getSymbol(), this.getTimstamp());
	}
}
