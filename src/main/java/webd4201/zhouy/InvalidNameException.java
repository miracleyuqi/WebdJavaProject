package webd4201.zhouy;

@SuppressWarnings(value = "serial")

public class InvalidNameException extends Exception
{	
	public InvalidNameException(String message)
	{
		super(message);
	}
	
	public InvalidNameException()
	{
		super();
	}	
}
