package com.spring.aop.exceptionHandling;

/**
 * @author Izhar
 UnauthorizedException class is used for throwing customized exceptions
 */
public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String msg = null;
	
    public UnauthorizedException() {
        super();
    }
    
    /**
     * @param cause
     */
    public UnauthorizedException(Throwable cause) {
        super(cause);
    }
    
    /**
     * @param message
     */
    public UnauthorizedException(String message) {
        super(message);
System.out.println(message);
        this.msg = message;
    }
    
    @Override
    public String getMessage() {
        return msg;
    }
    
    @Override
    public String toString() {
        return msg;
    }
}