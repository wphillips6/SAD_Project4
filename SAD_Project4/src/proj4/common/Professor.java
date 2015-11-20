package proj4.common;

import java.util.List;

public class Professor {
	
	private String name;
	private String taID;
	private int availNextTerm;
	private List<Course> competencies;
	
	public Professor() {
		this.taID = "";
		this.name = "";
		this.availNextTerm = 0;
	}
	
	public Professor(String id, String name, int availNextTerm) {
		this.taID = id;
		this.name = name;
		this.availNextTerm = availNextTerm;
	}
    
    public String getName() {
    	return name;
    }
    
    public void setName(String n) {
    	this.name = n;
    }
    
    public List<Course> getCompetencies() {
        return competencies;
    }

    public void setCompetencies(List<Course> comp) {
        this.competencies = comp;
    }
}
