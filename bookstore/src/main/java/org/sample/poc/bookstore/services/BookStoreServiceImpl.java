package org.sample.poc.bookstore.services;

import java.util.List;
import java.util.Map;

import org.sample.poc.bookstore.model.Book;
import org.sample.poc.bookstore.model.Order;
import org.sample.poc.bookstore.repos.DefaultRepository;
import org.springframework.stereotype.Service;
import org.tat.api.core.meta.DataAccess;

@Service("BookStoreService")
public class BookStoreServiceImpl implements BookStoreService{
	
	@DataAccess(entity=Book.class)
	private DefaultRepository<Book> bookDefaultRepository;
	
	@DataAccess(entity=Order.class)
	private DefaultRepository<Order> orderDefaultRepository;

	public List<Book> getBooks(Integer offset, Integer limit, String sorts, boolean all,
			Map<String, String> searchRequest) throws Exception {
		return bookDefaultRepository.findAll(offset, limit, sorts, all, searchRequest);
	}

	@Override
	public Book getBook(Object lookupKey) throws Exception {
		return bookDefaultRepository.findOne(lookupKey);
	}

	@Override
	public List<Order> getOrders(Integer offset, Integer limit, String sorts, boolean all,
			Map<String, String> searchRequest) throws Exception {
		return orderDefaultRepository.findAll(offset, limit, sorts, all, searchRequest);
	}

}
