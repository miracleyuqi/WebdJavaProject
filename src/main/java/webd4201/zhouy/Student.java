/**
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      Student.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 24, 2023
 * Description:    This project was created for Assignment 4 of WEBD4201
 */

package webd4201.zhouy;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Student extends User {
	//attributes (properties)
	
	//constants
	public static final String DEFAULT_PROGRAM_CODE = "UNDC";
	public static final String DEFAULT_PROGRAM_DESCRIPTION = "Undeclared";
	public static final int DEFAULT_YEAR = 1;

	//local attributes
	private String programCode;
	private String programDescription;
	private int year;
	Vector<String> mark= new Vector<>();
	private long id;
	private String password;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Date lastAccess;
	private Date enrolDate;
	private boolean enabled;
	private char type;
	
 	//class constants
    private final static int VALID_ID = 9;
	// DA static methods
	public static void initialize(Connection c)
		{StudentDA.initialize(c);}
	public static Student retrieve(Long key) throws NotFoundException
		{try {
			return StudentDA.retrieve(key);
		} catch (NotFoundException | InvalidIdException | InvalidPasswordException | InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;}
	//public static Vector<Student> retrieveAll()
		//{return StudentDA.retrieveAll();}
	public static void terminate()
		{StudentDA.terminate();}
	
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	// DA instance methods, you NEED to be a Student object to do these *********************************
	public void create() throws DuplicateException
		{try {
			StudentDA.create(this);
		} catch (DuplicateException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	public void delete() throws NotFoundException
		{try {
			StudentDA.delete(this);
		} catch (InvalidUserDataException | InvalidPasswordException
				| InvalidNameException | InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	public void update() throws NotFoundException
		{try {
			StudentDA.update(this);
		} catch (InvalidUserDataException | InvalidPasswordException
				| InvalidNameException | InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	public static Student authenticate(long login, String password) throws NotFoundException, NoSuchAlgorithmException 
	{
		try {
			return StudentDA.authenticate(login, password);
		} catch (NoSuchAlgorithmException | NotFoundException | InvalidIdException | InvalidPasswordException
				| InvalidNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//setters
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setMark(Vector<String> mark) {
		this.mark = mark;
	}
	
	//getters
	public String getProgramCode() {
		return this.programCode;
	}
	public String getProgramDescription() {
		return this.programDescription;
	}
	public int getYear() {
		return this.year;
	}
	public Vector<String> getMark() {
		return this.mark;
	}
		
	//constructors
	//parameterized
	
	/**
	 * @param id
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param lastAccess
	 * @param enrolDate
	 * @param enabled
	 * @param type
	 * @param programCode
	 * @param programDescription
	 * @param year
	 * @param vector
	 * @throws InvalidIdException
	 * @throws InvalidPasswordException
	 * @throws InvalidNameException
	 * @throws InvalidUserDataException
	 */
	public Student(long id, String password, String firstName, String lastName,
			String emailAddress, Date lastAccess, Date enrolDate,
			boolean enabled, char type, String programCode,
			String programDescription, int year)//, Vector<Mark> vector) 
					throws InvalidIdException, InvalidPasswordException, InvalidNameException, InvalidUserDataException {
		super(id, password, firstName, lastName, emailAddress, lastAccess,
				enrolDate, enabled, type);
		this.setId(id);
		setProgramCode(programCode);
		setProgramDescription(programDescription);
		setYear(year);
		//setMark(mark);
	}
	
	
	//overloaded constructor
	/**
	 * @param id
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param lastAccess
	 * @param enrolDate
	 * @param enabled
	 * @param type
	 * @param programCode
	 * @param programDescription
	 * @param year 
	 * @throws InvalidIdException
	 * @throws InvalidPasswordException
	 * @throws InvalidNameException
	 * @throws InvalidUserDataException
	 */
	public Student(long id, String password, String firstName, String lastName,
			String emailAddress, Date lastAccess, Date enrolDate,
			char type, boolean enabled, String programCode,
			String programDescription, int year) 
					throws InvalidUserDataException, InvalidIdException, InvalidPasswordException, InvalidNameException 
	{
		this(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate,  
				enabled, type, programCode, programDescription, year); //new Vector<Mark>());
	}

	//default constructor
	public Student() throws InvalidUserDataException, InvalidIdException, InvalidPasswordException, InvalidNameException 
	{
		this(DEFAULT_ID, DEFAULT_PASSWORD, DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, 
				DEFAULT_EMAIL_ADDRESS, new Date(), new Date(), DEFAULT_TYPE, DEFAULT_ENABLED_STATUS, 
				DEFAULT_PROGRAM_CODE, 
				DEFAULT_PROGRAM_DESCRIPTION, DEFAULT_YEAR); 
	}
	
	//methods
	//override toString method to create a student's information according to their year	
	public String toString()
	{
		if (year == 1)
		{
			return "Student Info for: " + 
					"\n\t" + firstName + " " + lastName + " (" + id + ")" +
					"\n\tCurrently in " + year + "st year of " + programDescription + " (" + programCode + ")" + 
					"\n\tat Durham College" + 
					"\n\tEnrolled: " + SQL_DF.format(enrolDate); 
		}
		else if (year == 2)
		{
			return "Student Info for: " + 
					"\n\t" + firstName + " " + lastName + " (" + id + ")" +
					"\n\tCurrently in " + year + "nd year of " + programDescription + " (" + programCode + ")" + 
					"\n\tat Durham College" + 
					"\n\tEnrolled: " + SQL_DF.format(enrolDate); 
		}
		else if (year == 3)
		{
			return "Student Info for: " + 
					"\n\t" + firstName + " " + lastName + " (" + id + ")" +
					"\n\tCurrently in " + year + "rd year of " + programDescription + " (" + programCode + ")" + 
					"\n\tat Durham College" + 
					"\n\tEnrolled: " + SQL_DF.format(enrolDate); 
		}
		else
		{
			return "Student Info for: " + 
					"\n\t" + firstName + " " + lastName + " (" + id + ")" +
					"\n\tCurrently in " + year + "th year of " + programDescription + " (" + programCode + ")" + 
					"\n\tat Durham College" + 
					"\n\tEnrolled: " + SQL_DF.format(enrolDate); 
		}
	}	
}
