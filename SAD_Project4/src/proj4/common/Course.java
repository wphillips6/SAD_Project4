package proj4.common;

public class Course implements Comparable {
	private String id;
	private String number;
	private String description;
	private String prerequisite;
	private Semester semester;
	private int enrollLim;

	public Course() {
	}

	public Course(String i, String n, String d, String p, Semester s, int enrollLim) {
		this.setID(i);
		this.setNumber(n);
		this.setDescription(d);
		this.setPrerequisite(p);
		this.setSemester(s);
		this.enrollLim = enrollLim;
	}

	public int getEnrollLim() {
		return enrollLim;
	}

	public void setEnrollLim(int enrollLim) {
		this.enrollLim = enrollLim;
	}

	public String getID() {
		return id;
	}

	public void setID(String i) {
		this.id = i;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String n) {
		this.number = n;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String p) {
		this.prerequisite = p;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester s) {
		this.semester = s;
	}
	
	@Override
	public int compareTo(Object c) {
		System.out.println("Comparing "+this.getID()+" with "+((Course)c).getID());
		return ((Course)c).getNumber().compareTo(this.getNumber());
	}

	@Override
	public boolean equals(Object obj) {
		//System.out.println("Comparing "+this.getID()+" with "+((Course)obj).getID()+" "+this.getID().equals(((Course)obj).getID()));
		return this.getID().equals(((Course)obj).getID());
	}

}