package proj4.common;

import java.util.List;
import java.util.Vector;

public class TeacherAssistant {

	private String name;
	private String taID;
	private int availNextTerm;
	private  List<Course> courses;
	private  List<Course> teachableCourses;
	private String strComp;

	/**
	 * 
	 * @element-type Course
	 */
	public Vector myCourse;

	public TeacherAssistant() {
		this.taID = "";
		this.name = "";
		this.availNextTerm = 0;
		this.strComp = "";
	}
	
	public TeacherAssistant(String id, String name, int availNextTerm) {
		this.taID = id;
		this.name = name;
		this.availNextTerm = availNextTerm;
		this.strComp = "";
	}
	
	public int getAvailNextTerm() {
		return availNextTerm;
	}

	public void setAvailNextTerm(int availNextTerm) {
		this.availNextTerm = availNextTerm;
	}

	public String getStrComp() {
		return strComp;
	}

	public void setStrComp(String strComp) {
		this.strComp = strComp;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = name;
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

	public String getTaID() {
		return taID;
	}
}