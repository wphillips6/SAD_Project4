package proj4.serverapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import proj4.common.Semester;
import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Course;
import proj4.common.TeacherAssistant;

public class ServerApplication {

	//  Database credentials - This is incredibly insecure.  Don't ever
	// do this on a production application.  http://cwe.mitre.org/data/definitions/259.html
	static final String USER = "root";
	static final String PASS = "root";
	
	private StudentEntry se;
	private AdminEntry ae;
	private Connection dbConnection;

	public ServerApplication() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/CourseData", USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		se = new StudentEntry(dbConnection);
		ae = new AdminEntry(dbConnection);
	}
	
	public static void main( String[] args) {
		new ServerApplication();
	}

	public int getCourseDemand(String courseID) {
		//TODO:  SQL query to take courseid and get the number of students wanting
		// to take this course
		return 0;
	}

	public Student getStudent(String studentID) {
		Student retVal = null;
		String selStudent = "SELECT * from CourseData.Student WHERE uID = ?";
		try {
			PreparedStatement sqlSelStudent = dbConnection.prepareStatement(selStudent);
			sqlSelStudent.setString(1, studentID);
			ResultSet rs = sqlSelStudent.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Student(rs.getString("uID"), rs.getString("name"),
						rs.getInt("CreditsCompleted"), rs.getInt("CoursesCompleted"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public TeacherAssistant getTA(String taID) {
		TeacherAssistant retVal = null;
		String selTA = "SELECT * from CourseData.TA WHERE StaffID = ?";
		try {
			PreparedStatement sqlSelTA = dbConnection.prepareStatement(selTA);
			sqlSelTA.setString(1, taID);
			ResultSet rs = sqlSelTA.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new TeacherAssistant(rs.getString("StaffID"), rs.getString("Name"),
						rs.getInt("AvailNextTerm"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public Professor getProf(String profID) {
		Professor retVal = null;
		String selProf = "SELECT * from CourseData.Professor WHERE StaffID = ?";
		try {
			PreparedStatement sqlSelProf = dbConnection.prepareStatement(selProf);
			sqlSelProf.setString(1, profID);
			ResultSet rs = sqlSelProf.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Professor(rs.getString("StaffID"), rs.getString("Name"),
						rs.getInt("AvailNextTerm"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
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
		System.out.println("Checking credentials in database...");
		String selStudent = "SELECT * from CourseData.Student WHERE uID = ? AND Password = ?";
		String selAdmin = "SELECT * from CourseData.Admin WHERE uID = ? AND Password = ?";
		try {
			PreparedStatement sqlSelStudent = dbConnection.prepareStatement(selStudent);
			PreparedStatement sqlSelAdmin = dbConnection.prepareStatement(selAdmin);
			System.out.println("**Checking " + usrname + " with " + pwd);
			sqlSelStudent.setString(1, usrname);
			sqlSelStudent.setString(2, pwd);
			ResultSet rs = sqlSelStudent.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				return 2;
			}
			//System.out.println("N is " + n);
			sqlSelAdmin.setString(1, usrname);
			sqlSelAdmin.setString(2, pwd);
			rs = sqlSelAdmin.executeQuery();
			rs.last();
			n = rs.getRow();
			if(n==1) {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public Course getCourse(String name) {
		Course retVal = null;
		String sel = "SELECT * from CourseData.Professor WHERE CourseID = ?";
		try {
			PreparedStatement sqlSel = dbConnection.prepareStatement(sel);
			sqlSel.setString(1, name);
			ResultSet rs = sqlSel.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Course(rs.getString("CourseID"), rs.getString("Description"),
						"", rs.getString("PreRequisite"), null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public List<TeacherAssistant> getAllTAs() {
		//TODO
		return null;
	}
	
	public List<Professor> getAllProfs() {
		//TODO
		return null;
	}
	
	public List<Student> getAllStudents() {
		//TODO
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
	 * @return reference to the student entry
	 */
	public AdminEntry getAdminEntry() {
		return ae;
	}

	public void updateCourse(Course c, boolean shadow, Semester s) {
		// TODO Auto-generated method stub
		
	}

}