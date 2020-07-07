package test.com.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Month;
import java.util.EnumSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.data.OddEvenNumbers;
import com.data.StringVarifier;

public class ParameterizedTesting {
	
	  private int num ;

	  @BeforeEach 
	  void initNum()
	  {
		  num = 11;
	  }
	   
	  @Test
	  void verifyOddNumber() {
		  boolean res = OddEvenNumbers.isOdd(this.num);
		  assertTrue(res);
	  }
	  //Customize the odd number test as parameterized testing with more no of parameters as arguments
	  //and resuing the test case 
	  //Source of arguments, an int array, 
	  //Access the arguments as int values  : ValueSource.ints()
	  
	  @ParameterizedTest
	  @ValueSource(ints = {1, 3, 5, -4, 15, Integer.MAX_VALUE}) // six numbers are passed to the test as integer  to verify
	  @DisplayName("Odd Number Test")
	  void shouldReturnTrueForOddNumbers(int numValue) {
		  System.out.println("The number to be tested is "+numValue);
		  boolean res = OddEvenNumbers.isOdd(numValue);
		  assertTrue(res);
	  } 
		/*
		 * value sources only support the types:
		 * 
		 * short (with the 'shorts' attribute) 
		 * byte (with the 'bytes' attribute) 
		 * int (with the 'ints' attribute) 
		 * long (with the 'longs' attribute) 
		 * float (with the 'floats'  attribute) 
		 * double (with the 'doubles' attribute) 
		 * char (with the 'chars' attribute) 
		 * java.lang.String (with the 'strings' attribute) 
		 * java.lang.Class  (with the 'classes' attribute)
		 */
	  
	  //only one argument is to be passed to the test method each time.
	  //can't pass null through a @ValueSource, even for String and Class types
	  
	  
	  //Check that true for null for blank strings. 
	  @ParameterizedTest
	  @ValueSource(strings = {"", "  ","abcd"})
	  @DisplayName("Test Null or Blank String")
	  void verifyBlankOrNullStringValue(String input) {
		  System.out.println("The length of string to be tested is "+input.length());
	      assertTrue(StringVarifier.isBlank(input));
	  }
	  
	      //pass a single null value to a parameterized test method using @NullSource:
		   //Since primitive data types donot accept null values, can't use the @NullSource for primitive arguments.
	  
		  @ParameterizedTest
		  @NullSource  //no values;;only single null value
		  void verifyNullOrBlankString(String input) {
			  System.out.println("Verifying the value of string to be tested is "+input);			 
		      assertTrue(StringVarifier.isBlank(input));
		     }
	 
		  //pass empty values using the @EmptySource annotation:
		  
		  @ParameterizedTest
		  @EmptySource  //passes a single empty argument to the annotated method.
		  void verifyEmptyString(String input) {
			  System.out.println("Verifying Empty String "+input);
		      assertTrue(StringVarifier.isBlank(input));
		  }
		  //can also pass empty values for Collection types and arrays.
		  
		  //In order to pass both null and empty values, use the compose @NullAndEmptySource annotation:
		  @ParameterizedTest
		  @NullAndEmptySource
		  void verifyNullAndEmptyStrings(String input) {
			  System.out.println("Verifying Null and Empty String "+input);
		      assertTrue(StringVarifier.isBlank(input));
		  }
		  
		 //As with the @EmptySource, the composed annotation works for Strings, Collections, and arrays as well.
		 //To pass a few more empty string variations to the parameterized test, can combine @ValueSource, @NullSource, and @EmptySource together:
		  
		  @ParameterizedTest
		  @NullAndEmptySource
		  @ValueSource(strings = {"  ", "\t", "\n"})
		  void veriforAllTypesOfBlankStrings(String input) {
			  System.out.println("Verifying for @NullAndEmptySource with "+input);
		   
		      assertTrue(StringVarifier.isBlank(input));
		  }
		  
		  //to run a test with different values from an enumeration, we  use the @EnumSource annotation.
		  //Now assert that all month numbers are between 1 and 12:
		  
		  //The Month is an enum representing the 12 months of the year -
		  //January, February, March, April, May, June, July, August, September, October, November and December with 
		  //month value as serial number wise value. 
		  
		  @ParameterizedTest
		  @EnumSource(Month.class) // passing all 12 months
		  void verifyThatValueForAMonthIsAlwaysBetweenOneAndTwelve(Month monthValue) {
			  System.out.println("Verifying for @EnumSource with "+monthValue);
		      int monthNumber = monthValue.getValue();
		      assertTrue(monthNumber >= 1 && monthNumber <= 12);
		   }
		  
		  //filter out a few months by using the names attribute.
		  
