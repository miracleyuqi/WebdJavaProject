/**
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      StudentDA.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 21, 2023
 * Description:    This project was created for Assignment 4 of WEBD4201
 */

package webd4201.zhouy;

import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.Vector;

import webd4201.zhouy.DatabaseConnect;
import webd4201.zhouy.User;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * StudentDA class
 *
 */
public class StudentDA
{
	// declare variables for student objects 
	static Vector<Student> students = new Vector<Student>();	// contains student references
	static Student aStudent;
	static User aUser;
	
	// declare variables for the database connection
	static Connection aConnection = null;
	static Statement aStatement;

	// declare static variables for all Student instance attribute values
	static long id;
	static String password;
	static String firstName;
	static String lastName;
	static String emailAddress;
	static Date lastAccess;
	static Date enrolDate;
	static boolean enabled;
	static char type;	
	static String programCode;
	static String programDescription;
	static int year;
	
	// declare two constants to store SQL statements for using in create function
    private static final String sqlInsertUser = "INSERT INTO Users " + 
            "(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type)" + 
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String sqlInsertStudent = "INSERT INTO Students " + 
                "(Id, ProgramCode, ProgramDescription, Year)" + 
                "VALUES (?, ?, ?, ?)";
	
	private static final SimpleDateFormat SQL_DF = new SimpleDateFormat("yyyy-MM-dd");
	
	// establish the database connection
	public static void initialize(Connection c)
	{
        try 
        {
            aConnection=c;
            aStatement=aConnection.createStatement();
        }
        catch (SQLException e)
        {
        	System.out.println(e);	
        }
	}

	// close the database connection
	public static void terminate()
	{
        try
        { 	// close the statement
            aStatement.close();
        }
        catch (SQLException e)
        { 
         	System.out.println(e);	
        }
	}

