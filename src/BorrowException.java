/*
 * Class: IdException 
 * 
 * Description: This is the BorrowException class that receives the error message and stores it, and retrieval.
 * This class handles and throws the custom error message, in response to an exceptions, related to any Borrow Functions.
 * 
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */

import java.io.*;

public class BorrowException extends Exception {

	
	public BorrowException(String message)
	{
		super(message);
	}
	
	public String invalidAdvDay()
	{
		return "Invalid entry, please enter in a numeral!";
	}
	
	
	
}
