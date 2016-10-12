package org.sample.poc.bookstore.model;

import org.tat.api.core.meta.Column;
import org.tat.api.core.meta.Id;
import org.tat.api.core.meta.Table;

@Table(name="books")
public class Book {
	@Column(dbColumn="B_ID")
	@Id
	private String id;
	@Column(dbColumn="B_NAME")
	private String name;
	@Column(dbColumn="B_AUTHOR")
	private String author;
	@Column(dbColumn="B_GENRE")
	private String genre;
	@Column(dbColumn="B_PRICE")
	private Long price;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}

}
