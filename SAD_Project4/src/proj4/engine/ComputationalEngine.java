package proj4.engine;

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
		
		if (readStudentFile && readCourseFileName && readProfessorFile && readTAFile) {
			Optimizer op = new Optimizer(cCatalog, students, professors, tas);
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
}