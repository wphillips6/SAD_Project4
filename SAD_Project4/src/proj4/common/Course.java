package proj4.common;

public class Course {
	private String id;
	private String name;
	private String description;
	private String prerequisite;
	private Semester semester;

	public Course() {
	}

	public Course(String i, String n, String d, String p, Semester s) {
		this.setID(i);
		this.setName(n);
		this.setDescription(d);
		this.setPrerequisite(p);
		this.setSemester(s);
	}

	public String getID() {
		return id;
	}

	public void setID(String i) {
		this.id = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		this.name = n;
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
}