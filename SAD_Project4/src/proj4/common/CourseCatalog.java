package proj4.common;

import java.util.ArrayList;
import java.util.List;

import proj4.common.Semester.SemesterTerm;

public class CourseCatalog {
	
	private List<Course> availableCourses;
	
	public CourseCatalog() {
	}
	
	public CourseCatalog(int i) {
		availableCourses = new ArrayList<Course>(i);
		
		availableCourses.add(new Course("1", "CS6210","Advanced Operating Systems", "12", new Semester(SemesterTerm.valueOf("FALL"))));
		availableCourses.add(new Course("2", "CS6250", "Computer Networks", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("3", "CS6300", "Software Development Process", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("4", "CS7641", "Machine Learning", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("5", "CS6290", "High Performance Computer Architecture", "-1", new Semester(SemesterTerm.valueOf("SPRING"))));
		availableCourses.add(new Course("6", "CS6310", "Software Architecture and Design", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("7", "CS6440", "Intro to Health Informatics", "3", new Semester(SemesterTerm.valueOf("FALL"))));
		availableCourses.add(new Course("8", "CS6505", "Computability, Complexity and Algorithms", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("9", "CS7637", "Knowledge-Based Artificial Intelligence, Cognitive Systems", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("10", "CS4495", "Computer Vision", "-1", new Semester(SemesterTerm.valueOf("SPRING"))));
		availableCourses.add(new Course("11", "CS6475", "Computational Photography", "-1", new Semester(SemesterTerm.valueOf("FALL"))));
		availableCourses.add(new Course("12", "CS8803-002", "Introduction to Operating Systems", "-1", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("13", "CS8803-001", "Artificial Intelligence for Robotics", "9", new Semester(SemesterTerm.valueOf("EVERY"))));
		availableCourses.add(new Course("14", "CS6035", "Introduction to Information Security", "-1", new Semester(SemesterTerm.valueOf("SPRING"))));
		availableCourses.add(new Course("15", "CSE6220", "High-Performance Computing", "-1", new Semester(SemesterTerm.valueOf("FALL"))));
		availableCourses.add(new Course("16", "CS7646", "Machine Learning for Trading", "4", new Semester(SemesterTerm.valueOf("SPRING"))));
		availableCourses.add(new Course("17", "CS8803", "Special Topics: Reinforcement Learning", "-1", new Semester(SemesterTerm.valueOf("FALL"))));
		availableCourses.add(new Course("18", "CSE8803", "Special Topics: Big Data", "-1", new Semester(SemesterTerm.valueOf("SPRING"))));
	}
	
	public List<Course> getCourses() {
		return availableCourses;
	}
	
	public Course getCourse(int i) {
		return availableCourses.get(i);
	}
	
	public Course getCourse(String s) {
		for (int i = 0; i < availableCourses.size(); i++) {
			Course c = availableCourses.get(i);
			if ( c.getName().equals(s)) {
				return c;
			}
		}
		return null;
	}
}