		  @ParameterizedTest
		  @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER","JULY"})
		  void verifyMonthsThatAre30DaysLong(Month monthvalue) {
			  System.out.println("Verifying the Month @EnumSource with "+monthvalue);
		      final boolean isALeapYear = false;
		      assertEquals(30, monthvalue.length(isALeapYear));
		  }
		  
		  //filter out a few months by using the names attribute.
		  //assert that the April, September, June, and November are 30 days long:

		   
		  @ParameterizedTest
		  @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER","JULY"})
		  void verifyThatSomeMonthsAre30DaysLong(Month month) {
			  System.out.println("Verifying the Month with "+month);
		      final boolean isALeapYear = false;
		      assertEquals(30, month.length(isALeapYear));
		  }
		  
		  //By default, the names will only keep the matched enum values. 
		  //turn this around by setting the mode attribute to EXCLUDE:
		  @ParameterizedTest
		  @EnumSource(
		    value = Month.class,
		    names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
		    mode = EnumSource.Mode.EXCLUDE)
		  void verifyThatExceptFourMonths_OthersAre31DaysLong(Month month) {
			  System.out.println("Verifying the Month length for "+month);
		      final boolean isALeapYear = false;
		      assertEquals(31, month.length(isALeapYear));
		  }
		  
		  //The @ValueSource, @EnumSource is only applicable when we're going to pass just one argument per test execution.
		  
		     //pass a regular expression to the names attribute:
			   
			  @ParameterizedTest
			  @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)  //MATCH_ANY, MATCH_ALL, INCLUDE, EXCLIUDE
			  void verifyThatThe_FourMonths_AreEndingWithBer(Month month) {
				  System.out.println("Verifying the Month with REGEx for "+month);
			      EnumSet<Month> months =
			        EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
			      assertTrue(months.contains(month));
			  }
			  
				/*
				 * To compare input and output both as parameters A parameterized test where:
				 * 1.Pass an input value and an expected value to the test method 
				 * 2. Compute the actual result with those input values
				 *  3. Assert the actual value with the expected value
				 */
			  //need argument sources capable of passing multiple arguments. 
			  //The @CsvSource is one of those sources:
			  //The @CsvSource accepts an array of comma-separated values and each array entry corresponds
			  //to a line in a CSV file.
			  
			  @ParameterizedTest
			  @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
			  void compareInputandUppercaseOutputValues(String input, String expected) {
				  System.out.println("Verifying the input and output with  "+input  +" and "+expected);
			      String actualValue = input.toUpperCase();
			      assertEquals(expected, actualValue);
			  }
				/*
				 * This source takes one array entry each time, splits it by comma and passes
				 * each array to the annotated test method as separate parameters. By default,
				 * the comma is the column separator but we can customize it using the delimiter
				 * attribute:
				 */
				  
			  
			  //It is now a colon-separated value, still a CSV!
			  
			  @ParameterizedTest
			  @CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
			  void verifyTheLowercaseValues(String input, String expected) {
			      String actualValue = input.toLowerCase();
			      assertEquals(expected, actualValue);
			  }
			   
			  //load the CSV file and ignore the header column with @CsvFileSource:

				  //The resources attribute represents the CSV file resources on the classpath to read. 
			     //And, we can pass multiple files to it.
			     //The numLinesToSkip attribute represents the number of lines to skip when reading the CSV files.
			    //By default, @CsvFileSource does not skip any lines, but this feature is usually useful for skipping 
			    //the header lines.
			  
			  //The numLinesToSkip attribute represents the number of lines to skip when reading the CSV files.
			  //By default, @CsvFileSource does not skip any lines, but this feature is usually useful for skipping \
			  //the header lines.
			  
			  //Wuth @CsvSource, the delimiter is customizable with the delimiter attribute.

			 // In addition to the column separator:

			  // line separator can be customized using the lineSeparator attribute – a newline is the default value
			  // The file encoding is customizable using the encoding attribute – UTF-8 is the default value

				/*
				 * //Customized test display name
				 * 
				 * @ParameterizedTest(name = "{index} {0} is the input data with output as {1}")
				 * 
				 * For customizing the display name of test:

                     {index} will be replaced with the invocation index – The invocation index for the first execution is 1, for the second is 2, and so on
                     {arguments} is a placeholder for the complete, comma-separated list of arguments
                     The {0}, {1}, ... are placeholders for individual arguments
				 */ 
			  
			  //The @CsvFileSource is an ArgumentsSource which is used to load comma-separated value (CSV) 
			  //files from one or more classpath resources.
			 
			  
				  @ParameterizedTest
				  @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)				 
				  void verifyDataFromCSVFile(String input, String expected) {
					  System.out.println("Verifying the input and output from CSV file as "+input +" and "+expected);
				      String actualValue = input.toUpperCase();
				      assertEquals(expected, actualValue);
				  }

		 }
