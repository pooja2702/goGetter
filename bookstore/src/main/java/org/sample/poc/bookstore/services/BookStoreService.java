package org.sample.poc.bookstore.services;

import java.util.List;
import java.util.Map;

import org.sample.poc.bookstore.model.Book;
import org.sample.poc.bookstore.model.Order;

public interface BookStoreService {
	
	List<Book> getBooks(Integer offset, Integer limit, String sorts, boolean all,
			Map<String, String> searchRequest) throws Exception;

	Book getBook(Object bookId) throws Exception;

	List<Order> getOrders(Integer offset, Integer limit, String sorts, boolean all, Map<String, String> searchRequest) throws Exception;
}
