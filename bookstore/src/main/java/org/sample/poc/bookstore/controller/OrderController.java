package org.sample.poc.bookstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sample.poc.bookstore.model.Order;
import org.sample.poc.bookstore.services.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tat.api.core.filter.Embed;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/books/orders")
public class OrderController {
	
	@Autowired
	private BookStoreService service;

	@ApiOperation(httpMethod="GET",value="Returns list of Orders",responseContainer="List",notes="Returns list of orders")
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
	@Embed
	public List<Order> getAllOrders(
			@RequestParam(value = "offset", defaultValue = "0") @ApiParam(value="Start index for page",defaultValue="0") Integer offset,
			@RequestParam(value = "limit", defaultValue = "20") @ApiParam(value="No.of items",defaultValue="20") Integer limit,
			@RequestParam(value = "sorts", required = false) @ApiParam(value="List of sort fields") String sorts,
			@RequestParam(value = "fields", required = false) @ApiParam(value="List of fields to be fetched") String fields,
			@RequestParam(value = "all", required = false, defaultValue = "false") @ApiParam(value="Flag to fetch all records",defaultValue="false") boolean all,
			@RequestParam(value = "orderId", required = false) @ApiParam(value="Order Id") String id,
			@RequestParam(value="placedDate",required=false) @ApiParam(value="Date on which order is placed") String date,
			@RequestParam(value="noOfBooks", required=false) @ApiParam(value="Number of books in the order") String noOfBooks
			) throws Exception {
		//Form the Search request map

		Map<String, String> searchRequest = new HashMap<String, String>();
		if(id != null)
			searchRequest.put("id", id);
		if(date != null)
			searchRequest.put("placedDate", date);
		if(noOfBooks != null)
			searchRequest.put("noOfBooks", noOfBooks);
		List<Order> orders = service.getOrders(offset, limit, sorts, all, searchRequest);
		return orders;
	}

}
