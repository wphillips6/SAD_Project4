package proj4.serverapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import proj4.common.Course;
import proj4.common.Professor;
import proj4.common.Student;
import proj4.common.TeacherAssistant;



public class AdminEntry {

	private Integer enrollLimit;
	private List<String> semesterCourse;
	private String semester;
	private List<String> professor;
	private List<String> professorCourses;
	private List<String> taPool;
	private List<String> taCourses;
	private ServerApplication sa;
	private Connection dbConnection;

	public AdminEntry() {
	}

	public AdminEntry( ServerApplication sa, Connection dbConnection) {
		  this.dbConnection = dbConnection;
		  this.sa = sa;
	}

	public void getEnrollLimit() {
	}

	/**
	 * 
	 * @param limit
	 */
	public void setEnrollLimit( int limit, String course) {
		String insStmt = "UPDATE CourseData.Course SET `CourseLimit` = ? WHERE CourseNum = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, limit);
			insPrepStmt.setString(2, course);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getSemesterCourse() {
	}

	/**
	 * 
	 */
	public void addCourse( String desc) {
		System.out.println("AE: adding course " + desc);
	}

	public String getSemester() {
		return "";
	}

	public void setSemester( String s) {
	}

	public void getProfessorById(String id) {
	}

	public void addProfessor( String profName) {
		System.out.println("AE: adding professor " + profName);
	}

	public List<Course> getProfessorCourses(String profId) {
		return null;
	}

	public void setProfessorCourses( String profId, List<String> courses) {

	}

	public void getTAPool() {
	}

	public List<Course> getTACourses(String taId) {
		return null;
	}

	public void invokeShadowMode() {
	}

	public void addTA(String name) {
		System.out.println("AE: adding TA " + name);
	}

	public void updateTAAvailable(String taID, int isAvailNextTerm) {
		String insStmt = "UPDATE CourseData.TA SET `AvailNextTerm` = ? WHERE StaffID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, isAvailNextTerm);
			insPrepStmt.setString(2, taID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProfAvailable(String profID, int isAvailNextTerm) {
		String insStmt = "UPDATE CourseData.Professor SET `AvailNextTerm` = ? WHERE StaffID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, isAvailNextTerm);
			insPrepStmt.setString(2, profID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTACompetencies(String taID, String comp) {
		String insStmt = "UPDATE CourseData.Professor SET `TACompetencies` = ? WHERE StaffID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setString(1, comp);
			insPrepStmt.setString(2, taID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateProfCompetencies(String profID, String comp) {
		String insStmt = "UPDATE CourseData.Professor SET `ProfCompetencies` = ? WHERE StaffID = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setString(1, comp);
			insPrepStmt.setString(2, profID);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addStudent(String name) {
		System.out.println("AE: adding student " + name);
	}
	
}