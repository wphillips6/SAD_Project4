package proj4.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.CourseCatalog;
import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Semester.SemesterTerm;
import proj4.common.TeacherAssistant;

public class ComputationalEngine {
	
	String fileName = "/home/ubuntu/Documents/Project 4/student_schedule.txt";
	String studentFileName = "/home/ubuntu/Documents/Project 4/StudentPreferences_Input.csv";
	String studentHistoryFileName = "/home/ubuntu/Documents/Project 4/StudentHistory_Inputs.csv";
	String professorFileName = "/home/ubuntu/Documents/Project 4/Professor.csv";
	String taFileName = "/home/ubuntu/Documents/Project 4/TA.csv";
	
	//private static final int MAX_COURSE_NUM_SIZE = 2;
	
	private CourseCatalog cCatalog = new CourseCatalog(18);
    private List<Student> students = new ArrayList<Student>(600);
    private List<Student> studentsHistory = new ArrayList<Student>(600);
    private List<Professor> professors = new ArrayList<Professor>();
    private List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
    
    
	public ComputationalEngine() {
		
	}
    
    // Main processing method
	public void CalculateSchedule() {
		// TODO Read the test data from the provided folder
		//boolean readFile = ReadTextFile();
		boolean readStudentFile = ReadStudentCSVFile();
		boolean readStudentHistoryFile = ReadStudentHistoryCSVFile();
		boolean readProfessorFile = ReadProfessorCSVFile();
		boolean readTAFile = ReadTACSVFile();
		
		//if (readFile) {
			//Optimizer op = new Optimizer(ConvertToStudentMatrix(),ConvertToCourseMatrix(),ConvertToPrereqMatrix());
			//op.Calculate();
		if (readStudentFile && readStudentHistoryFile && readProfessorFile && readTAFile) {
			Optimizer op = new Optimizer(ConvertToStudentMatrix(),ConvertToStudentHistoryMatrix(),
					                     ConvertToProfessorMatrix(),ConvertToTAMatrix(),
					                     ConvertToCourseMatrix(),ConvertToPrereqMatrix());
			op.Calculate();
		}
		else {
			System.out.printf("File problem encountered.");
		}
	}
	
