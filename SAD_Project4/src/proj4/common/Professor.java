package proj4.common;

import java.util.List;

public class Professor {
	
	private String name;
	private List<Course> competencies;
	
    public Professor() {
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
