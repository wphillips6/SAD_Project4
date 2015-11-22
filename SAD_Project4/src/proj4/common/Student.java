package proj4.common;

import java.util.List;

public class Student {
	
	private String uID;
	private String name;
	private int creditsComplete;
	private int coursesComplete;
	private List<Course> desiredCourses;
	
	public Student() {
		
	}
	
    public Student(String uID, String name, int credits, int courses) {
    	this.uID = uID;
    	this.name = name;
    	creditsComplete = credits;
    	coursesComplete = courses;
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

    public void setDesiredCourses(List<Course> desiredCourses) {
        this.desiredCourses = desiredCourses;
    }
}
