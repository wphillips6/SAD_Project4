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
	private List<Course> semesterCourses;
	public Semester s;

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

	
	/**
	 * Method addCourse adds a course to the course catalog. 
	 * @param Course   the course to be added.
	*/
	public void addCourse(Course c){
		sa.addCourse(c);
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
		Course c = sa.getCourse(desc);//, shadow);
		if(c == null)
		{
			error = new String("Course entry is invalid.\n");
		}
		else
		{
			sa.removeCourse(c, shadow);
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
	/**
	 * Updates a Professor's competencies.  This is a string of numbers passed in and is directly
	 * stored in the database as it is passed in.
	 * 
	 * @param profID	The ID of the professor to modify
	 * @param comp	The string to store in the database
	 */
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
	
	
	/**
	 * retrieves the requested data for a given student from either the database
	 * or through a recalculation of recommendations using the most recent 
	 * database parameters
	 * 
	 * @param String name or id of the student whose information is to be retrieved
	 * @param boolean determining whether the return list is comprised of courses
	 *        desired by the student or courses recommended by the system for the
	 *        student
	 * @return List of strings containing course identifying information
	 */
	public List<Course> getStudentInfo(String name, boolean prefsOrRecs) {
		Student s = sa.getStudent(name);
		List<Course> courseList = new ArrayList<Course>();
		List<String> stringyCourseList = new ArrayList<String>();
		if( prefsOrRecs) courseList = s.getDesiredCourses();
		else{
			sa.CalcAndStoreRecommendations();
			courseList = sa.getStudentEntry().getCurrentRecommendation(name);
			s = sa.getStudent(name);
			courseList = s.getCurrentRecs();

		}
		if(courseList != null) semesterCourses = new ArrayList<Course>();
		semesterCourses = courseList;
		return courseList;
	}

	/**
	 * returns whether there is any data within the internal list of strings
	 * for display to the admin user
	 * @return boolean value indicating whether there is data for display
	 */
	public boolean displayStudentData(){
		return semesterCourses != null;
	}
	
	/**
	 * returns the data stored in semesterCourses corresponding to data
	 * requested by the admin.
	 * @return list of strings containing course identifying information
	 *         which are either student preferred courses or system
	 *         recommended courses
	 */
	public List<Course> infoForCurrentStudent(){
		return semesterCourses;
	}
	
	/**
	 * sets the internal list of course identifiers to null to reset
	 * for the next request.
	 */
	public void resetStudentDisp(){
		semesterCourses = null;
	}




	
}