	// The function of retrieving student data using prepareStatement 
	public static Student retrieve(long key) throws NotFoundException, InvalidIdException, InvalidPasswordException, InvalidNameException 
	{ 
		Student aStudent = null;
		String sqlQuery = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type, ProgramCode, ProgramDescription, Year" + 
		                    " FROM Users JOIN Students ON Users.id = Students.id " + 
		                    " WHERE Users.Id = ?";
		  
		try 
		{
			PreparedStatement aStatement = aConnection.prepareStatement(sqlQuery);
			aStatement.setLong(1, key);
			ResultSet rs = aStatement.executeQuery();
			boolean gotIt = rs.next();
			
            if (gotIt)
			{
			    long id = rs.getLong("Id");
			    String password = rs.getString("Password");
			    String hashedPassword = null;
				try 
				{
					hashedPassword = Hashing.hashString(password);
				} 
				catch (NoSuchAlgorithmException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    String firstName = rs.getString("FirstName");
			    String lastName = rs.getString("LastName");
			    String emailAddress = rs.getString("EmailAddress");
			    Date lastAccess = rs.getDate("LastAccess");
			    Date enrolDate = rs.getDate("EnrolDate");
			    char type = rs.getString("Type").charAt(0);
			    boolean enabled = rs.getBoolean("Enabled");
			    String programCode = rs.getString("ProgramCode");
			    String programDescription = rs.getString("ProgramDescription");
			    int year = rs.getInt("Year");
	
			    try 
			    {
			        aStudent = new Student(id, hashedPassword, firstName, lastName, emailAddress, lastAccess, enrolDate, type, enabled, programCode, programDescription, year);
			    } 
			    catch (InvalidUserDataException e) 
			    {
			        e.printStackTrace();
			    }
			} 
			else 
			{
				throw (new NotFoundException("Student with id of " + key +" not found in the database."));
			}
			rs.close();
		} 
		catch (SQLException e) 
		{ 
			System.out.println(e);
		} 		  
		return aStudent;
	}

	
	// Function to create a new student record 
	public static boolean create(Student aStudent) throws DuplicateException, SQLException {
	    boolean inserted = false; //insertion success flag
	    
	    // Prepare statement for both user and student table
	    PreparedStatement userStatement = null;
	    PreparedStatement studentStatement = null;
	    
	    try {
	        // Begin transaction
	        aConnection.setAutoCommit(false);

	        // Insert User record
	        userStatement = aConnection.prepareStatement(sqlInsertUser);
	        userStatement.setLong(1, aUser.getId());
	        userStatement.setString(2, aUser.getPassword());
	        userStatement.setString(3, aUser.getFirstName());
	        userStatement.setString(4, aUser.getLastName());
	        userStatement.setString(5, aUser.getEmailAddress());
	        userStatement.setDate(6, new java.sql.Date(aUser.getLastAccess().getTime()));
	        userStatement.setDate(7, new java.sql.Date(aUser.getEnrolDate().getTime()));
	        userStatement.setBoolean(8, aUser.getEnabled());
	        userStatement.setString(9, Character.toString(aUser.getType()));
	        boolean userInserted = userStatement.execute();
	        
	        // Insert Student record
	        studentStatement = aConnection.prepareStatement(sqlInsertStudent);
	        studentStatement.setLong(1, aStudent.getId());
	        studentStatement.setString(2, aStudent.getProgramCode());
	        studentStatement.setString(3, aStudent.getProgramDescription());
	        studentStatement.setInt(4, aStudent.getYear());
	        boolean studentInserted = studentStatement.execute();

	        // Commit transaction if both insertions succeed
	        if (userInserted && studentInserted) {
	            aConnection.commit();
	            inserted = true;
	        } 
	        else 
	        {
	            throw new SQLException("Failed to insert User and/or Student record");
	        }
	    } 
	    catch (SQLException e) {
	        // Roll back transaction on failure
	        aConnection.rollback();
	        System.out.println(e.getMessage());
	    } 
	    finally {
	        // Close prepared statements and result sets
	        try {
	            if (userStatement != null) {
	                userStatement.close();
	            }
	            if (studentStatement != null) {
	                studentStatement.close();
	            }
	        } 
	        catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        // Set auto-commit mode to true
	        aConnection.setAutoCommit(true);
	    }
	    return inserted;
	}

	
	// Function to delete a student record 
	public static int delete(Student aStudent) throws NotFoundException, InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{	
		int records = 0;
		// retrieve the phone no (key)
		id = aStudent.getId();
		// create the SQL delete statement
		String sqlDeleteStudent  = "DELETE FROM students " + 
										"WHERE id = '" + id + "'";

		// see if this Student already exists in the database
		try
		{
			Student.retrieve(id);  //used to determine if record exists for the passed Student
    		// if found, execute the SQL update statement
    		records = aStatement.executeUpdate(sqlDeleteStudent);
    	}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Student with id " + id 
					+ " cannot be deleted, does not exist.");
		}catch (SQLException e)
			{ System.out.println(e);	}
		return records;
	}

	// Function to update a student record 
	public static int update(Student aStudent) throws NotFoundException, InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{	
		int records = 0;  //records updated in method
		
	    // define the SQL query statement using the id key

	    String sqlUpdateStudent = "UPDATE Students " + "SET ProgramCode=?, ProgramDescription=?, Year=? " +
	        "WHERE Id=?";

	    try (PreparedStatement studentStatement = aConnection.prepareStatement(sqlUpdateStudent)) 
	    {
	        // set the parameter values for the student statement
	        studentStatement.setString(1, aStudent.getProgramCode());
	        studentStatement.setString(2, aStudent.getProgramDescription());
	        studentStatement.setInt(3, aStudent.getYear());
	        studentStatement.setLong(4, aStudent.getId());

	        // execute the update statements
	        records = studentStatement.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println(e);
	    }
		
		return records;
	}
	
	// Function to authenticate the input of a new student information  
	public static Student authenticate(long login, String password) throws NotFoundException, NoSuchAlgorithmException, InvalidIdException, InvalidPasswordException, InvalidNameException {
	    Connection aConnection = DatabaseConnect.initialize();
	    StudentDA.initialize(aConnection);

	    String hashedPassword = Hashing.hashString(password);
	    Student aStudent = null;

	    System.out.println("received login: " + login);
	    System.out.println("received password: " + password);

	    // Retrieve the student object
	    aStudent = StudentDA.retrieve(login);

	    if (aStudent == null) {
	        // Student object not found, return null
	        DatabaseConnect.terminate();
	        return null;
	    }

	    System.out.println("Retrieved student: " + aStudent);
	    System.out.println("Expected login: " + aStudent.getId());
	    System.out.println("Expected password: " + aStudent.getPassword());

	    // Check if the password matches
	    if (login == aStudent.getId() && hashedPassword.equals(aStudent.getPassword())) {
	        System.out.println("Login Successful!");
	        DatabaseConnect.terminate();
	        return aStudent;
	    } else {
	        System.out.println("Login Failed!");
	        System.out.println("Login: " + login + " vs. " + aStudent.getId());
	        System.out.println("Password: " + hashedPassword + " vs. " + aStudent.getPassword());
	        System.out.println("Check: " + (login == aStudent.getId()) + " and " + (hashedPassword.equals(aStudent.getPassword())));
	        DatabaseConnect.terminate();
	        return null;
	    }
	}	
}
