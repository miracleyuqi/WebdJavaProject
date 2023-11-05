package webd4201.zhouy;

@SuppressWarnings(value = "serial")

public class InvalidIdException extends Exception
{	
	public InvalidIdException(String message)
	{
		super(message);
	}
	
	public InvalidIdException()
	{
		super();
	}	
}
