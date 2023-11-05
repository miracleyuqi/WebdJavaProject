/**
 * Project Name:   WEBD4201 Term Test 1
 * File Name:      FacultyDA.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           Feb 15, 2023 
 * Description:    This project was created for term test 1 of WEBD4201
 */

package webd4201.zhouy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.sql.*;

public class FacultyDA 
{
	// declare variables for faculty objects 
	static Vector<Faculty> faculty = new Vector<Faculty>();	// contains student references
	static Student aStudent;
	
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
	static String schoolCode;
	static String schoolDescription;
	static String office;
	static int extension;
	
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

	// The function of retrieving faculty data using prepareStatement 
	public static Faculty retrieve(long key) throws NotFoundException 
	{ 
		Faculty aFaculty = null;
		String sqlQuery = "SELECT Users.Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type, SchoolCode, SchoolDescription, Office, Extension" + 
		                    " FROM Users, Faculty " + 
		                    " WHERE Users.id = Faculty.id AND Users.Id = ?";
		  
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
			    String firstName = rs.getString("FirstName");
			    String lastName = rs.getString("LastName");
			    String emailAddress = rs.getString("EmailAddress");
			    Date lastAccess = rs.getDate("LastAccess");
			    Date enrolDate = rs.getDate("EnrolDate");
			    char type = rs.getString("Type").charAt(0);
			    boolean enabled = rs.getBoolean("Enabled");
			    String schoolCode = rs.getString("schoolCode");
			    String schoolDescription = rs.getString("schoolDescription");
			    String office = rs.getString("Office");
			    int extension = rs.getInt("Extension");
	
			    try 
			    {
			        try {
						aFaculty = new Faculty(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, enabled, type, schoolCode, schoolDescription, office, extension);
					} catch (InvalidIdException | InvalidPasswordException
							| InvalidNameException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    } 
			    catch (InvalidUserDataException e) 
			    {
			        e.printStackTrace();
			    }
			} 
			else 
			{
				throw (new NotFoundException("Faculty with id of " + key +" not found in the database."));
			}
			rs.close();
		} 
		catch (SQLException e) 
		{ 
			System.out.println(e);
		}
		  
		return aFaculty;
	}


	// Function to create a new faculty record 

	public static boolean create(Faculty aFaculty) throws DuplicateException 
	{	
		boolean inserted = false; //insertion success flag
		// retrieve the Faculty attribute values
		id = aFaculty.getId();
		password = aFaculty.getPassword();
		firstName = aFaculty.getFirstName();
		lastName = aFaculty.getLastName();
		emailAddress = aFaculty.getEmailAddress();
		lastAccess = aFaculty.getLastAccess();
		enrolDate = aFaculty.getEnrolDate();
		enabled = aFaculty.getEnabled();
		type = aFaculty.getType();
		schoolCode = aFaculty.getSchoolCode();
		schoolDescription = aFaculty.getSchoolDescription();
		office = aFaculty.getOffice();
		extension = aFaculty.getExtension();
		
		// create the SQL insert statement using attribute values
		String sqlInsertUser = "INSERT INTO Users " + 
									"(Id, Password, FirstName, LastName, EmailAddress, LastAccess, EnrolDate, Enabled, Type)" + 
									"VALUES ('" + id + "', '" + password + "', '" + firstName + "', '" + lastName + 
									"', '" + emailAddress + "', '" + lastAccess + "', '" + enrolDate + 
									"', '" + enabled + "', '" + type + "')";

		String sqlInsertFaculty = "INSERT INTO Faculty " + 
									"(Id, SchoolCode, SchoolDescription, Office, Extension)" + 
									"VALUES ('" + id + "', '" + schoolCode + "', '" + schoolDescription + "', '" + office + "', '" + extension + "')";
		

		// see if this faculty already exists in the database
		try
		{
			retrieve(id);
			throw (new DuplicateException("Problem with creating faculty record, ID " + id +" already exists in the system."));
		}
		// if NotFoundException, add faculty to database
		catch(NotFoundException e)
		{
			try
 			{  // execute the SQL update statement
	    		inserted = aStatement.execute(sqlInsertUser);
	    		aStatement.execute(sqlInsertFaculty);
			}
			catch (SQLException ee)
				{ System.out.println(ee);	}
		}
		return inserted;
	}
	
