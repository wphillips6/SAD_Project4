package proj4.common;

import java.util.Collections;
import java.util.List;

public class Student {
	
	private String uID;
	private String name;
	private int creditsComplete;
	private int coursesComplete;
	private List<Course> desiredCourses;
	private List<Course> completedCourses;
	private List<Course> currentRecs;
	private int numDesiredCourses;
	
	public Student() {
		
	}
	
    public Student(String uID, String name, int credits, int courses, int numDesiredCourses) {
    	this.uID = uID;
    	this.name = name;
    	this.creditsComplete = credits;
    	this.coursesComplete = courses;
    	this.numDesiredCourses = numDesiredCourses;
    	this.currentRecs = Collections.emptyList();
    }
    
    public List<Course> getCurrentRecs() {
    	return this.currentRecs;
    }
    
    public void setCurrentRecs(List<Course> recs){
    	this.currentRecs = recs;
    }
    
    public String getUID() {
    	return uID;
    }
    
    public int getCreditsCompleted(){
    	return creditsComplete;
    }
    
    public void setUID(String u) {
    	this.uID = u;
    }
    
    public String getName(){
    	return name;
    }
    
    public int getNumDesiredCourses() {
    	return numDesiredCourses;
    }
    
    public List<Course> getDesiredCourses() {
    	return desiredCourses;
    }

    public void setDesiredCourses(List<Course> c) {
        this.desiredCourses = c;
    }
    
    public List<Course> getCompletedCourses() {
    	return completedCourses;
    }

    public void setCompletedCourses(List<Course> c) {
        this.completedCourses = c;
    }
}
