package proj4.common;

import java.util.List;

public class TeacherAssistant {
	
	private String staffId;
	private String name;
	private String available;
	private List<Course> competencies;
	
    public TeacherAssistant() {
    }
    
    public String getStaffId() {
    	return staffId;
    }
    
    public void setStaffId(String s) {
    	this.staffId = s;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String n) {
    	this.name = n;
    }
    
    public String getAvailable() {
    	return available;
    }
    
    public void setAvailable(String a) {
    	this.available = a;
    }
    
    public List<Course> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(List<Course> comp) {
        this.competencies = comp;
    }
}
