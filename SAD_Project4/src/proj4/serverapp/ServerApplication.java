package proj4.serverapp;

import java.sql.Connection;

import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Course;
import proj4.common.TeacherAssistant;

public class ServerApplication {

	private StudentEntry se;
	private AdminEntry ae;
	private Connection dbConnection;

	public ServerApplication() {
		se = new StudentEntry(dbConnection);
		ae = new AdminEntry(dbConnection);
	}
	
	public static void init() {
		//TODO:  Need to add db connection setup
		//se = new StudentEntry(dbConnection);
		//ae = new AdminEntry(dbConnection);

	}

	public void fail() {
	}

	public static void main( String[] args) {
		new ServerApplication();
	}

	public int getCourseDemand(String courseID) {
		//TODO:  SQL query to take courseid and get the number of students wanting
		// to take this course
		return 0;
	}

	public Course getCourse(String courseID) {
		//TODO:  SQL query to get course from DB and create object from it
		return null;
	}

	public Student getStudent(String studentID) {
		//TODO:  SQL query to get student info from DB and create object from it
		return null;
	}

	public TeacherAssistant getTA(String taID) {
		//TODO:  SQL query to get TA info from DB and create object from it
		return null;
	}

	public Professor getProf(String profID) {
		//TODO:  SQL query to get Prof info from DB and create object from it
		return null;
	}

	/**
	 * Called by the Web App to determine if a given username/password
	 * combination is valid.
	 * 
	 * @param usrname  	The sanitized username
	 * @param pwd		The sanitized password
	 * @return 			0 - failed login
	 * 					1 - successful admin login
	 * 					2 - successful student login
	 */
	public int validateUser(String usrname, String pwd) {
		//TODO:  SQL query to determine if 1) this is a valid admin login
		// or 2) this is a successful student login
		return 0;
	}

	public Course getCourseByName(String name) {
		//TODO:  SQL query to get course info from DB and return an object
		return null;
	}
	
	/**
	 * Used by the other servlets to get a copy of the right objects to interact
	 * with the rest of the model
	 * 
	 * @return reference to the student entry
	 */
	public StudentEntry getStudentEntry() {
		return se;
	}
	
	/**
	 * Used by the other servlets to get a copy of the right objects to interact
	 * with the rest of the model
	 * 
	 * @return
	 */
	public AdminEntry getAdminEntry() {
		return ae;
	}

}