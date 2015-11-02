package com.spring.aop.service;
import java.util.UUID;
import org.springframework.stereotype.Component;
import com.spring.aop.interfaces.SecretServiceInterface;
import com.spring.aop.model.Secret;

/**
 * @author Izhar
 *
 */
@Component
public class SecretService implements SecretServiceInterface {

	/**
	 Store a new secret using storeSecret
	 @param userid is the user who wants to store the new secret
	 @param secret is the new secret which is to be added to memory map
	 */
	public UUID storeSecret(String userId, Secret secret) {
		
		secret.generateUUID();
		String key = userId+"::"+secret.getId();				
		SecretMap.ownerSecret.put(key,secret);
		return secret.getId();							// return the generated UUID
	}

	/**
	 Reading a secret using readSecret
	 @param userid is the user who wants to read the secret
	 @param secretUUIS is the id of the secret which is to be read
	 */
	public Secret readSecret(String userId, UUID secretId) {
		
		String value = userId+"::"+secretId;					
		if(SecretMap.ownerSecret.containsKey(value))
			return SecretMap.ownerSecret.get(value);
		else
			return SecretMap.sharedSecrets.get(value);		
	}

	/**
	 Sharing of a secret using shareSecret
	 @param userid is the user who wants to share the secret
	 @param UUID the secret key which is to be shared
	 @param targetId is the user id with whom the UUID is shared
	 */
	public void shareSecret(String userId, UUID secretId, String targetUserId) {
		
		String ownerKey = userId+"::"+secretId;				
		String key = targetUserId+"::"+secretId;			
		SecretMap.sharedSecrets.put(key, SecretMap.ownerSecret.get(ownerKey));
	}

	/**
	 Unsharing of a secret using unshareSecret
	 @param userid is the user who wants to unshare the secret
	 @param UUID the secret key which is to be unshared
	 @param targetId is the user id with whom the UUID is unshared
	 */
	public void unshareSecret(String userId, UUID secretId, String targetUserId) {
		
		String key = targetUserId+"::"+secretId;				// get the key
		SecretMap.sharedSecrets.remove(key);			// remove it			
	}
}