# AOP-with-Spring
This application covers the AOP paradigm using Spring .
It covers the different features of AspectOriented Programing such as
Logging Advice Transactions Join point . 
The problem description is stated below:
 build a proof-of-concept secret storing and sharing service. One can
create/read secretes, and share/unshare them with other users as well.
The service is a proof of concept in that we do not intend to turn it
into a fully featured system; instead, we leave most of the service
implementation as either a dummy or a placeholder with an empty method,
as we only want to concentrate on the access control and logging aspects
with AOP.
 
 public interface SecretService {
	/**
 	* Store a secrete in the service. A new “secret” record is already
created, identified by randomly generated UUID, with the current user as
the owner of the secret. 
 	* @param userId the ID of the current user
 	* @param secret the secret to be stored. No duplication or null check
is performed.
 	* @return always return a new UUID for the given secret
 	*/
	UUID storeSecret(String userId, Secret secret);

	/**
 	* Read a secret by ID
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being requested
 	* @return the requested secret  
 	*/
	Secret readSecret(String userId, UUID secretId);
    
	/**
 	* Share a secret with another user. The secret may not have been
created by the current user.
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being shared
 	* @param targetUserId the ID of the user to share the secret with
 	*/
	void shareSecret(String userId, UUID secretId, String targetUserId);
    
	/**
 	* Unshare the current user's own secret with another user.
 	* @param userId the ID of the current user
 	* @param secretId the ID of the secret being unshared
 	* @param targetUserId the ID of the user to unshare the secret with
 	*/
	void unshareSecret(String userId, UUID secretId, String targetUserId);
}

Suppose the existing implementation of the service forgot to enforce
access control; i.e., the shareSecret method does not check whether the
current user owns or is shared with the secret in the first place, and
readSecret does not check that either. Your task is to use AOP to
enforce the following access control, add logging, implement the
storeSecret method and any other method that you deem necessary to
satisfy the following requirements: 
One can share the secrets he owns with anybody.
One can only read secrets that are shared with him, or his own secrets.
In any other case, an UnauthorizedException is thrown.
If Alice shares her secret with Bob, Bob can further share ir with Carl. 
One can only unshare his own secret. When unsharing a secret with
someone that the secret is not shared by any means, the operation is
silently ignored. When one attempts to unshare a secret he neither owns
or is shared with, an UnauthorizedException is thrown. When attempts to
unshare a secret he is shared with but does not own, the request is
silently ignored.
Both sharing and unsharing of Alice’s secret with Alice herself have no
effect; i.e., Alice always has access to her own secret.
Log a message before each invocation of the read, share, and unshare
methods and after the store method, with messages exactly as the
following, one message per line, except you need to replace the user IDs
with the right ones. All logs go to the console through System.out.
Alice creates a secrete with ID x
Alice reads the secret of ID x
Alice shares the secret of ID x with Carl
Alice unshares the secret of ID x with Carl
Please note that our access control here assumes that authentication is
already taken care of elsewhere, i.e., it’s outside the scope of the
project to make sure only Alice can call readSecret with userId as
“Alice”.

