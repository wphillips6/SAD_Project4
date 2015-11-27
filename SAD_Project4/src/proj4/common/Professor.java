package proj4.common;

import java.util.List;

public class Professor {
	
	private String name;
	private String profID;
	private int availNextTerm;
	private List<Course> competencies;
	private String strComp;
	
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

	public Professor() {
		this.profID = "";
		this.name = "";
		this.availNextTerm = 0;
		this.strComp = "";
	}
	
	public Professor(String id, String name, int availNextTerm) {
		this.profID = id;
		this.name = name;
		this.availNextTerm = availNextTerm;
		this.strComp = "";
	}
    
    public String getProfID() {
		return profID;
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
