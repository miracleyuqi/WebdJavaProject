package webd4201.zhouy;

import java.sql.*;

import java.util.*;

public class TermTest1Tester {

    public static void main(String[] args) {
        System.out.println("******************** Term Test 1 Output ********************\n");
        Connection c = null;
        Faculty aFaculty;
        GregorianCalendar cal = new GregorianCalendar();
        java.util.Date today = cal.getTime();
        long possibleId = 100987654;
        try {
            System.out.println("\nCreate a Faculty user to insert/delete later in the program.\n");

            aFaculty = new Faculty(possibleId, "password", "Clint", "MacDonald", "clint.macdonald@durhamcollege.ca",
                    today, today, true, 'f', "SEIT", "Science, Eng, and IT", "C115", 2001);
            aFaculty.dump();
       
            c = DatabaseConnect.initialize();
            Faculty.initialize(c);
            try 
            {
                System.out.println("\nAttempt to retrieve a faculty that should does not exist YET (Id: " + possibleId + ")");
                aFaculty = Faculty.retrieve(possibleId);
                System.out.println("Faculty record with id " + possibleId + " retrieved from the database\n");
                aFaculty.dump();
            } catch (NotFoundException e) {
                System.out.println(e);
            }
                           
            try {
                System.out.println("\nAttempt to insert a new faculty record for "
                        + aFaculty.getFirstName() + " " + aFaculty.getLastName()+ "\n");
                aFaculty.create();
                System.out.println("Faculty with id " + possibleId + " was added to the database.\n");
                } catch (DuplicateException e) {
                    System.out.println(e);
                }
 
                try
                {
                    System.out.println("\nAttempt to retrieve a faculty that should now exist (Id: " + possibleId + ")");
                aFaculty = Faculty.retrieve(possibleId);
                System.out.println("Faculty record with id " + possibleId + " retrieved from the database\n");
                aFaculty.dump();
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }

            try {
            	String newOffice = "H223";
            	int newExt = 6666;
                System.out.println("\n" + aFaculty.getFirstName() + " " + aFaculty.getLastName() + " is changing offices and getting a new phone number.\n "
                        + "currently he is in " + aFaculty.getOffice() + " with the extension x" + aFaculty.getExtension() + "\n");
                aFaculty.setOffice(newOffice);
                aFaculty.setExtension(newExt);
                aFaculty.update();
                System.out.println("\n" + aFaculty.getFirstName() + " " + aFaculty.getLastName() + " is now updated in the database.\n");
            } catch (NotFoundException e) {
                System.out.println(e);
            }

            try 
            {
                System.out.println("\nAttempt to delete the new faculty record for "
                        + aFaculty.getFirstName() + " " + aFaculty.getLastName() + "(Id: " + aFaculty.getId() + ")");
                aFaculty.delete();
                System.out.println("Faculty record with id " + aFaculty.getId() + " successfully removed from the database.\n");
            } catch (NotFoundException e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
