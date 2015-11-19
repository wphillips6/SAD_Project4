package proj4.common;

import java.util.List;

public class Student {
	
	private String uID;
	private String name;
	private String numCourse;
	private List<Course> desiredCourses;
	private List<Course> completedCourses;
	
    public Student() {
    }
    
    public String getUID() {
    	return uID;
    }
    
    public void setUID(String u) {
    	this.uID = u;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String n) {
    	this.name = n;
    }
    
    public String getNumCourses() {
    	return numCourse;
    }
    
    public void setNumCourse(String n) {
    	this.numCourse = n;
    }
    
    public List<Course> getDesiredCourses() {
        return desiredCourses;
    }

    public void setDesiredCourses(List<Course> dc) {
        this.desiredCourses = dc;
    }
    
    public List<Course> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(List<Course> cc) {
        this.completedCourses = cc;
    }
}