/*
	private static String encode(Object digest, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object digest(String password2, String string) {
		// TODO Auto-generated method stub
		return null;
	}
*/
	
	// Function to delete a Faculty record 
	public static int delete(Faculty aFaculty) throws NotFoundException, InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{	
		int records = 0;
		// retrieve the phone no (key)
		id = aFaculty.getId();
		// create the SQL delete statement
		String sqlDeleteFaculty  = "DELETE FROM faculty " + 
										" WHERE id = '" + id + "'";
		String sqlDeleteUser  = "DELETE FROM users " + 
										" WHERE Id = '" + id + "'";

		// see if this Faculty already exists in the database
		try
		{
			Faculty.retrieve(id);  //used to determine if record exists for the passed Faculty
    		// if found, execute the SQL update statement
    		records = aStatement.executeUpdate(sqlDeleteFaculty);
    		aStatement.executeUpdate(sqlDeleteUser);
		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Faculty with id " + id 
					+ " cannot be deleted, does not exist.");
		}catch (SQLException e)
			{ System.out.println(e);	}
		return records;
	}

	// Function to update a faculty record 
	public static int update(Faculty aFaculty) throws NotFoundException, InvalidUserDataException, InvalidPasswordException, InvalidNameException, InvalidIdException
	{	
		int records = 0;  //records updated in method
		
		// retrieve the Student argument attribute values
		id = aFaculty.getId();
		password = aFaculty.getPassword(); 
		firstName = aFaculty.getFirstName();
		lastName = aFaculty.getLastName();
		emailAddress = aFaculty.getEmailAddress();
		lastAccess = aFaculty.getLastAccess();
		enrolDate = aFaculty.getEnrolDate();
		enabled = aFaculty.getEnabled();
		type = aFaculty.getType();
		schoolCode = aFaculty.getSchoolCode();
		schoolDescription = aFaculty.getSchoolDescription();
		office = aFaculty.getOffice();
		extension = aFaculty.getExtension();

		// define the SQL query statement using the id key
		String sqlUpdateUser = "UPDATE Users " + 
                "SET FirstName= '" + aFaculty.getFirstName() + "', " + 
                "LastName= '" + aFaculty.getLastName() + "', " + 
                "EmailAddress= '" + aFaculty.getEmailAddress() + "', " + 
                "LastAccess= '" + aFaculty.getLastAccess() + "', " + 
                "EnrolDate= '" + aFaculty.getEnrolDate() + "', " + 
                "Type= '" + aFaculty.getType() + "', " + 
                "Enabled= '" + aFaculty.getEnabled() + "'" +
                " WHERE Id = '" + aFaculty.getId() + "'";

		String sqlUpdateFaculty = "UPDATE Faculty " + 
                   "SET SchoolCode= '" + aFaculty.getSchoolCode() + "', " + 
                   "SchoolDescription= '" + aFaculty.getSchoolDescription() + "', " + 
                   "Office= '" + aFaculty.getOffice() + "'" +
                   "Extension= '" + aFaculty.getExtension() + "'" +
                   " WHERE Id = '" + aFaculty.getId() + "'";

		// see if this faculty exists in the database
		// NotFoundException is thrown by find method
		try
		{
			Faculty.retrieve(id);  //determine if there is a Student record to be updated
                    // if found, execute the SQL update statement
            records = aStatement.executeUpdate(sqlUpdateFaculty);
            aStatement.executeUpdate(sqlUpdateUser);
		}
		catch(NotFoundException e)
		{
			throw new NotFoundException("Faculty with id " + id 
					+ " cannot be updated, does not exist in the system.");
		}
		catch (SQLException e)
		{ System.out.println(e);}
		return records;
	}
}
