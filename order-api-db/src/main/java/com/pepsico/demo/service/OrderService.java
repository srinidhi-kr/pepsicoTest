package com.pepsico.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pepsico.demo.order.data.OrderRepository;
import com.pepsico.demo.order.model.CustOrder;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<CustOrder> getAllOrders(){
		List<CustOrder> orders = new ArrayList<>();
		orderRepository.findAll().forEach(orders::add);
		return orders;
		//return orders;
	}
	
	public CustOrder getOrderById(String id) {
		//return orders.stream().filter(o -> o.getId().equals(id)).findFirst().get();
		return orderRepository.findOne(id);
	}

	public void addOrder(CustOrder order) {
		//orders.add(order);
		orderRepository.save(order);
	}

	public void updateOrder(CustOrder order, String id) {
		orderRepository.save(order);
	}

	public void deleteOrderById(String id) {
		//orders.removeIf(o -> o.getId().equals(id));
		orderRepository.delete(id);
	}


}
