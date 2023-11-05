/**
 * Project Name:   WEBD4201 Assignment 1
 * File Name:      Mark.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           Jan 19, 2022,
 * Description:    This project was created for Assignment 1 of WEBD4201
 */

package webd4201.zhouy;

import java.text.DecimalFormat;
import java.util.Date;

public class Mark {
	//attributes (properties)
	
	//constants
	public static final float MINIMUM_GPA = (float) 0.0;
	public static final float MAXIMUM_GPA = (float) 5.0;
	public static final DecimalFormat GPA = new DecimalFormat("0.##");
	
	//local attributes
	private String courseCode;
	private String courseName;
	private int result; 
	private float gpaWeighting;
	
	//setters
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public void setGpaWeighting(float gpaWeighting) {
		this.gpaWeighting = gpaWeighting;
	}
	
	//getters
	public String getCourseCode() {
		return this.courseCode;
	}
	public String getCourseName() {
		return this.courseName;
	}
	public int getResult() {
		return this.result;
	}
	public float getGpaWeighting() {
		return this.gpaWeighting;
	}

	//parameterized constructor 
	/**
	 * 
	 * @param courseCode
	 * @param courseName
	 * @param result
	 * @param gpaWeighting
	 */
	public Mark(String courseCode, String courseName, int result, float gpaWeighting)
	{
		setCourseCode(courseCode);
		setCourseName(courseName);
		setResult(result);
		setGpaWeighting(gpaWeighting);
	}
	
	//override toString method to create mark's information
	public String toString()
	{
		return getCourseCode() +"    " + String.format("%-35s", getCourseName()) + "    " + 
				getResult() + GPA.format(getGpaWeighting()); 
	}
	
}
