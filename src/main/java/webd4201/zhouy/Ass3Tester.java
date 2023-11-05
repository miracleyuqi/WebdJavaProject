package webd4201.zhouy;

import java.sql.Connection;

public class Ass3Tester {

	public static void main(String[] args) {
		
		Connection c = null;
		c = DatabaseConnect.initialize();
        Student.initialize(c);
        
        System.out.println("AUTHENTICATION TESTS \n");
        
		// test student authenticator
        System.out.println("*******************************************");
        System.out.println("Should be Successful - Clint");
        testAuth(100000001, "mypassword");
        System.out.println("*******************************************");
        System.out.println("Should Fail - Wrong PW");
        testAuth(100000001, "mypassword2");
        System.out.println("*******************************************");
        System.out.println("Should Fail - User not found");
        testAuth(100000000, "mypassword");
        System.out.println("*******************************************");
        System.out.println("Should be Successul - Bob");
        testAuth(100000002, "mypassword");
        System.out.println("*******************************************");
	}
	
	public static void testAuth (long id, String pw) {
		
		
		try {
			Student stud = Student.authenticate(id, pw);
			System.out.println("Authentication Successful");
			System.out.println(stud.getFirstName() + " " + stud.getLastName());
		}
		catch (NotFoundException nfe) { System.out.println("Authentication Failed: " + nfe.getMessage()); }
			
	}

}
