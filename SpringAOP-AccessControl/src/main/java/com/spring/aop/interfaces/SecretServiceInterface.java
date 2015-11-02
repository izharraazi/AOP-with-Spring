/**
 * 
 */
package com.spring.aop.interfaces;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.spring.aop.model.Secret;

/**
 * @author Izhar
 *
 */
@Service
public interface SecretServiceInterface {

	/**
 	* Store a secrete in the service. A new “secret” record is already created, identified by randomly generated UUID, with the current user as the owner of the secret. 
 	* @param userId the ID of the current user
 	* @param secret the secret to be stored. No duplication or null check is performed.
 	* @return always return a new UUID for the given secret
 	*/
	public UUID storeSecret(String userId, Secret secret);

	/**
 	* Read a secret by ID
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being requested
 	* @return the requested secret  
 	*/
	public Secret readSecret(String userId, UUID secretId);
    
	/**
 	* Share a secret with another user. The secret may not have been created by the current user.
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being shared
 	* @param targetUserId the ID of the user to share the secret with
 	*/
	public void shareSecret(String userId, UUID secretId, String targetUserId);
    
	/**
 	* Unshare the current user's own secret with another user.
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being unshared
 	* @param targetUserId the ID of the user to unshare the secret with
 	*/
	public void unshareSecret(String userId, UUID secretId, String targetUserId);
	
	
}
