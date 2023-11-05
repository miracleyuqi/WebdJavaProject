/**
 * Project Name:   WEBD4201 Assignment 1
 * File Name:      Faculty.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           Jan 17, 2022,
 * Description:    This project was created for Assignment 1 of WEBD4201
 */

package webd4201.zhouy;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Faculty extends User {

	//attributes (properties)
	
	//constants
	public static final String DEFAULT_SCHOOL_CODE = "SET";
	public static final String DEFAULT_SCHOOL_DESCRIPTION = "School of Engineering & Technology";
	public static final String DEFAULT_OFFICE = "H-140";
	public static final int DEFAULT_PHONE_EXTENSION = 1234;
	
	//local attributes
	private String schoolCode;
	private String schoolDescription;
	private String office;
	private int extension;
	
 	//class constants
    private final static int VALID_ID = 9;
	// DA static methods, you DO NOT need to be a Customer object to do these *********************************
	public static void initialize(Connection c)
		{FacultyDA.initialize(c);}
	public static Faculty retrieve(Long key) throws NotFoundException
		{return FacultyDA.retrieve(key);}
	//public static Vector<Faculty> retrieveAll()
		//{return StudentDA.retrieveAll();}
	public static void terminate()
		{FacultyDA.terminate();}
	
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");

	// DA instance methods, you NEED to be a Customer object to do these *********************************
	public void create() throws DuplicateException
		{FacultyDA.create(this);}
	public void delete() throws NotFoundException
		{try {
			FacultyDA.delete(this);
		} catch (InvalidUserDataException | InvalidPasswordException
				| InvalidNameException | InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	public void update() throws NotFoundException
		{try {
			FacultyDA.update(this);
		} catch (InvalidUserDataException | InvalidPasswordException
				| InvalidNameException | InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	
	//setters 
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public void setSchoolDescription(String schoolDescription) {
		this.schoolDescription = schoolDescription;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public void setExtension(int extension) {
		this.extension = extension;
	}
	
	//getters
	public String getSchoolCode() {
		return this.schoolCode;
	}
	public String getSchoolDescription() {
		return this.schoolDescription;
	}
	public String getOffice() {
		return this.office;
	}
	public int getExtension() {
		return this.extension;
	}

	//constructors
	
	//default 
	/**
	 * 
	 * @throws InvalidIdException
	 * @throws InvalidPasswordException
	 * @throws InvalidNameException
	 * @throws InvalidUserDataException
	 */
	public Faculty() throws InvalidIdException, InvalidPasswordException, InvalidNameException, InvalidUserDataException
	{
		super();
		this.setSchoolCode(DEFAULT_SCHOOL_CODE);
		this.setSchoolDescription(DEFAULT_SCHOOL_DESCRIPTION);
		this.setOffice(DEFAULT_OFFICE);
		this.setExtension(DEFAULT_PHONE_EXTENSION);			
	};
	
	//parameterized
	/**
	 * 
	 * @param id
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param lastAccess
	 * @param enrolDate
	 * @param enabled
	 * @param type
	 * @param schoolCode
	 * @param schoolDescription
	 * @param office
	 * @param extension
	 * @throws InvalidIdException
	 * @throws InvalidPasswordException
	 * @throws InvalidNameException
	 * @throws InvalidUserDataException
	 */
	public Faculty(long id, String password, String firstName, String lastName, String emailAddress, 
			Date lastAccess, Date enrolDate, boolean enabled, char type, String schoolCode, 
			String schoolDescription, String office, int extension) 
					throws InvalidIdException, InvalidPasswordException, InvalidNameException, InvalidUserDataException
	{
		super(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, 
				enabled, type);
		this.setId(id);
		setSchoolCode(schoolCode);
		setSchoolDescription(schoolDescription);
		setOffice(office);
		setExtension(extension);
	}
	
	//methods
	//override toString method to create a faculty's information
	public String toString()
	{
		String basic_info = super.toString();
		String faculty_info = basic_info + 
				"\n\t" + getSchoolDescription() + "(" + getSchoolCode() + ")" + 
				"\n\tOffice: " + getOffice() + 
				"\n\t(905)-721-2000 x" + getExtension(); 
		return faculty_info.replace("User", getTypeForDisplay());
	}

	
	@Override
	public String getTypeForDisplay() {
		return "Faculty";
	}  
	
	
}
