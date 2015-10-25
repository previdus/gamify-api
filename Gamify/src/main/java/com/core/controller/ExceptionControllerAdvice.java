package com.core.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



public class ExceptionControllerAdvice {
	
	 @ExceptionHandler({IOException.class, Exception.class})
	    public ModelAndView handleIOException(Exception ex) {
	        ModelAndView model = new ModelAndView("account/genericError");
	 
	        model.addObject("exception", ex.getMessage());
	        
	         
	        return model;
	    }

}
