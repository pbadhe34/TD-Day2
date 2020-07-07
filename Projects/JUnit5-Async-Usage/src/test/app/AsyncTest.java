package test.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class AsyncTest {
	
	private static boolean ready = false;
	private static final ExecutorService threadpool = Executors.newCachedThreadPool();

    private static int factorialResult;
    
	@Test
	@Timeout(value = 500, unit = TimeUnit.MILLISECONDS)// Poll at most 5 seconds
	void asyncTimeOutTest() throws InterruptedException {
				
		int number = 20;		
		factorialUsingThread(number).start();		
		
	    while (!ready) {
	        Thread.sleep(350); // custom poll interval
	    }
	    // Obtain the asynchronous result and perform assertions
	    System.out.println("The factorial value is "+factorialResult);
	    assertEquals(-2102132736, factorialResult);	    
	}
	
	
	@Test	 
	void testAsyncWithFutureTask() throws InterruptedException, ExecutionException { 
				
		int number = 20;		
	   Future<Long> futureTask = factorialUsingFutureTask(number);
	
	   while (!futureTask.isDone()) { 
           System.out.println("FutureTask is waiting in test..."); 
       } 
	    // Obtain the asynchronous result and perform assertions
	    Long result = futureTask.get();
	    Long expected = 2432902008176640000L;
	    System.out.println("The factorial value with futureTask is "+result);
	    assertEquals(expected, result);	    
	}
	
	@Test	 
	void testAsyncWithCompletableFuture() throws InterruptedException, ExecutionException { 
				
	   int number = 20;		
	   Future<Long> futureTask = factorialUsingCompletableFuture(number);
	   

	    // Obtain the asynchronous result and perform assertions
	
	   Future<Long> completableFuture = factorialUsingCompletableFuture(number);
	   
	   while (!completableFuture.isDone()) { 
           System.out.println("completableFuture is waiting in test..."); 
       } 
      
       
	    Long result = completableFuture.get();
	    
	    Long expected = 2432902008176640000L;
	    System.out.println("The factorial value with completableFuture is "+result);
	    assertEquals(expected, result);	    
	}
	
	
	public static long factorial(int number) {
        long result = 1; 
        for(int i=number;i>0;i--) {
        	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            result *= i; 
        } 
        return result; 
    }
	
	//Finds factorial of a number using Thread

	public static Thread factorialUsingThread(int number) {
        Thread newThread = new Thread(() -> {
        	try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	ready = true;
        	factorialResult = (int) factorial(number);
            System.out.println("Factorial of " + number + " in Thread is: " + factorialResult );
        });

        return newThread;
    }
	
	/**
     * Finds factorial of a number using FutureTask     *  
     */
    
    public static Future<Long> factorialUsingFutureTask(int number) {
        Future<Long> futureTask = threadpool.submit(() -> factorial(number)); 

		/*
		 * while (!futureTask.isDone()) {
		 * System.out.println("FutureTask is not finished yet..."); }
		 */ 

        return futureTask;
    }
    /**
     * Finds factorial of a number using CompletableFuture     *  
     */
     
    public static Future<Long> factorialUsingCompletableFuture(int number) {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        return completableFuture;
    }  
     

    public static void main (String[] args) throws InterruptedException, ExecutionException {
        int number = 20;

        //Thread counting
        factorialUsingThread(number).start();

        //FutureTask Example
        Future<Long> futureTask = factorialUsingFutureTask(number);
        System.out.println("Factorial of " + number + " with futureTask is: " + futureTask.get());

        // CompletableFuture Example
        Future<Long> completableFuture = factorialUsingCompletableFuture(number);
        System.out.println("Factorial of " + number + " with completableFuture is: " + completableFuture.get());

    }


}
