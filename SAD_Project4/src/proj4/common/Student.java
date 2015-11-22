package proj4.common;

import java.util.ArrayList;
import java.util.List;

import proj4.common.Semester.SemesterTerm;

public class Student {
	
	private String uID;
	private String name;
	private int creditsComplete;
	private int coursesComplete;
	private List<Course> desiredCourses;
	private int numDesiredCourses;
	
	public Student() {
		
	}
	
    public Student(String uID, String name, int credits, int courses, int numDesiredCourses) {
    	this.uID = uID;
    	this.name = name;
    	creditsComplete = credits;
    	coursesComplete = courses;
    	this.numDesiredCourses = numDesiredCourses;
    }
    
    public String getUID() {
    	return uID;
    }
    
    public void setUID(String u) {
    	this.uID = u;
    }
    
    public List<Course> getDesiredCourses() {
    	return desiredCourses;
    }
    
    public int getNumDesiredCourses() {
    	return numDesiredCourses;
    }

    public void setDesiredCourses(List<Course> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }
    
    public String getName(){
    	return name;
    }
}
