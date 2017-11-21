package com.pepsico.demo.order.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pepsico.demo.order.model.CustOrder;
import com.pepsico.demo.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	private List<CustOrder> fallBackOrders = new ArrayList<>(Arrays.asList (new CustOrder ("1","fallback order1", 125.5),
			new CustOrder ("2","fallback order 2", 255.5),
			new CustOrder ("3","fallback order 3", 325.5)));
	
	@RequestMapping(value="/orders",method=RequestMethod.GET,produces="application/json")
	@HystrixCommand(fallbackMethod = "getOrdersFallBackMethod")
	public List<CustOrder> getOrders() {
		
		List<CustOrder> custOrders = orderService.getAllOrders();
		log.info("^^^^ Inside getOrders method ^^^^^");
		//System.out.println("^^^^ Inside getOrders method ^^^^^");
		if (custOrders.size() == 4)
			throw new RuntimeException(); // in order to invoke the fall back method on failure
		return orderService.getAllOrders();
	}
	
	@RequestMapping(value="/orders/{id}",method=RequestMethod.GET,produces="application/json")
	public CustOrder getOrderById(@PathVariable String id) {
		CustOrder co = orderService.getOrderById(id);
		if (co == null)
			throw new RuntimeException("No order found for the requested order id");
		return orderService.getOrderById(id);
	}
	
	@RequestMapping(value="/orders",method=RequestMethod.POST,consumes="application/json")
	public void addOrder(@RequestBody CustOrder order) {
		orderService.addOrder(order);
	}
	
	@RequestMapping(value="/orders/{id}",method=RequestMethod.PUT,produces="application/json")
	public void updateOrder(@RequestBody CustOrder order,@PathVariable String id) {
		orderService.updateOrder(order, id);
	}
	
	@RequestMapping(value="/orders/{id}",method=RequestMethod.DELETE)
	public void deleteOrderById(@PathVariable String id) {
		orderService.deleteOrderById(id);
	}
	
	public List<CustOrder> getOrdersFallBackMethod(){
		
		//System.out.println("^^^^ Inside fallback method ^^^^^");
		log.info("^^^^ Inside fallback method ^^^^^");
		return fallBackOrders;
		
	}
	
}