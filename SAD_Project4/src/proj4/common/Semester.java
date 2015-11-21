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

    public SemesterTerm getTerm() {
        return term;
    }

    public void setTerm(String semesterID, SemesterTerm term) {
        this.term = term;
        this.id = semesterID;
    }
}
