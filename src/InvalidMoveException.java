public class InvalidMoveException extends Exception 
{
	
	public InvalidMoveException(){}
	
	public InvalidMoveException(String errorMessage)
	{
		super(errorMessage);
	}
	
	public InvalidMoveException(Throwable cause)
	{
		super(cause);
	}
	
	public InvalidMoveException(String message, Throwable cause)
	{
		super(message, cause);
	}
}