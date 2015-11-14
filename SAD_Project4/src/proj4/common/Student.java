package proj4.common;

import java.util.List;

public class Student {
	
	private String uID;
	private List<Course> desiredCourses;
	
    public Student() {
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
