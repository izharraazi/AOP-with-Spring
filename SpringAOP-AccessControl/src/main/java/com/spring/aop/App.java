package com.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * @author Izhar
 *
 */
public class App {

	public static void main(String[] args) {
		// Loading Configuration from Beans.xml
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
	   // Get the HelloGreeter bean by id
	}

}
