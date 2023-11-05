package webd4201.zhouy;

@SuppressWarnings(value = "serial")

public class InvalidUserDataException extends Exception
{	
	public InvalidUserDataException()
	{
		super();
	}	
	
	public InvalidUserDataException(String message)
	{
		super(message);
	}
	

}