package proj4.serverapp;

import java.sql.Connection;
import java.util.List;
import proj4.common.Course;
import proj4.common.Professor;
import proj4.common.Student;
import proj4.common.TeacherAssistant;



public class AdminEntry {

	public Integer enrollLimit;
	public List<String> semesterCourse;
	public String semester;
	public List<String> professor;
	public List<String> professorCourses;
	public List<String> taPool;
	public List<String> taCourses;
	public ServerApplication myServerApplication;

	public AdminEntry() {
	}

	public AdminEntry( Connection dbconnection) {
	}

	public void getEnrollLimit() {
	}

	/**
	 * 
	 * @param limit
	 */
	public void setEnrollLimit( int limit, String course) {
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

	public void updateTA(String taID, Boolean isAvailNextTerm) {
	}

	public void updateProf(String profID, Boolean isAvailNextTerm) {
	}

	public void addStudent(String name) {
		System.out.println("AE: adding student " + name);
	}
	
}