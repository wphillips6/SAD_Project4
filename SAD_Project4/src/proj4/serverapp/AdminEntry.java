package proj4.serverapp;
import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import proj4.common.Course;
import proj4.common.Professor;
import proj4.common.Student;
import proj4.common.TeacherAssistant;
import proj4.common.Semester;
import org.apache.tomcat.jni.Time;


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
			s = new Semester(String.valueOf(year) + "SPR", Semester.SemesterTerm.SPRING);
		}
		else if((month == Calendar.JANUARY && day > 11) || month == Calendar.FEBRUARY ||
				 month == Calendar.MARCH || month == Calendar.APRIL || 
				 (month == Calendar.MAY && day < 11)){
			s = new Semester(String.valueOf(year) + "SUM", Semester.SemesterTerm.SUMMER);
		}
		else s = new Semester(String.valueOf(year) + "FAL", Semester.SemesterTerm.FALL);
	}
	
	
	/**
	* Method setEnrollLimit sets the enrollment limit to the value entered
	*                       by the administrator for the course indicated
	*                       in the next semester. an error is returned if
	*                       the course specified does not exist in the 
	*                       requested model, or if an invalid limit is
	*                       specified 
	* @param limit  integer number corresponding to the desired maximum
	*               capacity of the course for the active semester  
	* @param course string descriptor of a course in the system
	* @param shadow should the limit be applied to the course in the shadow
	*               or standard model?
	* @return error string descriptor describing why the operation is 
	*               unsuccessful
	*
	*/
	public String setEnrollLimit( int limit, String course, boolean shadow) {
		String error = null;
		String[] courses = course.split(",");
		for(int i = 0; i < courses.length; ++i)
		{
			String[] idDesc = courses[i].split(" ");
			String cid = idDesc[0];
			Course c = myServerApplication.getCourse(cid);
			if(c == null && error == null)
			{
				error = new String("Course entry " +  String.valueOf(i) + 
						           "is invalid.\n");
			}
			else if(c == null)
			{
				error += " course entry " +  String.valueOf(i) + 
						 "is also invalid.\n";
			}
			else
			{
				c.setEnrollLim(limit);
				myServerApplication.updateCourse(c, shadow, this.s);
			}
		}
		return error;
		
	}
	public void getSemesterCourse() {
		
	}
	
	/**
	 * Method addCourse adds a course to the course catalog. if the course
	 *                  already exists an error message is returned
	 * @param shadow   Is this course to be added to the shadow model
	 *                 accessible only by administrators?
	 * @param semester during which semesters is this class to be offered 
	*  @return error   returns an error message if the operation is unsuccessful
	*/
	public String addCourse( String desc, boolean shadow, String semester) {
		String error = null;
		return error;
	}
	
	/**
	 * Method addCourse removes a course from the course catalog. if there is no
	 *                  course identifiable by the entered desc an error is
	 *                  returned
	 *                  already exists an error message is returned
	 * @param shadow   Is this course to be removed from the shadow model
	 *                 accessible only by administrators?
	*  @return error   returns an error message if the operation is unsuccessful
	*/
	public String removeCourse( String desc, boolean shadow) {
		String error = null;
		return error;
	}
	
	/**
	 * Method editCourse edits the semesters during which a course in the catalog
	 *                   is offered. an error message is returned if the requested
	 *                   assignment results in no change, or if the course req'd
	 *                   for alteration does not exist in the model.
	 * @param shadow   Edit the shadow version of the course within the model
	 *                 accessible only by administrators?
	 * @param semester the new semester assignment for when the class will be offered
	*  @return error   returns an error message if the operation is unsuccessful
	*/
	public String editCourse(String course, boolean shadow, String semester) 
	{	
		String error = null;
		String[] courses = course.split(",");
		for(int i = 0; i < courses.length; ++i)
		{
			String[] idDesc = courses[i].split(" ");
			String cid = idDesc[0];
			Course c = myServerApplication.getCourse(cid);
			if(c == null && error == null)
			{
				error = new String("Course entry " +  String.valueOf(i) + 
						           "is invalid.\n");
			}
			else if(c == null)
			{
				error += " course entry " +  String.valueOf(i) + 
						 "is also invalid.\n";
			}
			else
			{
				Semester sem;
				if (semester.equals("FALL"))
					sem = new Semester(Semester.SemesterTerm.FALL);
				else if (semester.equals("SPRING"))
					sem = new Semester(Semester.SemesterTerm.SPRING);
				else if (semester.equals("SUMMER"))
					sem = new Semester(Semester.SemesterTerm.SUMMER);
				else sem = new Semester(Semester.SemesterTerm.EVERY);
				c.setSemester(sem);
				myServerApplication.updateCourse(c, shadow, this.s);
			}
		}
		return error;
		
	}
	
	public String getSemester() {
		return s.getId();
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



	
}