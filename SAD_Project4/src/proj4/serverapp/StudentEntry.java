package proj4.serverapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.Semester;
import proj4.common.Student;


public class StudentEntry {

  private ServerApplication sa;
  private Connection dbConnection;

  public StudentEntry() {
  }

  public StudentEntry( ServerApplication sa, Connection dbConnection) {
	  this.dbConnection = dbConnection;
	  this.sa = sa;
  }

  public void getUsername(String studentID) {
  }

  public void setUsername(String uid) {
  }

  public void getDesiredCourses(String studentID) {
  }

  /**
   * Updates a Student's preferences in the database.
 * @param studentID		The Student to modify
 * @param prefs			String of numbers that correspond to ordered list of preferences ie. 1,5,13,11
 */
public void setDesiredCourses(String studentID, String prefs) {
		Student retVal = null;
		String insStmt = "UPDATE CourseData.Student SET `Desired Courses` = ? WHERE uID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setString(1, prefs);
			insPrepStmt.setString(2, studentID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
  }
  
  /**
   * Updates a Students desired number of courses for next term
   * 
 * @param studentID		The student to modify
 * @param num			Number of classes this student is wanting to take
 */
public void setNumDesiredCourses(String studentID, int num) {
		Student retVal = null;
		String insStmt = "UPDATE CourseData.Student SET `NumCoursesDesired` = ? WHERE uID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, num);
			insPrepStmt.setString(2, studentID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
  }

  public void getCoursePrefList(String studentID) {
  }

  public void setCoursePrefList(String studentID,  List<Course> c) {

  }

  public void getSemester() {
  }

  public void setSemester( Semester s) {
  }

  public void getCurrentRecommendation(String studentID) {
  }
  
  public List<Course> getAllCourses() {
	  return sa.getAllCourses();
  }
  
  public Student getStudent(String studentID) {
	  return sa.getStudent(studentID);
  }

}