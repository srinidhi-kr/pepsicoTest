package com.pepsi.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class OrderController {
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ResponseEntity<String> getAllOrders(){
	
		ServiceInstance serviceInstance = loadBalancerClient.choose("order-api-db");
		
		String baseUrl = serviceInstance.getUri().toString();
		
		baseUrl = baseUrl+"/orders";
		log.info("Base url choosen is --- {}", baseUrl);
		//System.out.println("Base url choosen is ---" + baseUrl);
		
		RestTemplate r = new RestTemplate();
		ResponseEntity<String> response = r.getForEntity(baseUrl, String.class);
		return response;
		}
	
	@RequestMapping(value="/orders",method=RequestMethod.GET)
	public ResponseEntity<String> order(){
		
		ResponseEntity<String> response = null;
	
		//List<ServiceInstance> instances = discoveryClient.getInstances("order-api-db");
		for (int i=0; i<50; i++) { //to simulate opening of circuit
			ServiceInstance serviceInstance = loadBalancerClient.choose("order-api-db");
			
			//ServiceInstance serviceInstance = instances.get(0);
			//ServiceInstance serviceInstance1 = instances.get(1);
			//System.out.println("-- First Base URL is -- "+ serviceInstance.getUri().toString());
			//System.out.println("-- Second Base URL is -- "+ serviceInstance1.getUri().toString());
		
			String baseUrl = serviceInstance.getUri().toString();
		
			baseUrl = baseUrl+"/orders";
			//System.out.println("Base url choosen is ---" + baseUrl);
			log.info("Base url choosen is --- {}", baseUrl);
		
			RestTemplate r = new RestTemplate();
			response = r.getForEntity(baseUrl, String.class);
		}
		return response;
		}
}
