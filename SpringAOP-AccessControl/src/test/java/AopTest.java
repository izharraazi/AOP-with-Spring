

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.UUID;

import com.spring.aop.exceptionHandling.UnauthorizedException;
import com.spring.aop.interfaces.SecretServiceInterface;
import com.spring.aop.model.Secret;
import com.spring.aop.service.SecretMap;
import com.spring.aop.service.SecretService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;




/**
 * Unit test for simple App.
 */
public class AopTest {
	
	@Autowired
	SecretMap secretmap;
	
	ApplicationContext context;
	SecretServiceInterface secretService;
	
	/**
	 * Initialize context
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception{
		
		ApplicationContext	context = new ClassPathXmlApplicationContext("beans.xml");
		secretService = (SecretServiceInterface) context.getBean("secretService");
	}
	
	
    /** TestA
    Bob cannot read Alice’s secret, which has not been shared with Bob. 
    "Unauthorized Exception"
     */
    @Test(expected=UnauthorizedException.class)
	public void testA(){
		System.out.println("----------------------TEST A - Bob cannot read Alice’s secret, which has not been shared with Bob. ");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.readSecret("Bob", secretId);
		System.out.println("-----------------------TEST A Ends----------------------");
	}
    
    /** TestB
     * Alice shares a secret with Bob, and Bob can read it.
     */
    @Test
    public void testB(){
    	System.out.println("----------------------------------------------------------");
    	System.out.println("----------------------TEST B ---------------------------- ");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.readSecret("Bob", secretId);
		System.out.println("-----------------------TEST B Ends----------------------");
	}
	
    /** Test C
     * Alice shares a secret with Bob, and Bob shares Alice’s it with Carl, and Carl can read this secret.
     */
    @Test
	public void testC(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST C");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Bob", secretId, "Carl");
		secretService.readSecret("Carl", secretId);
		System.out.println("-----------------------TEST C Ends----------------------");
	}
    
    /** Test D
     * Alice shares her secret with Bob; Bob shares Carl’s secret with Alice and encounters UnauthorizedException
     */
    @Test(expected=UnauthorizedException.class)
	public void testD(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST D");
		UUID secretId_Alice = secretService.storeSecret("Alice", new Secret());
		UUID secretId_Carl = secretService.storeSecret("Carl", new Secret());
		secretService.shareSecret("Alice", secretId_Alice, "Bob");
		secretService.shareSecret("Bob", secretId_Carl, "Alice");
		System.out.println("-----------------------TEST D Ends----------------------");
	}
    
    /** Test E
     * Alice shares a secret with Bob, Bob shares it with Carl, Alice unshares it with Carl,
     * and Carl cannot read this secret anymore.
     */
    @Test(expected=UnauthorizedException.class)
	public void testE(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST E");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Bob", secretId, "Carl");
		secretService.unshareSecret("Alice", secretId, "Carl");
		secretService.readSecret("Carl", secretId);
		System.out.println("-----------------------TEST E Ends----------------------");
	}
    
    /** Test F
     * Alice shares a secret with Bob and Carl; Carl shares it with Bob, then Alice unshares it with Bob;
     * Bob cannot read this secret anymore.
     */
    @Test(expected=UnauthorizedException.class)
	public void testF(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST F");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Alice", secretId, "Carl");
		secretService.shareSecret("Carl", secretId, "Bob");
		secretService.unshareSecret("Alice", secretId, "Bob");
		secretService.readSecret("Bob", secretId);
		System.out.println("-----------------------TEST F Ends----------------------");
	}
    
    /** Test G
     * Alice shares a secret with Bob; Bob shares it with Carl, and then unshares it with Carl.
     * Carl can still read this secret.
     */
    @Test
	public void testG(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST G");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Bob", secretId, "Carl");
		secretService.unshareSecret("Bob", secretId, "Carl");
		secretService.readSecret("Carl", secretId);
		System.out.println("-----------------------TEST G Ends----------------------");
	}
    
    /** Test H
     * Alice shares a secret with Bob; Carl unshares it with Bob, and encounters UnauthorizedException
     */
    @Test(expected=UnauthorizedException.class)
	public void testH(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST H");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.unshareSecret("Carl", secretId, "Bob");
		System.out.println("-----------------------TEST H Ends----------------------");
	}
    
    /** Test I
     * Alice shares a secret with Bob; Bob shares it with Carl; Alice unshares it with Bob;
     * Bob shares it with Carl with again, and encounters UnauthorizedException
     */
    @Test(expected=UnauthorizedException.class)
	public void testI(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TEST I");
		UUID secretId = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Bob", secretId, "Carl");
		secretService.unshareSecret("Alice", secretId, "Bob");
		secretService.shareSecret("Bob", secretId, "Carl");
		System.out.println("-----------------------TEST I Ends----------------------");
	}
    
    /** Test J
     * Alice stores the same secrete object twice, and get two different UUIDs
     */
    @Test
	public void testJ(){
    	System.out.println("----------------------------------------------------------");
		System.out.println("TESTJ");
		UUID secretId1 = secretService.storeSecret("Alice", new Secret());
		UUID secretId2 = secretService.storeSecret("Alice", new Secret());
		boolean isSame = (secretId1==secretId2);
		Assert.assertEquals(false, isSame);
		System.out.println("-----------------------TEST J Ends----------------------");
	}
}