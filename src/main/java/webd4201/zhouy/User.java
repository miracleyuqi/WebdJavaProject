/**
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      User.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 23, 2023
 * Description:    This project was created for Assignment 4 of WEBD4201
 */

package webd4201.zhouy;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class User implements CollegeInterface {
	
	//attributes (properties)
	
	//constants
	public static final long DEFAULT_ID = 100123456; 
	public static final String DEFAULT_PASSWORD = "password"; 
	public static final byte MINIMUM_PASSWORD_LENGTH = 8;
	public static final byte MAXIMUM_PASSWORD_LENGTH = 40;
	public static final String DEFAULT_FIRST_NAME = "John";
	public static final String DEFAULT_LAST_NAME = "Doe";
	public static final String DEFAULT_EMAIL_ADDRESS = "john.doe@dcmail.com";
	public static final boolean DEFAULT_ENABLED_STATUS = true; 
	public static final char DEFAULT_TYPE = 's'; 
	public static final byte ID_NUMBER_LENGTH = 9;
	public static final DateFormat DF = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CANADA);
			
	//properties
	private long id;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Date lastAccess;
	private Date enrolDate;
	private boolean enabled;
	private char type; 
	
	// DA static methods
	public static void initialize(Connection c)
		{UserDA.initialize(c);}
	public static void terminate()
		{UserDA.terminate();}	
	public static User retrieve(Long key) throws NotFoundException
		{return UserDA.retrieve(key);}

	// DA instance methods
	public void create() throws DuplicateException
		{UserDA.create(this);}
	public void delete() throws NotFoundException
		{UserDA.delete(this);} 
	public void update() throws NotFoundException
		{try {
			UserDA.update(this);
		} catch (NotFoundException | InvalidUserDataException | InvalidPasswordException | InvalidNameException
				| InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}} 

	public static User authenticate(long login, String password) throws NotFoundException, NoSuchAlgorithmException 
	{
		return UserDA.authenticate(login, password);
	}
	
	//setters
	public void setId(long id) throws InvalidIdException {
		//If the length of id is not 9 digits, throw an error message
		if (String.valueOf(id).length() != 9)
		{
			throw new InvalidIdException("The length of id must be 9 digits.");
		}		
		else
		{
			this.id = id;
		}
	}

	public void setPassword(String password) throws InvalidPasswordException {
		//If the length of password is out of range, throw an error message
		if (String.valueOf(password).length() < MINIMUM_PASSWORD_LENGTH || 
				String.valueOf(password).length() > MAXIMUM_PASSWORD_LENGTH)
		{
			throw new InvalidPasswordException("The length of password must be between 8 and 40 digits.");
		}		
		else
		{
			this.password = password;
		}
	}
	
	//This part I borrowed a method I learned from this site: 
	//https://www.delftstack.com/howto/java/check-if-string-contains-numbers-in-java/
	//to check if there is number(s) in the name attribute
	public void setFirstName(String firstName) throws InvalidNameException {
		//If the first name is null or contains numbers, throw an error message
		if (!verifyName(firstName))
		{
			throw new InvalidNameException("First name cannot be null and may only include letters.");
		}		
		else
		{
			this.firstName = firstName;
		}
	}
	
	public void setLastName(String lastName) throws InvalidNameException {
		//If the last name is null or contains numbers, throw an error message
		if (!verifyName(lastName))
		{
			throw new InvalidNameException("Last name cannot be null and may only include letters.");
		}		
		else
		{
			this.lastName = lastName;
		}

	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	public void setEnrolDate(Date enrolDate) {
		this.enrolDate = enrolDate;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void setType(char type) {
		this.type = type;
	}

	//getters
	public long getId() {
		return this.id;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public Date getEnrolDate() {
		return this.enrolDate;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public char getType() {
		return type;
	}

	// constructors
	
	// parameterized constructor 
	/**
	 * @overloads
	 * @returns a parameterized constructed instance of the user class
	 * @param id
	 * @param title
	 * @param type
	 * @param length
	 * @param genre
	 * @param views
	 * @param releaseDate
	 * @param enabled
	 * @throws InvalidIdException 
	 * @throws InvalidPasswordException 
	 * @throws InvalidNameException 
	 * @throws InvalidUserDataException
	 */
	public User(long id, String password, String firstName, String lastName, String emailAddress, 
			Date lastAccess, Date enrolDate, boolean enabled, char type) 
					throws InvalidUserDataException 
	{
		try
		{
			setId(id);
			setPassword(password);
			setFirstName(firstName);
			setLastName(lastName);
			setEmailAddress(emailAddress);
			setLastAccess(lastAccess);
			setEnrolDate(enrolDate); 
			setEnabled(enabled);
			setType(type);
		}
		
		//catch the original error from setId and throw a new exception
		catch(InvalidIdException iie)
		{
			throw new InvalidUserDataException(iie.getMessage());
		}
		
		//catch the original error from setPassword and throw a new exception
		catch(InvalidPasswordException ipe)
		{
			throw new InvalidUserDataException(ipe.getMessage());
		}
		
		//catch the original error from setFirstName or setLastName and throw a new exception
		catch(InvalidNameException ine)
		{
			throw new InvalidUserDataException(ine.getMessage());
		}

	}
	
	// default constructor
	/**
	 * @throws InvalidIdException 
	 * @returns an unparameterized constructed instance of the user class
	 */
	public User() throws InvalidUserDataException
	{
		this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL_ADDRESS, 
				new Date(), new Date(), DEFAULT_ENABLED_STATUS, DEFAULT_TYPE);
		
		try
		{
			setId(id);
			setPassword(password);
			setFirstName(firstName);
			setLastName(lastName);
			setEmailAddress(emailAddress);
			setLastAccess(lastAccess);
			setEnrolDate(enrolDate); 
			setEnabled(enabled);
			setType(type);
		}
		
		//catch the original error from setId and throw a new exception
		catch(InvalidIdException iie)
		{
			throw new InvalidUserDataException(iie.getMessage());
		}
		
		//catch the original error from setPassword and throw a new exception
		catch(InvalidPasswordException ipe)
		{
			throw new InvalidUserDataException(ipe.getMessage());
		}
		
		//catch the original error from setFirstName or setLastName and throw a new exception
		catch(InvalidNameException ine)
		{
			throw new InvalidUserDataException(ine.getMessage());
		}
	};
	
	//methods
	
	//Overloads toString() method to create a user's information
	public String toString()
	{
		return "User Info for: " + id +
				"\n\tName: " + firstName + " " + lastName + " (" + emailAddress + ")" +
				"\n\tCreated on: " + DF.format(enrolDate) +
				"\n\tLast access: " + DF.format(lastAccess); 
	}

	//Displays the returned string from the toString() method
	public void dump()
	{
		System.out.println(toString());
	}

	//return the boolean value of ID
	public static boolean verifyId(long id)
	{
		if(String.valueOf(id).length() == ID_NUMBER_LENGTH)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean verifyName(String name)
	{
		if (name == "" || name.matches(".*[0-9].*"))
		{
			return false;
		}		
		else
		{
			return true;
		}
	}
	
	@Override
	public String getTypeForDisplay() {
		return "";
	}  	
}
