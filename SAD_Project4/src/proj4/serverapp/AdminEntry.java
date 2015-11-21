package proj4.serverapp;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import org.apache.tomcat.jni.Time;

import proj4.common.Course;
import proj4.common.Semester;
import proj4.common.CourseCatalog;

public class AdminEntry {
	public Integer enrollLimit;
	public List<String> semesterCourse;
	public Semester s;
	public List<String> professor;
	public List<String> professorCourses;
	public List<String> taPool;
	public List<String> taCourses;
	public ServerApplication myServerApplication;

	public AdminEntry() {

	}
	
	public AdminEntry( Connection dbconnection) {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		// This is a predictive system, so the current or active semester should be
		// the /next/ semester with respect to the current date.
		if((month == Calendar.AUGUST && day >= 17) || month == Calendar.SEPTEMBER || 
			month == Calendar.OCTOBER || month == Calendar.NOVEMBER || 
			month == Calendar.DECEMBER || (month == Calendar.JANUARY && day <= 11)){
			s.setTerm(String.valueOf(year), Semester.SemesterTerm.SPRING);
		}
		else if((month == Calendar.JANUARY && day > 11) || month == Calendar.FEBRUARY ||
				 month == Calendar.MARCH || month == Calendar.APRIL || 
				 (month == Calendar.MAY && day < 11)){
			s.setTerm(String.valueOf(year), Semester.SemesterTerm.SUMMER);
		}
		else s.setTerm(String.valueOf(year), Semester.SemesterTerm.FALL);
	}
	
	public void getEnrollLimit() {
		
	}
	
	/**
	*
	* @param limit
	 * @param error 
	 * @return 
	 * @return 
	*/
	public String setEnrollLimit( int limit, String course, boolean shadow) {
		String error = null;
		
		return error;
		
	}
	public void getSemesterCourse() {
		
	}
	
	/**
	 * @param shadow 
	 * @param semester 
	*
	*/
	public String addCourse( String desc, boolean shadow, String semester) {
		String error = null;
		return error;
	}
	
	public String removeCourse( String desc, boolean shadow, String semester) {
		String error = null;
		return error;
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
	
	public String setProfessorCourses( String profId, String courses,boolean addOrRemove, 
			boolean astOrPrfcy, boolean shadow) {

		return null;
	}
	
	public void getTAPool() {
		
	}
	
	public String setTACourses(String taId, String courses, boolean addOrRemove, 
			boolean astOrPrfcy, boolean shadow) {
		
		
		return null;
	}
	

	public void addTA(String name) {
		System.out.println("AE: adding TA " + name);
	}
	
	public void updateTA(String taID, Boolean isAvailNextTerm) {
		
	}
	
	public void updateProf(String profID, Boolean isAvailNextTerm) {
		
	}
	
	public String getStudentInfo(String name, boolean prefsOrRecs) {

		return null;
	}

	public String editCourse(String course, boolean equals, String semester) {
		// TODO Auto-generated method stub
		return null;
	}
	
}