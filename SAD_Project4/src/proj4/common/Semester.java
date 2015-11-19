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

	public void setTerm(SemesterTerm t) {
		this.term = t;
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
