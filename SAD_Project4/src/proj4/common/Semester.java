package proj4.common;

public class Semester {
	
    public enum SemesterTerm {
        FALL,
        SPRING,
        SUMMER,
        EVERY
    }
    private String id;
    private SemesterTerm term;

    public Semester() {
    }
    
    public Semester(SemesterTerm s) {
    	this.term = s;
    }

    public Semester(String semesterID, SemesterTerm t) {
    	this.term = t;
    	this.id = semesterID;
    }
    
    public SemesterTerm getTerm() {
        return term;
    }

    public String getId() {
        return id;
    }
    
    public void setTerm( SemesterTerm term) {
        this.term = term;
    }
    
    public void setTerm(String s) {
		if (s != null) {
			for (SemesterTerm t : SemesterTerm.values()) {
				if (s.equalsIgnoreCase(t.toString())) {
					this.term = t;
				}
			}
		}
	}

}
