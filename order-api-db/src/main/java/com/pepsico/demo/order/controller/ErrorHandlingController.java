package com.pepsico.demo.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pepsico.demo.order.model.ExceptionResponse;

@ControllerAdvice
public class ErrorHandlingController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse>generalException(Exception e) throws Exception{
		
		ExceptionResponse er = new ExceptionResponse();
		ResponseEntity<ExceptionResponse> re = new ResponseEntity<>(er,HttpStatus.OK);
		if (e.getMessage().equals("No order found for the requested order id"))
			er.setCode(404);
		else
			er.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		er.setMessage(e.getMessage());
		return re;
		
	}
}
