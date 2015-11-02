package com.spring.aop.aspect;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.spring.aop.exceptionHandling.UnauthorizedException;
import com.spring.aop.model.Secret;
import com.spring.aop.service.SecretMap;

/**
 * @author Izhar
 LoggingAspect to validate and provide access control 
 */
@Component
@Aspect
public class LoggingAspect {

	
	/**
	 Poincut for reading a secret
	 */
	@Pointcut("execution(* com.spring.aop.service.SecretService.readSecret(..))")
	public void readSecret() {}
	
	/**
	 Pointcut for storing a secret
	 */
	@Pointcut("execution(* com.spring.aop.service.SecretService.storeSecret(..))")
	public void storeSecret() {}
	
	

	/**
	 Pointcut for sharing a secret
	 */
	@Pointcut("execution(* com.spring.aop.service.SecretService.shareSecret(..))")
	public void shareSecret() {}
	
	/**
	 Pointcut for unsharing a secret
	 */
	@Pointcut("execution(* com.spring.aop.service.SecretService.unshareSecret(..))")
	public void unshareSecret() {}

	
	/**
	 @Before Advice for validation for secret 
	 * @param joiningPoint
	 * @throws UnauthorizedException
	 */
	@Before("storeSecret()")
	public void storeSecretBeforeAdvice(JoinPoint joiningPoint) throws UnauthorizedException{
		Object args[] = joiningPoint.getArgs();

		try{
			
			String userId = (String) args[0];			// get the userId
			Secret secret = (Secret) args[1];			// get the secret
			if(userId==null || userId=="" || secret==null){
				System.out.println("LoggingAspect.storeSecretBeforeAdvice() User Id or Secret is null");
				throw new NullPointerException();
			}
				
			}
		
		catch(NullPointerException e){
			throw new UnauthorizedException("UserId and Secret cannot be null"); 
		}
	}

	
	/**
	 @Before Advice for  validating and authorizing of sharing the secret with other users
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Before("shareSecret()")
	public void shareSecretAdvice(JoinPoint joinPoint) throws UnauthorizedException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];						
			UUID secretId = (UUID) args[1];							
			String targetUserId = (String) args[2];					
			
			if(userId==null || userId=="" || secretId==null || targetUserId==null || targetUserId=="")
				throw new NullPointerException();
			System.out.println(userId+" shares the secret of ID "+secretId+" with "+targetUserId);
			
			String value = userId+"::"+secretId;							
			
			if(!SecretMap.ownerSecret.containsKey(value) && !SecretMap.sharedSecrets.containsKey(value))
				throw new UnauthorizedException("Unauthorized exception- Insufficient Priveleges");
		}
	
		catch(NullPointerException e){
			throw new UnauthorizedException("userId or secretId or targetUserId is null or invalid"); 
		}
	}

	/**
	 @Around Advice for validation of unsharing secrets
	 * @param proceedingJoinPoint
	 * @throws Throwable 
	 */
	@Around("unshareSecret()")
	public void unshareSecretAdvice(ProceedingJoinPoint proceeding) throws Throwable{
		Object args[] = proceeding.getArgs();
		try{
			String value;
			String userId = (String) args[0];						// get the userId
			UUID secretId = (UUID) args[1];							// get the secretId
			String targetUserId = (String) args[2];					// get the targetUserId
			
			if(userId==null || "".equals(userId) || secretId==null || targetUserId==null || "".equals(targetUserId))
				throw new NullPointerException();
			System.out.println(userId+" unshares the secret with  "+targetUserId+" with Id:: "+secretId);
			 value = userId+"::"+secretId;						
		
			if(SecretMap.ownerSecret.containsKey(value))
				proceeding.proceed();										
			else if(!SecretMap.sharedSecrets.containsKey(value))
				throw new Exception();
		}	
		catch(NullPointerException e){
			throw new UnauthorizedException("Invalid userId or secretId or targetUserId or null vlues."); 
		}
		catch (Exception e) {
			throw new UnauthorizedException("Unauthorized Exception-Insufficient Priveleges");
		}
	}	
	
	/**
	 Advice which will run after sucessfull storage of secret
	 * @param joinPoint
	 * @param secret
	 * @throws UnauthorizedException
	 */
	@AfterReturning(pointcut="storeSecret()",returning = "secret")
	public void storeSecretAfterReturningAdvice(JoinPoint joinPoint, Object secret) throws UnauthorizedException{
		
		Object args[] = joinPoint.getArgs();
		
		String userId = (String) args[0];			// get the userId
		UUID secretId = (UUID) secret;				// get the secretId
		System.out.println(userId+" creates a secret with ID:: "+secretId);		
	}
	
	/**
	 @Before Advice validating and authorizing of reading the secret 
	 * @param joinPoint
	 * @throws UnauthorizedException
	 */
	@Before("readSecret()")
	public void readSecretAdvice(JoinPoint joinPoint) throws UnauthorizedException{
		Object args[] = joinPoint.getArgs();

		try{
			String userId = (String) args[0];					// get the userId
			UUID secretId = (UUID) args[1];						// get the secretId
			
			if(userId==null || userId=="" || secretId==null)	throw new NullPointerException();
			System.out.println(userId+" reads the secret of ID "+secretId);
			
			String value = userId+"::"+secretId;							
			if(!SecretMap.ownerSecret.containsKey(value) && !SecretMap.sharedSecrets.containsKey(value))
				throw new UnauthorizedException("Unauthorized exception- Insufficient Privelages");
		}
		
		catch(NullPointerException e){
			throw new UnauthorizedException("UserId or Secret Invalid");  
		}
	}

}