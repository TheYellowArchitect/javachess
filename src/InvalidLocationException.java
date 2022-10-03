public class InvalidLocationException extends Exception 
{
	public InvalidLocationException(){}
	
	public InvalidLocationException(String errorMessage)
	{
		super(errorMessage);
	}
	
	public InvalidLocationException(Throwable cause)
	{
		super(cause);
	}
	
	public InvalidLocationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
