package proj4.common;

import java.util.List;

public class CourseCatalog {
	
	private List<Course> availableCourses;
	
	public CourseCatalog() {
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
			if ( c.getNum().equals(s)) {
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