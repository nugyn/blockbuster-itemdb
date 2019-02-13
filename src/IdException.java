/*
 * Class: IdException 
 * 
 * Description: This is the IdException class that receives the error message and stores it, and retrieval.
 * This class handles and throws the custom error message, in response to an exceptions, related to the Id.
 * Author: Duy Linh Nguyen - s3429599
 * 
 * 
 */



import java.io.*;

@SuppressWarnings("serial")
public class IdException extends Exception
{

	public IdException (String message)
	{
		super(message);
	}
	
	public String getErrorMessage()
	{
		return "Please re-enter ID";
	}
	
	
}
