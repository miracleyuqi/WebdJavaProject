package webd4201.zhouy;

import java.sql.Connection;

/**
 * Main method to test the new User class created for lab 6
 * 
 * @author Darren Puffer
 * @version 1.0 (2020 Janaury 29)
 * @since 1.0
 */
import java.util.*;

public class Lab2Tester {

	public static void main(String[] args) {
		System.out.println("******************** Lab 2 Output ********************\n");
		Connection c = null;
		Student mainStudent;  //object for a program created Student
		Student dbStudent;   //object for database retrieved Student
		long possibleId = 100222222L;
		GregorianCalendar cal = new GregorianCalendar();
		Date lastAccess = cal.getTime();
		cal.set(2017, Calendar.SEPTEMBER, 3);
		Date enrol = cal.getTime();
		try{
			mainStudent = new Student();
			dbStudent = new Student();
			System.out.println("\nCreate a Student user to insert/delete later in the program, passing:\n\t" +
					"Student student1 = new Student(" + possibleId + "L, \"password\", \"Robert\", \"McReady\"," +
					" \"bob.mcready@dcmail.ca\", enrol, lastAccess, 's', true, \"CPA\", \"Computer Programmer Analyst\", 3);\n"); 
			
			mainStudent = new Student(possibleId, "password", "Robert", "McReady", "bob.mcready@dcmail.ca",
					lastAccess, enrol, 's', true, "CPA", "Computer Programmer Analyst", 3);
			
			System.out.println("mainStudentaaa" + mainStudent);
			//mainStudent.dump();
			try{
				
	            // initialize the database (i.e. create a database connection)
	            c = DatabaseConnect.initialize();
	            Student.initialize(c);
	            	            
	            try // attempt to get a Student that does NOT exist, throws Exception
	            {
	            	System.out.println("\nAttempt to retrieve a student that does not exist (Id: " + possibleId + ")");
	            	dbStudent = Student.retrieve(possibleId);
	            	System.out.println("Student record with id " + possibleId + " retrieved from the database\n");
	            	dbStudent.dump();
	            }
	            catch(NotFoundException e)
	            {	System.out.println(e.getMessage());}
				
	            try // attempt to get a Student that does exist
	            {
	            	possibleId = 100111111L;
	            	System.out.println("\nAttempt to retrieve a student that does exist (Id: " + possibleId + ")");
	            	dbStudent = Student.retrieve(possibleId);
	            	System.out.println("Student record with id " + possibleId + " retrieved from the database\n");
	            	dbStudent.dump();
	            }
	            catch(NotFoundException e)
	            {	System.out.println(e.getMessage());}
	            
	            try
	            {
	            	System.out.println("\nAttempt to insert a new student record for " 
	            						+ mainStudent.getFirstName() + " " + mainStudent.getLastName() 
	            						+ "(Id: " + mainStudent.getId()+")");

	            	mainStudent.create();
	            	mainStudent.dump();
	                System.out.println("Student record added to the database.\n");
	            }
	            catch(DuplicateException e)
	            {	System.out.println(e);}
	            
	            try
	            {
	            	System.out.println("\nChange the student object and attempt to update the student record for " 
	            						+ mainStudent.getFirstName() + " " + mainStudent.getLastName() 
	            						+ "(Id: " + mainStudent.getId() +")");
	            	mainStudent.setPassword("newpassword");
	            	mainStudent.setProgramCode("RPN");
	            	mainStudent.setProgramDescription("Registered Practical Nurse");
	            	
	            	mainStudent.update();
	                System.out.println("Student record updated in the database.\n");
	            }
	            catch(NotFoundException e)
	            {	System.out.println(e);}
	            
	            System.out.println("\nStudents are encouraged to comment out the folowing try...catch block to"
	            		+ " verify the new record exists in pgAdmin by running the \"SELECT * FROM Students;\" command "); 
		            	
	            try // now, attempt to delete the new Student
	            {
	            	System.out.println("\nAttempt to delete the new student record for " 
	            	   						+ mainStudent.getFirstName() + " " + mainStudent.getLastName() 
   						+ "(Id: " + mainStudent.getId() + ")");
	        	   		mainStudent.delete();
	        	   	System.out.println("Student record with id " + mainStudent.getId() + " successfully removed from the database.\n");
	            }
	            catch(NotFoundException e)
	                    {	System.out.println(e);}

	            try // now, try to find the deleted Student
	            {
	            	possibleId = 100222222L;
	                mainStudent = Student.retrieve(possibleId);
	                mainStudent.dump();
	                mainStudent.delete();
	            }
	            catch(NotFoundException e)
	            {
	            	System.out.println("Did not find student record with id " + possibleId + ".\n");
	            }
	            
	            // Retrieve my own student information 
	            try 
	            {
	            	possibleId = 100854558L;
	            	System.out.println("\nMy student information (Id: " + possibleId + ")");
	            	dbStudent = Student.retrieve(possibleId);
	            	System.out.println("Student record with id " + possibleId + " retrieved from the database\n");
	            	dbStudent.dump();
	            }
	            catch(NotFoundException e)
	            {	System.out.println(e.getMessage());}
	            
	            
			 }catch(Exception e){   //catch for database initialize/connect try
				  System.out.println(e.toString());
			 }finally{ // close the database resources, if possible            
		          try{  Student.terminate(); }catch(Exception e){}
		          try{  DatabaseConnect.terminate(); }catch(Exception e){}
			 }
		}catch(InvalidUserDataException iude){
			System.out.println(iude.getMessage());
		}
	}
}

