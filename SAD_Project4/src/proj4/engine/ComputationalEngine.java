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

	private CourseCatalog cCatalog; //= new CourseCatalog();
    private List<Student> students; //= new ArrayList<Student>();
    private List<Professor> professors = new ArrayList<Professor>();
    private List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
    
    
	public ComputationalEngine() {
		
	}
	
	public ComputationalEngine(CourseCatalog cc, List<Student> s, List<Professor> p, List<TeacherAssistant> t) {
		cCatalog = cc;
		students = s;
		professors = p;
		tas = t;
	}
    
	// Main processing method - without reading CSVs
	public void CalculateSchedule() {
		Optimizer op = new Optimizer(cCatalog, students, professors, tas);
		op.Calculate();
	}
	
    // Main processing method - with reading CSVs
	public void CalculateSchedule(String courseFileName, String studentFileName, String professorFileName, String taFileName) {
		if (ReadCourseCSVFile(courseFileName) && ReadStudentCSVFile(studentFileName) && ReadProfessorCSVFile(professorFileName) && ReadTACSVFile(taFileName)) {
			Optimizer op = new Optimizer(cCatalog, students, professors, tas);
			op.Calculate();
		}
		else {
			System.out.printf("File problem encountered.");
		}
	}
	
	public boolean ReadCourseCSVFile(String fileName) {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(fileName);
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
			cCatalog = new CourseCatalog(courseList.size());
			for (int i = 0; i < courseList.size(); i++)
			{
				cCatalog.setCourse(courseList.get(i));
			}
			//cCatalog.setCourses(courseList);
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadStudentCSVFile(String fileName) {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			// Read the lines
			List<Student> studentList = new ArrayList<Student>();
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
					for (int j = 0; j < dCourses.length; j++)
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
					
					studentList.add(stud);
				}
			}
			// close the file
			students = new ArrayList<Student>(studentList.size());
			for (int i = 0; i < studentList.size(); i++)
			{
				students.add(studentList.get(i));
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadProfessorCSVFile(String fileName) {

		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(fileName);
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
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}
		return true;
	}
	
	public boolean ReadTACSVFile(String fileName) {
	
		String line = null;

		try {
			// Read text files
			FileReader fileReader = new FileReader(fileName);
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
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}
		return true;
	}
}