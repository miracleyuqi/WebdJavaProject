/**
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      UserDA.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 21, 2023,
 * Description:    This project was created for Assignment 4 of WEBD4201
 */

package webd4201.zhouy;

import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.Vector;

import webd4201.zhouy.DatabaseConnect;
import webd4201.zhouy.User;
import webd4201_banking.client;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDA
{
	// declare variables for user objects 
	static Vector<User> users = new Vector<User>();	// contains user references
	static User aUser;
	
	// static constants
	private static final String sqlGetAll = "SELECT * FROM users ORDER BY id;";
	
	// declare variables for the database connection
	static Connection aConnection = null;
	static Statement aStatement;

	// declare static variables for all user instance attribute values
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

	// The function of retrieving user data using prepareStatement 
	public static User retrieve(long key) throws NotFoundException 
	{ 
		User aUser = null;
		String sqlQuery = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type" + 
		                    " FROM Users" + 
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
	
			    try 
			    {
			        aUser = new User(id, hashedPassword, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type);
			    } 
			    catch (InvalidUserDataException e) 
			    {
			        e.printStackTrace();
			    }
			} 
			else 
			{
				throw (new NotFoundException("User with id of " + key +" not found in the database."));
			}
			rs.close();
		} 
		catch (SQLException e) 
		{ 
			System.out.println(e);
		} 		  
		return aUser;
	}
	
	public static Vector<User> retrieveAll() throws InvalidUserDataException, InvalidIdException, InvalidNameException {
		Vector<User> users = new Vector<User>();
		
		try {
			PreparedStatement ps = aConnection.prepareStatement(sqlGetAll);
			ResultSet rs = ps.executeQuery();
			boolean isMore = rs.next();
			while (isMore) {
				User c = new User();
				c.setId(rs.getInt("Id"));
				c.setFirstName(rs.getString("firstName"));
				c.setLastName(rs.getString("lastName"));
				c.setEmailAddress(rs.getString("email"));
				c.setLastAccess(rs.getDate("lastLogin"));
				c.setEnrolDate(rs.getDate("enrolDate"));
				c.setEnabled(true);
				c.setType(type);
				users.addElement(c);
				isMore = rs.next();
			}
		}
		catch (SQLException sqle) {}
		
		return users;
	}
	

	// Function to create a new user record 
	public static boolean create(User aUser) throws DuplicateException 
	{	
		boolean inserted = false; //insertion success flag
		// retrieve the User attribute values
		id = aUser.getId();
		//password = aUser.getPassword();
	    password = aUser.getPassword();
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
		firstName = aUser.getFirstName();
		lastName = aUser.getLastName();
		emailAddress = aUser.getEmailAddress();
		lastAccess = aUser.getLastAccess();
		enrolDate = aUser.getEnrolDate();
		enabled = aUser.getEnabled();
		type = aUser.getType();
		
	    // System.out.println("test id: " + id);
	    // create the SQL insert statement using attribute values
	    String sqlInsertUser = "INSERT INTO Users " + 
	                                "(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type)" + 
	                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
		// see if this User already exists in the database
		try
		{
			retrieve(id);
			throw (new DuplicateException("Problem with creating User record, ID " + id +" already exists in the system."));
		}
		// if NotFoundException, add User to database
	    catch(NotFoundException e)
	    {
	        try(PreparedStatement userStatement = aConnection.prepareStatement(sqlInsertUser);)
	            //PreparedStatement studentStatement = aConnection.prepareStatement(sqlInsertStudent))
	        {              	            
	            userStatement.setLong(1, id);
	            userStatement.setString(2, hashedPassword);
	            userStatement.setString(3, firstName);
	            userStatement.setString(4, lastName);
	            userStatement.setString(5, emailAddress);
	            userStatement.setDate(6, new java.sql.Date(lastAccess.getTime()));
	            userStatement.setDate(7, new java.sql.Date(enrolDate.getTime()));
	            userStatement.setBoolean(8, enabled);
	            userStatement.setString(9, Character.toString(type));
	            
	            inserted = userStatement.execute();	            
	        }
	        catch (SQLException ee)
	        { 
	            System.out.println(ee);	
	        }
	    }
		return inserted;
	}
	
	// Function to delete an user record 
	public static int delete(User aUser) throws NotFoundException
	{	
		int records = 0;
		// retrieve the phone no (key)
		id = aUser.getId();
		// create the SQL delete statement
		String sqlDeleteUser  = "DELETE FROM users " + 
										"WHERE id = '" + id + "'";

		// see if this User exists in the database
		try
		{
			Student.retrieve(id);  //used to determine if record exists for the passed User
    		// if found, execute the SQL update statement
    		records = aStatement.executeUpdate(sqlDeleteUser);    		
		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("User with id " + id 
					+ " cannot be deleted, does not exist.");
		}
		catch (SQLException e)
		{ 
			System.out.println(e);	
		}
		return records;
	}

	// Function to update a User record 
	public static int update(User aUser) throws NotFoundException, InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{	
		int records = 0;  //records updated in method
		
	    // define the SQL query statement using the id key
	    String sqlUpdateUser = "UPDATE Users " + "SET FirstName=?, LastName=?, EmailAddress=?, LastAccess=?, "
	    		+ "EnrolDate=?, Type=?, Enabled=? " +
	        "WHERE Id=?";

	    try (PreparedStatement userStatement = aConnection.prepareStatement(sqlUpdateUser))	          
	    {
	        // set the parameter values for the user statement
	        userStatement.setString(1, aUser.getFirstName());
	        userStatement.setString(2, aUser.getLastName());
	        userStatement.setString(3, aUser.getEmailAddress());
	        userStatement.setDate(4, new java.sql.Date(aUser.getLastAccess().getTime()));
	        userStatement.setDate(5, new java.sql.Date(aUser.getEnrolDate().getTime()));
	        userStatement.setString(6, Character.toString(aUser.getType()));
	        userStatement.setBoolean(7, aUser.getEnabled());
	        userStatement.setLong(8, aUser.getId());
	        
	        // execute the update statements
	        records = userStatement.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println(e);
	    }
		
		return records;
	}
	
	public static User authenticate(long login, String password) throws NotFoundException, NoSuchAlgorithmException {
		Connection aConnection = DatabaseConnect.initialize();
		UserDA.initialize(aConnection);
		
		String hashedPassword = Hashing.hashString(password);
		User aUser = null;
	    
	    aUser = UserDA.retrieve(login);
	    
	    System.out.println("Retrieved student: " + aUser);
	    System.out.println("Expected login: " + aUser.getId());
	    System.out.println("Expected password: " + aUser.getPassword());
	    DatabaseConnect.terminate();
	    if (login == aUser.getId() && hashedPassword.equals(aUser.getPassword())) {
	        System.out.println("Login Successful!");
	        return aUser;
	    } else {
	        System.out.println("Login Failed!");
	        System.out.println("Login: " + login + " vs. " + aUser.getId());
	        System.out.println("Password: " + hashedPassword + " vs. " + aUser.getPassword());
	        System.out.println("Check: " + (login == aUser.getId()) + " and " + (hashedPassword.equals(aUser.getPassword())));
	        return null;
	    }
	}
}