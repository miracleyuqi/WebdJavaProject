package webd4201.zhouy;

@SuppressWarnings(value = "serial")

public class InvalidPasswordException extends Exception
{	
	public InvalidPasswordException(String message)
	{
		super(message);
	}
	
	public InvalidPasswordException()
	{
		super();
	}	
}
