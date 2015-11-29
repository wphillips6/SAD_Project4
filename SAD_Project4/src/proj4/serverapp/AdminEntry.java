package proj4.serverapp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import proj4.common.Course;
import proj4.common.Professor;
import proj4.common.Student;
import proj4.common.TeacherAssistant;
import proj4.common.Semester;
import org.apache.tomcat.jni.Time;


public class AdminEntry {
	private Integer enrollLimit;
	private List<String> semesterCourse;
	public Semester s;
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
		String error = "";
		String insStmt = "UPDATE CourseData.Course SET `CourseLimit` = ? WHERE CourseNum = ?";
		try {
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, limit);
			insPrepStmt.setString(2, course);
			insPrepStmt.executeUpdate();
		} catch (SQLException e) {
			error = e.getMessage();
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
		String[] courses = desc.split(",");
		for(int i = 0; i < courses.length; ++i)
		{
			String[] idDesc = courses[i].split(" ");
			String cid = idDesc[0];
			String cDesc = "";
			if(idDesc.length > 1) cDesc = idDesc[1];
			char[] cidChars = idDesc[0].toCharArray();
			int cNumStrt = 0;
			for(int j = 0; j < cidChars.length && cNumStrt == 0; ++j){
				if (cidChars[j] == '0' ||
					cidChars[j] == '1' ||
					cidChars[j] == '2' || 
					cidChars[j] == '3' ||
					cidChars[j] == '4' || 
					cidChars[j] == '5' ||
					cidChars[j] == '6' ||
					cidChars[j] == '7' ||
					cidChars[j] == '8' ||
					cidChars[j] == '9') cNumStrt = j; 
		
			}
			if(idDesc.length > 1) cDesc = idDesc[1];
			if((cDesc == "" || cNumStrt == 0) && error == null)
			{
				error = new String("Course entry " +  String.valueOf(i) + 
						           "has invalid format.\n\n Addition of a " +
						           " course to the catalog requires inputs\n" +
						           " to be formatted as follows:\n\n <Department" +
						           " abbreviation><Course number> <Course Descriptor>" +
						           "\n\n With each entry delimited by a comma.");
			}
			else if(cDesc == "" || cNumStrt == 0)
			{
				error += " course entry " +  String.valueOf(i) + 
						 "also has invalid format.\n";
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
				Course c = new Course(cid,cid.substring(cNumStrt), cDesc, "",sem );
				sa.addCourse(c, shadow, this.s);
			}
		}
		return error;
		

	}
	
	/**
	 * Method removeCourse removes a course from the course catalog. if there is no
	 *                  course identifiable by the entered desc an error is
	 *                  returned
	 *                  already exists an error message is returned
	 * @param shadow   Is this course to be removed from the shadow model
	 *                 accessible only by administrators?
	*  @return error   returns an error message if the operation is unsuccessful
	*/
	public String removeCourse( String desc, boolean shadow) {
		String error = null;
		String[] courses = desc.split(",");
		for(int i = 0; i < courses.length; ++i)
		{
			String[] idDesc = courses[i].split(" ");
			String cid = idDesc[0];
			Course c = sa.getCourse(cid);//, shadow);
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

				sa.removeCourse(c, shadow);
			}
		}
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
			Course c = sa.getCourse(cid);//,shadow);
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
				sa.updateCourse(c, shadow, this.s);
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
	
	public void getTAPool() {
		
	}


	public void addTA(String name) {
		System.out.println("AE: adding TA " + name);
	}
	
	/**
	 * Updates a TA's availability for next term.
	 * 
	 * @param taID				The TA to modify passed by id
	 * @param isAvailNextTerm	0 - TA is not available next term
	 * 							1 - TA is available
	 */
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

	/**
	 * Updates a Prof's availability for next term.
	 * 
	 * @param taID				The Prof to modify passed by id
	 * @param isAvailNextTerm	0 - Prof is not available next term
	 * 							1 - Prof is available
	 */
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
	
	/**
	 * Updates a TA's competencies.  This is a string of numbers passed in and is directly
	 * stored in the database as it is passed in.
	 * 
	 * @param taID	The ID of the TA to modify
	 * @param comp	The string to store in the database
	 */
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
	
	public List<String> getStudentInfo(String name, boolean prefsOrRecs) {
		Student s = sa.getStudent(name);
		List<Course> courseList = new ArrayList<Course>();
		List<String> stringyCourseList = new ArrayList<String>();
		if( prefsOrRecs) courseList = s.getDesiredCourses();
		else courseList =  sa.getStudentEntry().getCurrentRecommendation(name);
		for (Course c: courseList){
			stringyCourseList.add(c.getID() + " " + c.getDescription());
		}
		return stringyCourseList;
	}



	
}