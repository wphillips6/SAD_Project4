package proj4.serverapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proj4.common.CourseCatalog;
import proj4.common.Recommendation;
import proj4.common.Semester;
import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Course;
import proj4.common.TeacherAssistant;
import proj4.common.Administrator;
import proj4.engine.ComputationalEngine;

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
	private List<Recommendation> curRecs;

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

		//this.CalcAndStoreRecommendations();
	}
	
	public static void main( String[] args) {
		new ServerApplication();
	}

	public int getCourseDemand(int crsNumber) {
		int retVal = 0;
		float timestamp = this.getCurrentRecTimestamp();
		String topRecNum = "SELECT COUNT(uID_student) FROM CourseData.OptimizerRecs WHERE `TimeStamp` = ? AND CourseID = ?";
		PreparedStatement sqlStmt;
		try {
			sqlStmt = dbConnection.prepareStatement(topRecNum);
			sqlStmt.setFloat(1, timestamp);
			sqlStmt.setInt(2, crsNumber);
			ResultSet rsTopRec = sqlStmt.executeQuery();
			if( rsTopRec.next() ){
				retVal = rsTopRec.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return retVal;
	}
	
	public float getCurrentRecTimestamp(){
		float retVal = 0;
		String topRecNum = "SELECT `TimeStamp` FROM CourseData.OptimizerRecs ORDER BY `TimeStamp` DESC LIMIT 1";
		PreparedStatement sqlStmt;
		try {
			sqlStmt = dbConnection.prepareStatement(topRecNum);
			ResultSet rsTopRec = sqlStmt.executeQuery();
			if( rsTopRec.next() ){
				retVal = rsTopRec.getFloat("TimeStamp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Returning Timestamp:  "+retVal);
		return retVal;
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
				
				l = new ArrayList<Course>();
				if(!rs.getString("CompletedCourses").equals("")){
					String[] arrCourses = rs.getString("CompletedCourses").split(",");
					for(int i = 0; i < arrCourses.length; i++) {
						l.add(this.getCourseByNum(arrCourses[i]));
						//System.out.println("Adding "+arrCourses[i]);
					}
				}
				retVal.setCompletedCourses(l);
				//Get Recommendation
				float timestamp = this.getCurrentRecTimestamp();
				String selStmt = "SELECT CourseID from CourseData.OptimizerRecs WHERE uID_student = ? and TimeStamp = ?";
				PreparedStatement sqlStmt = dbConnection.prepareStatement(selStmt);
				sqlStmt.setString(1, retVal.getUID());
				sqlStmt.setFloat(2, timestamp);
				ResultSet rsTopRec = sqlStmt.executeQuery();
				ArrayList<Course> crsRecList = new ArrayList<Course>();
				while(rsTopRec.next()){
					crsRecList.add(this.getCourseByNum(rsTopRec.getString("CourseID")));
				}
				retVal.setCurrentRecs(crsRecList);
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
				
				ArrayList<Course> l = new ArrayList<Course>();
				if(!rs.getString("TACompetencies").equals("")){
					String[] arrCourses = rs.getString("TACompetencies").split(",");
					for(int i = 0; i < arrCourses.length; i++) {
						l.add(this.getCourseByNum(arrCourses[i]));
						//System.out.println("Adding "+arrCourses[i]);
					}
				}
				retVal.setTeachableCourses(l);
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
				
				ArrayList<Course> l = new ArrayList<Course>();
				if(!rs.getString("ProfCompetencies").equals("")){
					String[] arrCourses = rs.getString("ProfCompetencies").split(",");
					for(int i = 0; i < arrCourses.length; i++) {
						l.add(this.getCourseByNum(arrCourses[i]));
						//System.out.println("Adding "+arrCourses[i]);
					}
				}
				retVal.setCompetencies(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public Administrator getAdmin(String adminID) {
		Administrator retVal = null;
		String selAdmin = "SELECT * from CourseData.Admin WHERE uID = ?";
		try {
			PreparedStatement sqlSelAdmin = dbConnection.prepareStatement(selAdmin);
			sqlSelAdmin.setString(1, adminID);
			ResultSet rs = sqlSelAdmin.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = new Administrator(rs.getString("uID"), rs.getString("Name"));
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
		int retVal = 0;
		System.out.println("Checking credentials in database...");
		String selStudent = "SELECT * from CourseData.Student WHERE uID = ? AND Password = ?";
		String selAdmin = "SELECT * from CourseData.Admin WHERE uID = ? AND Password = ?";
		boolean isAdmin = false;
		try {
//			dbConnection.setAutoCommit(false);			
			PreparedStatement sqlSelStudent = dbConnection.prepareStatement(selStudent);
			PreparedStatement sqlSelAdmin = dbConnection.prepareStatement(selAdmin);
			System.out.println("**Checking " + usrname + " with " + pwd);
			sqlSelStudent.setString(1, usrname);
			sqlSelStudent.setString(2, pwd);
			ResultSet rs = sqlSelStudent.executeQuery();
			rs.last();
			int n = rs.getRow();
			if(n==1) {
				retVal = 2;
			}
			//System.out.println("N is " + n);
			sqlSelAdmin.setString(1, usrname);
			sqlSelAdmin.setString(2, pwd);
			rs = sqlSelAdmin.executeQuery();
			rs.last();
			n = rs.getRow();
			if(n==1) {
				retVal = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Logging
		String updLog = "INSERT INTO CourseData.LoginEvents VALUES(?,?,?,?,null)";
		try {
			PreparedStatement updStatement = dbConnection.prepareStatement(updLog);
			updStatement.setString(1,usrname);
			updStatement.setFloat(2, System.currentTimeMillis());
			updStatement.setInt(3, retVal);
			updStatement.setInt(4, retVal);
			updStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return retVal;
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
				retVal.setNumber(rs.getString("CourseNum"));
				retVal.setDemand(this.getCourseDemand(rs.getInt("CourseNum")));
				System.out.println("Setting Course:  " + retVal.getID() + " " + retVal.getDemand());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
		
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
				retVal.setNumber(rs.getString("CourseNum"));
				retVal.setDemand(this.getCourseDemand(rs.getInt("CourseNum")));
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

	
	/**
	 * updates an existing course in the database with the semester it is to be
	 * offered during as well as a new enrollment limit.
	 * 
	 * @param Course pertaining to an existing course entry in the database.
	 * @param boolean indicating whether the course modification should be applied to the
	 *        standard or shadow version of the linear model
	 * @param the semester terms during which the course is to be offered
	 */
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
	
	
	/**
	 * Used by the other servlets to get a copy of the right objects to interact
	 * with the rest of the model
	 * 
	 * @param Course pertaining containing the data to be inserted into the database.
	 * @param boolean indicating whether the course addition should be applied to the
	 *        standard or shadow version of the linear model
	 * @param the semester terms during which the course is to be offered
	 */
	public void addCourse(Course c, boolean shadow, Semester s){
		String insStmt = "INSERT INTO CourseData.Course (`CourseNum`, `CourseID`, "
				       + "`Description`, `CourseLimit`, `Prerequisite`, `Corequisite`, "
				       + "`SemesterOffered`) Values ( ?, ?, ?, ?, ?, ?, ?) ";
		try{
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			insPrepStmt.setInt(1, Integer.parseInt(c.getNumber()));
			insPrepStmt.setString(2, c.getID());
			insPrepStmt.setString(3, c.getDescription());
			insPrepStmt.setInt(4,c.getEnrollLim());
			
			// setting requisites to -1 for now..
			insPrepStmt.setInt(5, -1);
			insPrepStmt.setInt(6, -1);
			insPrepStmt.setString(7, c.getSemester().getTermDesc());
			insPrepStmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * removes an existing course in the database from therein.
	 * 
	 * @param Course pertaining to an existing course entry in the database.
	 * @param boolean indicating whether the course removal should be applied to the
	 *        standard or shadow version of the linear model
	 */
	public void removeCourse(Course c, boolean shadow ){
		String delStmt = "DELETE FROM CourseData.Course WHERE `CourseNum` = ? AND " +
						 "`CourseID` = ? AND `Description` = ? ";
		try{
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(delStmt);
			insPrepStmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
		
	/**
	 * calls the optimizer to calculate a round of recommendations using
	 * all of the freshest data from the database. 
	 */
	public void CalcAndStoreRecommendations() {
		try {
			Statement truncate = dbConnection.createStatement();
			truncate.executeUpdate("TRUNCATE CourseData.OptimizerRecs");
			CourseCatalog cc = new CourseCatalog(this);
			ComputationalEngine ce = new ComputationalEngine(cc, this.getAllStudents(), this.getAllProfs(), this.getAllTAs());
		    curRecs = new ArrayList<Recommendation>();
		    curRecs = ce.CalculateSchedule();
			long mills = System.currentTimeMillis();
			String insStmt = "INSERT INTO CourseData.OptimizerRecs VALUES(?, ?, ?, ?, ?)";
		
			PreparedStatement insPrepStmt = dbConnection.prepareStatement(insStmt);
			for(int i = 0; i < curRecs.size(); i++){
				Recommendation r = curRecs.get(i);
				List<Student> stuList = r.getStudents();
				for(int j = 0; j < stuList.size(); j++){
					insPrepStmt.setFloat(1, mills);
					insPrepStmt.setString(2, "");
					insPrepStmt.setString(3, stuList.get(j).getUID().substring(0, 6));
					insPrepStmt.setString(4, r.getCourse().getNumber());
					insPrepStmt.setInt(5, 0);
					insPrepStmt.addBatch();
				}
				insPrepStmt.executeBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * Forces a recalc of the current database model parameters and returns
	 * the result
	 * 
	 * @return list of recommendations for analysis
	 */
	public List<Recommendation> getCurrentRecommendations(){
		CalcAndStoreRecommendations();
		return curRecs;
	}
}
