package com.spring.aop.service;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.spring.aop.model.*;


/**
 * @author Izhar
 */
@Service
public class SecretMap {
	
	public static HashMap<String, Secret> ownerSecret;
	public static HashMap<String, Secret> sharedSecrets;

	/**
	 Constructor initializing new hashmaps 
	 */
	public SecretMap(){
		ownerSecret = new HashMap<String, Secret>();
		sharedSecrets = new HashMap<String, Secret>();
	}
	
}