package proj4.common;

import java.util.List;
import java.util.Vector;

public class TeacherAssistant {

	private String name;
	private String taID;
	private int availNextTerm;
	private  List<Course> courses;
	private  List<Course> teachableCourses;

	/**
	 * 
	 * @element-type Course
	 */
	public Vector myCourse;

	public TeacherAssistant() {
		this.taID = "";
		this.name = "";
		this.availNextTerm = 0;
	}
	
	public TeacherAssistant(String id, String name, int availNextTerm) {
		this.taID = id;
		this.name = name;
		this.availNextTerm = availNextTerm;
	}

	public void getName() {
	}

	public void setName(String n) {
	}

	public void getCourses() {
	}

	public void setCourses(List<Course> courses) {
	}

	public List<Course> getTeachableCourses() {

		return null;
	}

	public void setTeachableCourses(List<Course> courses) {
	}

}