package proj4.serverapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proj4.common.Semester;
import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Course;
import proj4.common.TeacherAssistant;

/**
 * @author ubuntu
 * 
 * This is the main part of the server application.  It is assisted by the 
 * Student and AdminEntry classes that handle tasks that are specific
 * to students or Admins.
 */
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
		se = new StudentEntry(this, dbConnection);
		ae = new AdminEntry(this, dbConnection);
	}
	
	public static void main( String[] args) {
		new ServerApplication();
	}

	public int getCourseDemand(String courseID) {
		//TODO:  SQL query to take courseid and get the number of students wanting
		// to take this course
		return 0;
	}

	/**
	 * Queries the database and returns a Student object based on what is currently
	 * in the datbase.
	 * 
	 * @param studentID  the ID or username of the student
	 * @return		Student object corresponding to the entry in the database
	 */
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
						rs.getInt("CreditsCompleted"), rs.getInt("CoursesCompleted"), rs.getInt("NumCoursesDesired"));
				ArrayList<Course> l = new ArrayList<Course>();
				//System.out.println("Setting courses into student:  " + rs.getString("Desired Courses") + " comp: " + rs.getString("Desired Courses").equals(""));
				if(!rs.getString("Desired Courses").equals("")){
					String[] arrCourses = rs.getString("Desired Courses").split(",");
					for(int i = 0; i < arrCourses.length; i++) {
						l.add(this.getCourseByNum(arrCourses[i]));
						//System.out.println("Adding "+arrCourses[i]);
					}
				}
				retVal.setDesiredCourses(l);
				//TODO: Get Recommendation
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Queries the database and returns a TA object based on what is currently
	 * in the datbase.
	 * 
	 * @param studentID  the ID or username of the TA
	 * @return		TeacherAssistant object corresponding to the entry in the database
	 */
	
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
				retVal.setStrComp(rs.getString("TACompetencies"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Queries the database and returns a Professor object based on what is currently
	 * in the database.
	 * 
	 * @param profID  the ID or username of the Professor
	 * @return		Professor object corresponding to the entry in the database
	 */

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
				retVal.setStrComp(rs.getString("ProfCompetencies"));
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

	/**
	 * Queries the database and returns a Course object based on what is 
	 * currently in the database.
	 * 
	 * @param name	CourseID to look for in the database
	 * @return		Course object corresponding to the entry in the database
	 */
	public Course getCourse(String name) {
		Course retVal = null;
		String sel = "SELECT * from CourseData.Course WHERE CourseID = ?";
		try {
			PreparedStatement sqlSel = dbConnection.prepareStatement(sel);
			sqlSel.setString(1, name);
			ResultSet rs = sqlSel.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Course(rs.getString("CourseID"), rs.getString("Description"),
						"", rs.getString("PreRequisite"), null, rs.getInt("CourseLimit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
//	public Course(String i, String n, String d, String p, Semester s) {
//		this.setID(i);
//		this.setNumber(n);
//		this.setDescription(d);
//		this.setPrerequisite(p);
//		this.setSemester(s);
//	}
	
	/**
	 * Same as getCourse only this method looks by course number
	 * 
	 * @param num	This is the course number
	 * @return		Course object that corresponds to the entry in the database
	 */
	public Course getCourseByNum(String num) {
		Course retVal = null;
		String sel = "SELECT * from CourseData.Course WHERE CourseNum = ?";
		try {
			PreparedStatement sqlSel = dbConnection.prepareStatement(sel);
			sqlSel.setString(1, num);
			ResultSet rs = sqlSel.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Course(rs.getString("CourseID"), num, rs.getString("Description"),
						rs.getString("Prerequisite"), null, rs.getInt("CourseLimit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Gets all Course entries in the database and converts them to objects
	 * 
	 * @return	List of all Courses currently in the database
	 */
	public List<Course> getAllCourses() {
		ArrayList<Course> retVal = new ArrayList<Course>();
		String sel = "SELECT CourseNum from CourseData.Course";
		try {
			PreparedStatement sqlSel = dbConnection.prepareStatement(sel);
			ResultSet rs = sqlSel.executeQuery();
			while(rs.next()) {
				retVal.add(this.getCourseByNum(rs.getString("CourseNum")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Gets all TA entries in the database and converts them to objects
	 * 
	 * @return	List of all TAs currently in the database
	 */
	
	public List<TeacherAssistant> getAllTAs() {
		ArrayList<TeacherAssistant> retVal = new ArrayList<TeacherAssistant>();
		String selTA = "SELECT * from CourseData.TA";
		try {
			PreparedStatement sqlSelTA = dbConnection.prepareStatement(selTA);
			ResultSet rs = sqlSelTA.executeQuery();
			while( rs.next() ){
				retVal.add(this.getTA(rs.getString("StaffID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Gets all Professor entries in the database and converts them to objects
	 * 
	 * @return	List of all Professors currently in the database
	 */
	public List<Professor> getAllProfs() {
		ArrayList<Professor> retVal = new ArrayList<Professor>();
		String selProf = "SELECT * from CourseData.Professor";
		try {
			PreparedStatement sqlSelProf = dbConnection.prepareStatement(selProf);
			ResultSet rs = sqlSelProf.executeQuery();
			while( rs.next() ) {
				retVal.add(this.getProf(rs.getString("StaffID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Gets all Student entries in the database and converts them to objects
	 * 
	 * @return	List of all Students currently in the database
	 */
	public List<Student> getAllStudents() {
		ArrayList<Student> retVal = new ArrayList<Student>();
		String selStudent = "SELECT uID from CourseData.Student";
		try {
			PreparedStatement sqlSelStudent = dbConnection.prepareStatement(selStudent);
			ResultSet rs = sqlSelStudent.executeQuery();
			while(rs.next()) {
//				retVal.add(new Student(rs.getString("uID"), rs.getString("name"),
//						rs.getInt("CreditsCompleted"), rs.getInt("CoursesCompleted")));
				retVal.add(this.getStudent(rs.getString("uID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
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
		String insStmt = "UPDATE CourseData.Course SET `CourseLimit` = ? AND `SemesterOffered` = ? WHERE CourseNum = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, c.getEnrollLim());
			insPrepStmt.setString(2, s.getTermDesc());
			insPrepStmt.setString(3, c.getNumber());
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addCourse(Course c, boolean shadow, Semester s){
		
	}
	
	public void removeCourse(Course c, boolean shadow ){
		
		
	}

}
