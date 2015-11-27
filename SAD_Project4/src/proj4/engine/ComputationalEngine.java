package proj4.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proj4.common.Course;
import proj4.common.CourseCatalog;
import proj4.common.Recommendation;
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
	public List<Recommendation> CalculateSchedule() {
		Optimizer op = new Optimizer(cCatalog, students, professors, tas);
		return op.Calculate();
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
					Semester s = new Semester();
					s.setTerm(courses[6]);
					Course course = new Course(courses[1],courses[0],courses[2],courses[4],s);
					course.setEnrollLim(Integer.parseInt(courses[3]));
					
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
					
					Student stud = new Student(info[0], info[1], Integer.parseInt(info[2]),Integer.parseInt(info[3]),Integer.parseInt(info[5]));
					
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
					
					Professor prof = new Professor(info[0], info[1], Integer.parseInt(info[2]));

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
					
					TeacherAssistant ta = new TeacherAssistant(info[0], info[1], Integer.parseInt(info[2]));

					// Fill the list of desired courses with the information
					String tempComps = info[3].replace("\"", "");
					String[] comps = tempComps.split(",");
					List<Course> tComp = new ArrayList<Course>(comps.length);
					for (int j = 0; j < comps.length; j++) {
						Course c = cCatalog.getCourseByNum(comps[j]);
						tComp.add(c);
					}
					ta.setTeachableCourses(tComp);
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