package com.greenmart.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.greenmart.app.domain.dtos.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
		log.error("caught exception", ex);
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("unexpected error occured")
				.build();
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ApiErrorResponse> handleIllegalStateException(IllegalStateException ex) {
		ApiErrorResponse error = ApiErrorResponse.builder()
				.status(HttpStatus.CONFLICT.value())
				.message(ex.getMessage())
				.build();
		return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	
	
	
}
