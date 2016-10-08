package org.sample.poc.bookstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sample.poc.bookstore.model.Book;
import org.sample.poc.bookstore.services.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tat.api.core.filter.Embed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/books")
@Api("/books")
public class BookStoreController {
	
	@Autowired
	private BookStoreService service;

	@ApiOperation(httpMethod="GET",value="Returns list of Books",responseContainer="List",notes="Returns list of books")
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Embed
	public List<Book> getAllBooks(
			@RequestParam(value = "offset", defaultValue = "0") @ApiParam(value="Start index for page",defaultValue="0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") @ApiParam(value="No.of items",defaultValue="20") Integer limit,
			@RequestParam(value = "sorts", required = false) @ApiParam(value="List of sort fields") String sorts,
			@RequestParam(value = "fields", required = false) @ApiParam(value="List of fields to be fetched") String fields,
			@RequestParam(value = "all", required = false, defaultValue = "false") @ApiParam(value="Flag to fetch all records",defaultValue="false") boolean all,
			@RequestParam(value = "id", required = false) @ApiParam(value="Id of the book") String id,
			@RequestParam(value="name",required=false) @ApiParam(value="Name of the book") String name,
			@RequestParam(value="author", required=false) @ApiParam(value="Author of the book") String author,
			@RequestParam(value="genre",required=false) @ApiParam(value="Genre") String genre,
			@RequestParam(value="price",required=false) @ApiParam(value="Price of Book") Long price
			) throws Exception {
		//Form the Search request map

		Map<String, String> searchRequest = new HashMap<String, String>();
		if(id != null)
			searchRequest.put("id", id);
		if(name != null)
			searchRequest.put("name", name);
		if(author != null)
			searchRequest.put("author", author);
		if(genre != null)
			searchRequest.put("genre", genre);
		if(price != null)
			searchRequest.put("price", price.toString());
		List<Book> books = service.getBooks(offset, limit, sorts, all, searchRequest);
		return books;
	}
	
	@ApiOperation(httpMethod="GET",value="Returns list of Books",responseContainer="List",notes="Returns list of books")
	@RequestMapping(path="/{bookId}",method = RequestMethod.GET, headers = "Accept=application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Embed
	public Book getBook(
			@PathVariable("bookId") Object bookId,
			@RequestParam(value = "fields", required = false) @ApiParam(value="List of fields to be fetched") String fields
			) throws Exception {
		Book book = service.getBook(bookId);
		return book;
	}

}
