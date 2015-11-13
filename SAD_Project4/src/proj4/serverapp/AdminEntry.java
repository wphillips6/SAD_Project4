package proj4.serverapp;

import java.util.List;

import proj4.common.Course;



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

	public AdminEntry( String dbconnection, ServerApplication sa) {
	}

	public void getEnrollLimit() {
	}

	/**
	 * 
	 * @param limit
	 */
	public void setEnrollLimit( int limit) {
	}

	public void getSemesterCourse() {
	}

	/**
	 * 
	 */
	public void addCourse( String desc) {
	}

	public String getSemester() {
		return "";
	}

	public void setSemester( String s) {
	}

	public void getProfessorById(String id) {
	}

	public void addProfessor( String profName) {
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
	}

	public void addProf(String name) {
	}

	public void updateTA(String taID, Boolean isAvailNextTerm) {
	}

	public void updateProf(String profID, Boolean isAvailNextTerm) {
	}

	public void addStudent(String name) {
	}

}