	// Method to read the text file
	public boolean ReadTextFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(taFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.toLowerCase().contains("%") && line != null
						&& !line.isEmpty()) { // Skip the lines with comment and empty lines
					line = line.replaceAll("\\s", "");
					String delims = "[.]+";
					String[] courses = line.split(delims);
					
					// Fill the list of desired courses with the information
					List<Course> desiredCourses = new ArrayList<Course>(courses.length);
					for (int j = 0; j < courses.length; j++)
					{
						Course c = cCatalog.getCourse(Integer.parseInt(courses[j])-1);
						desiredCourses.add(c);
					}
					
					// Set the desired course to the student and then add the student to the list 
					Student stud = new Student();
					stud.setDesiredCourses(desiredCourses);
					students.add(stud);
				}
			}
			// close the file
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + taFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + taFileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadStudentCSVFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(studentFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if ( line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] courses = line.split(",");
					
					// Fill the list of desired courses with the information
					List<Course> desiredCourses = new ArrayList<Course>(courses.length-1);
					for (int j = 1; j < courses.length; j++)
					{
						Course c = cCatalog.getCourse(courses[j]);
						desiredCourses.add(c);
					}
					
					// Set the desired course to the student and then add the student to the list 
					Student stud = new Student();
					stud.setUID(courses[0]);
					stud.setDesiredCourses(desiredCourses);
					students.add(stud);
				}
			}
			// close the file
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + studentFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + studentFileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadStudentHistoryCSVFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(studentHistoryFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] courses = line.split(",");

					// Fill the list of desired courses with the information
					List<Course> courseHistory = new ArrayList<Course>(courses.length - 1);
					for (int j = 1; j < courses.length; j++) {
						Course c = cCatalog.getCourse(courses[j]);
						courseHistory.add(c);
					}

					// Set the desired course to the student and then add the
					// student to the list
					Student stud = new Student();
					stud.setUID(courses[0]);
					stud.setDesiredCourses(courseHistory);
					studentsHistory.add(stud);
				}
			}
			// close the file
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + studentHistoryFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + studentHistoryFileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadProfessorCSVFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(professorFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] courses = line.split(",");

					// Fill the list of desired courses with the information
					List<Course> comp = new ArrayList<Course>(courses.length - 1);
					for (int j = 1; j < courses.length; j++) {
						Course c = cCatalog.getCourse(courses[j]);
						comp.add(c);
					}

					// Set the desired course to the student and then add the
					// student to the list
					Professor prof = new Professor();
					prof.setName(courses[0]);
					prof.setCompetencies(comp);
					professors.add(prof);
				}
			}
			// close the file
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + professorFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + professorFileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadTACSVFile() {
	
		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(professorFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] courses = line.split(",");

					// Fill the list of desired courses with the information
					List<Course> comp = new ArrayList<Course>(courses.length - 1);
					for (int j = 1; j < courses.length; j++) {
						Course c = cCatalog.getCourse(courses[j]);
						comp.add(c);
					}

					// Set the desired course to the student and then add the
					// student to the list
					TeacherAssistant ta = new TeacherAssistant();
					ta.setName(courses[0]);
					ta.setTeachableCourses(comp);
					tas.add(ta);
				}
			}
			// close the file
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + studentFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + studentFileName + "'");
			return false;
		}
		return true;
	}
	
	// Method to convert Student Preferences into the array
	public Integer[][] ConvertToStudentMatrix() {
		Integer[][] As = new Integer[600][18];
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < As[i].length; j++) {
				As[i][j] = 0;
			}
			
			// Set the desired course to the student array
			Student stud = students.get(i);
			List<Course> desiredCourses = stud.getDesiredCourses();
			for (int j = 0; j < desiredCourses.size(); j++) {
				Course c = desiredCourses.get(j);
				As[i][Integer.parseInt(c.getID())-1] = 1;
			}
		}
		return As;
	}
	
	// Method to convert Student History information into the array
	public Integer[][] ConvertToStudentHistoryMatrix() {
		Integer[][] Ash = new Integer[studentsHistory.size()][18];
		for (int i = 0; i < studentsHistory.size(); i++) {
			for (int j = 0; j < Ash[i].length; j++) {
				Ash[i][j] = 0;
			}
			
			// Set the desired course to the student array
			Student stud = studentsHistory.get(i);
			List<Course> desiredCourses = stud.getDesiredCourses();
			for (int j = 0; j < desiredCourses.size(); j++) {
				Course c = desiredCourses.get(j);
				Ash[i][Integer.parseInt(c.getID())-1] = 1;
			}
		}
		return Ash;
	}
	
	// Method to convert Professor information into the array
	public Integer[][] ConvertToProfessorMatrix() {
		Integer[][] p = new Integer[professors.size()][18];
		for (int i = 0; i < professors.size(); i++) {
			for (int j = 0; j < p[i].length; j++) {
				p[i][j] = 0;
			}
			
			// Set the desired course to the professor array
			Professor prof = professors.get(i);
			List<Course> comp = prof.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				p[i][Integer.parseInt(c.getID())-1] = 1;
			}
		}
		return p;
	}
	
	// Method to convert TA information into the array
	public Integer[][] ConvertToTAMatrix() {
		Integer[][] t = new Integer[tas.size()][18];
		for (int i = 0; i < tas.size(); i++) {
			for (int j = 0; j < t[i].length; j++) {
				t[i][j] = 0;
			}
			
			// Set the desired course to the professor array
			TeacherAssistant ta = tas.get(i);
			List<Course> comp = ta.getTeachableCourses();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				t[i][Integer.parseInt(c.getID())-1] = 1;
			}
		}
		return t;
	}
	
	// Method to convert course information into the array
	public Integer[][] ConvertToCourseMatrix() {
		Integer[][] Ac = new Integer[18][3];
		List<Course> availableCourses = cCatalog.getCourses();
		
		// Set the course information to the course array
		for (int i = 0; i < availableCourses.size(); i++) {
			Course c = availableCourses.get(i);
			if (c.getSemester().getTerm().equals(SemesterTerm.EVERY)) { 			// Every - 1,1,1
				for (int j = 0; j < 3; j++) {
					Ac[i][j] = 1;
				}
			}
			else if (c.getSemester().getTerm().equals(SemesterTerm.SPRING)) {		// Spring - 1,0,0
				Ac[i][0] = 1;
				Ac[i][1] = 0;
				Ac[i][2] = 0;
			}
			else if (c.getSemester().getTerm().equals(SemesterTerm.FALL)) {			// Fall - 0,0,1
				Ac[i][0] = 0;
				Ac[i][1] = 0;
				Ac[i][2] = 1;
			}
		}
		return Ac;
	}
	
	// Method to convert prerequisite information into the array
	public Integer[][] ConvertToPrereqMatrix() {
		Integer[][] Ap = new Integer[18][18];
		List<Course> availableCourses = cCatalog.getCourses();
		
		// Set the prerequisite course to the prerequisite array
		for (int i = 0; i < availableCourses.size(); i++) {
			for (int j = 0; j < Ap.length; j++) {
				Ap[i][j] = 0;
			}
			
			Course c = availableCourses.get(i);
			if (Integer.parseInt(c.getPrerequisite()) != -1) {
				Ap[i][Integer.parseInt(c.getPrerequisite()) - 1] = 1;
			}
		}
		return Ap;
	}
}