package Engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.CourseCatalog;
import proj4.common.Semester;
import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Semester.SemesterTerm;
import proj4.common.TeacherAssistant;

public class ComputationalEngine {
	
	String courseFileName = "/home/ubuntu/Documents/Project 4/Course.csv";
	String studentFileName = "/home/ubuntu/Documents/Project 4/Student.csv";
	String professorFileName = "/home/ubuntu/Documents/Project 4/Professor.csv";
	String taFileName = "/home/ubuntu/Documents/Project 4/TA.csv";
	
	private CourseCatalog cCatalog = new CourseCatalog();
    private List<Student> students = new ArrayList<Student>();
    private List<Professor> professors = new ArrayList<Professor>();
    private List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
    
    
	public ComputationalEngine() {
		
	}
    
    // Main processing method
	public void CalculateSchedule() {
		
		boolean readCourseFileName = ReadCourseCSVFile();
		boolean readStudentFile = ReadStudentCSVFile();
		boolean readProfessorFile = ReadProfessorCSVFile();
		boolean readTAFile = ReadTACSVFile();
		
		//if (readFile) {
			//Optimizer op = new Optimizer(ConvertToStudentMatrix(),ConvertToCourseMatrix(),ConvertToPrereqMatrix());
			//op.Calculate();
		if (readStudentFile && readCourseFileName && readProfessorFile && readTAFile) {
			Optimizer op = new Optimizer(ConvertToStudentMatrix(), ConvertToStudentHistoryMatrix(),
					                     ConvertToProfessorMatrix(),ConvertToTAMatrix(),
					                     ConvertToCourseMatrix(),ConvertToPrereqMatrix());
			op.Calculate();
		}
		else {
			System.out.printf("File problem encountered.");
		}
	}
	
	public boolean ReadCourseCSVFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(courseFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			List<Course> courseList = new ArrayList<Course>();
			
			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] courses = line.split(",");

					// Fill the list of course with the information
					Course course = new Course();
					course.setNum(courses[0]);
					course.setID(courses[1]);
					course.setDescription(courses[2]);
					course.setLimit(courses[3]);
					course.setPrerequisite(courses[4]);
					course.setCorequisite(courses[5]);
					Semester s = new Semester();
					s.setTerm(courses[6]);
					course.setSemester(s);
					course.setSystemSpec(courses[7]);
					course.setIntelSpec(courses[8]);
					course.setRoboSpec(courses[9]);
					course.setMachSpec(courses[10]);
					
					//Add the course to the temp list
					courseList.add(course);
				}
			}
			// close the file
			cCatalog.setCourses(courseList);
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + courseFileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + courseFileName + "'");
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
					String[] info = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					
					Student stud = new Student();
					stud.setUID(info[0]);
					stud.setName(info[1]);
					stud.setNumCourse(info[5]);
					
					// Fill the list of desired courses with the information
					String tempDCourse = info[6].replace("\"", "");
					String[] dCourses = tempDCourse.split(",");
					List<Course> desiredCourses = new ArrayList<Course>(dCourses.length);
					for (int j = 1; j < dCourses.length; j++)
					{
						Course c = cCatalog.getCourseByNum(dCourses[j]);
						desiredCourses.add(c);
					}
					stud.setDesiredCourses(desiredCourses);
					
					// Fill the list of completed courses with the information
					String tempCCourse = info[7].replace("\"", "");
					String[] cCourses = tempCCourse.split(",");
					List<Course> completeCourses = new ArrayList<Course>(cCourses.length);
					for (int j = 0; j < cCourses.length; j++)
					{
						Course c = cCatalog.getCourseByNum(cCourses[j]);
						completeCourses.add(c);
					}
					stud.setCompletedCourses(completeCourses);
					
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
	
	public boolean ReadProfessorCSVFile() {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(professorFileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			// Read the lines
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) { // Skip the lines with comment and empty lines
					String[] info = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					
					Professor prof = new Professor();
					prof.setStaffId(info[0]);
					prof.setName(info[1]);
					prof.setAvailable(info[2]);

					// Fill the list of desired courses with the information
					String tempComps = info[3].replace("\"", "");
					String[] comps = tempComps.split(",");
					List<Course> pComp = new ArrayList<Course>(comps.length);
					for (int j = 0; j < comps.length; j++) {
						Course c = cCatalog.getCourseByNum(comps[j]);
						pComp.add(j, c);
					}
					prof.setCompetencies(pComp);
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
					String[] info = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
					
					TeacherAssistant ta = new TeacherAssistant();
					ta.setStaffId(info[0]);
					ta.setName(info[1]);
					ta.setAvailable(info[2]);

					// Fill the list of desired courses with the information
					String tempComps = info[3].replace("\"", "");
					String[] comps = tempComps.split(",");
					List<Course> tComp = new ArrayList<Course>(comps.length);
					for (int j = 0; j < comps.length; j++) {
						Course c = cCatalog.getCourseByNum(comps[j]);
						tComp.add(c);
					}
					ta.setCompetencies(tComp);
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
		Integer[][] As = new Integer[students.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < As[i].length; j++) {
				As[i][j] = 0;
			}
			
			// Set the desired course to the student array
			Student stud = students.get(i);
			List<Course> desiredCourses = stud.getDesiredCourses();
			for (int j = 0; j < desiredCourses.size(); j++) {
				Course c = desiredCourses.get(j);
				As[i][Integer.parseInt(c.getNum())-1] = 1;
			}
		}
		return As;
	}
	
	// Method to convert Student History information into the array
	public Integer[][] ConvertToStudentHistoryMatrix() {
		Integer[][] Ash = new Integer[students.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < students.size(); i++) {
			for (int j = 0; j < Ash[i].length; j++) {
				Ash[i][j] = 0;
			}
			
			// Set the desired course to the student array
			Student stud = students.get(i);
			List<Course> completeCourses = stud.getCompletedCourses();
			for (int j = 0; j < completeCourses.size(); j++) {
				Course c = completeCourses.get(j);
				Ash[i][Integer.parseInt(c.getNum())-1] = 1;
			}
		}
		return Ash;
	}
	
	// Method to convert Professor information into the array
	public Integer[][] ConvertToProfessorMatrix() {
		Integer[][] p = new Integer[professors.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < professors.size(); i++) {
			for (int j = 0; j < p[i].length; j++) {
				p[i][j] = 0;
			}
			
			// Set the desired course to the professor array
			Professor prof = professors.get(i);
			List<Course> comp = prof.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				String test = c.getNum();
				p[i][Integer.parseInt(c.getNum())-1] = 1;
			}
		}
		return p;
	}
	
	// Method to convert TA information into the array
	public Integer[][] ConvertToTAMatrix() {
		Integer[][] t = new Integer[tas.size()][cCatalog.getCourseCatalogSize()];
		for (int i = 0; i < tas.size(); i++) {
			for (int j = 0; j < t[i].length; j++) {
				t[i][j] = 0;
			}
			
			// Set the desired course to the professor array
			TeacherAssistant ta = tas.get(i);
			List<Course> comp = ta.getCompetencies();
			for (int j = 0; j < comp.size(); j++) {
				Course c = comp.get(j);
				t[i][Integer.parseInt(c.getNum())-1] = 1;
			}
		}
		return t;
	}
	
	// Method to convert course information into the array
	public Integer[][] ConvertToCourseMatrix() {
		Integer[][] Ac = new Integer[cCatalog.getCourseCatalogSize()][3];
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
		Integer[][] Ap = new Integer[cCatalog.getCourseCatalogSize()][cCatalog.getCourseCatalogSize()];
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