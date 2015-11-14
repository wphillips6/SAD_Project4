package proj4.common;

public class Semester {
	
    public enum SemesterTerm {
        FALL,
        SPRING,
        SUMMER,
        EVERY
    }
    
    private SemesterTerm term;

    public Semester() {
    }
    
    public Semester(SemesterTerm s) {
    	this.term = s;
    }

    public SemesterTerm getTerm() {
        return term;
    }

    public void setTerm(SemesterTerm term) {
        this.term = term;
    }
}
