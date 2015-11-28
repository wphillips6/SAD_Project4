package proj4.common;

import java.util.ArrayList;
import java.util.List;

import proj4.serverapp.ServerApplication;

public class CourseCatalog {
	
	private List<Course> availableCourses;
	
	public CourseCatalog() {
	}
	
	public CourseCatalog(int i) {
		availableCourses = new ArrayList<Course>(i);
	}
	
	public CourseCatalog(ServerApplication sa) {
		//availableCourses = new ArrayList<Course>();
		availableCourses = sa.getAllCourses();
	}
	
	public List<Course> getCourses() {
		return availableCourses;
	}
	
	public Course getCourse(int i) {
		return availableCourses.get(i);
	}
	
	public void setCourses(List<Course> c) {
		this.availableCourses = c;
	}
	
	public void setCourse(Course c) {
		availableCourses.add(c);
	}
	
	public void setCourse(int i, Course c) {
		availableCourses.add(i, c);
	}
	
	public Course getCourseByNum(String s) {
		for (int i = 0; i < availableCourses.size(); i++) {
			Course c = availableCourses.get(i);
			if ( c.getNumber().equals(s)) {
				return c;
			}
		}
		return null;
	}
	
	public Course getCourseById(String s) {
		for (int i = 0; i < availableCourses.size(); i++) {
			Course c = availableCourses.get(i);
			if ( c.getID().equals(s)) {
				return c;
			}
		}
		return null;
	}
	
	public int getCourseCatalogSize() {
		return availableCourses.size();
	}

}