package test.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import app.Repository;
import app.service.Service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

//JUnit5 Extension that initializes mocks and handles 
//strict stubbings
//also supports JUnit Jupiter's method parameter as generics
//Use parameters for initialization of mocks that you use only 
//in that specific test method.

//@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class ServiceMockTest {
    @Mock
    Repository repository;

    @InjectMocks
    Service service;
    
	
	
	/*
	 * @BeforeEach void beforeEach() { 
	 * MockitoAnnotations.initMocks(this); //all mocks initialize
	 *   // repository = Mockito.mock(Repository.class);//single mock init
	 * 
	 * }
	 */ 
    
   
    @Test
    void verifyMockHasInitialied() {
     
    	 assertNotNull(repository);
    	    
    }
    
    
    @Test
    void verifyThatServiceHasInjectedWithMock() {
    	
    	 Repository mockObj=  service.getRepository();
    	 
    	 System.out.println("The mock object injected is "+mockObj.getClass().getName());
    	    
     
    	 assertNotNull(mockObj);
    		    
    }
    
    @Test
    void checkDataWithMocks() throws SQLException {
        // Setup mock scenario
        try {
        	System.out.println("The Repository mock impleennation is  "+repository.getClass().getName());
            Mockito.when(repository.getData()).thenReturn(Arrays.asList("Aali", "Sushant", "Anit", "Bhushan", "KK"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Execute the service that uses the mocked repository
        List<String> data = service.getDataFilteredWithLengthLessThanFive();
     
         //Verify the call was made from caller parent on the mock object
        // Mockito.verify(repository, VerificationMode)
        
       //verify that it was called
      //  Mockito.verify(repository);        
       
        
        //Verify that the call was made from caller parent on the mock object
        //method getData
        
        Mockito.verify(repository).getData();
        
        //Verify that the call wass made alteast once on the 
        //getdata method of mock object
        
       Mockito.verify(repository, Mockito.atLeastOnce()).getData();
        
     //  Mockito.verify(repository, Mockito.times(2)).getData();
        
        
        Mockito.verify(repository, Mockito.atMost(2)).getData();
        Mockito.verify(repository, Mockito.atMostOnce()).getData();
        
        // Validate the response
        Assertions.assertNotNull(data);
        Assertions.assertEquals(3, data.size());
    }

    @Test
    void verifyThatRepositoryThrowsException() {
        // Setup mock scenario
        try {
        	System.out.println("Inside the verifyThatRepositoryThrowsException test");
            Mockito.when(repository.getData()).thenThrow(new SQLException("Connection Exception"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Execute the service that uses the mocked repository
        List<String> stuff = service.getDataFilteredWithLengthLessThanFive();

        // Validate the response
        Assertions.assertNotNull(stuff);
        Assertions.assertEquals(0, stuff.size());
    }
    
	/*
	 * @Mock private List<Integer> sharedList;
	 * 
	 * @Test public void hasLocalMockInThisTest(@Mock List<Integer> localList) {
	 * //localList Mock is initlized at run timr
	 * 
	 * localList.add(100); sharedList.add(100); assertEquals(1, sharedList.size());
	 * assertEquals(1, localList.size()); }
	 * 
	 * //the extension supports JUnit Jupiter's constructor parameters. //This
	 * allows to do setup work in the constructor and set your //fields to final.
	 * 
	 * public ServiceMockTest(@Mock List mockList) { this.sharedList = mockList; }
	 * 
	 * @Test public void verifyMockInitFromConstructor() {
	 * Assertions.assertNotNull(sharedList); }
	 */
}
