package org.sample.poc.bookstore.model;


import java.sql.Timestamp;

import org.tat.api.core.meta.Field;
import org.tat.api.core.meta.Id;
import org.tat.api.core.meta.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect
@Table(name="ORDERS")
public class Order {
	
	@Field(dbColumn = "O_ID")
	@Id
	private String orderId;
	
	@Field(dbColumn="O_PLACED_DATE")
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Timestamp placedDate;
	
	@Field(dbColumn="O_BOOK_COUNT")
	private Integer noOfBooks;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Timestamp getPlacedDate() {
		return placedDate;
	}
	public void setPlacedDate(Timestamp placedDate) {
		this.placedDate = placedDate;
	}
	public Integer getNoOfBooks() {
		return noOfBooks;
	}
	public void setNoOfBooks(Integer noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

}
