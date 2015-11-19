package proj4.common;

public class Course {
	private String num;
	private String id;
	private String description;
	private String limit;
	private String prerequisite;
	private String corequisite;
	private Semester semester;
	private String sys_spec;
	private String intel_spec;
	private String robo_spec;
	private String mach_spec;

	public Course() {
	}

	public Course(String n, String i, String d, String l, String p, 
			String c, Semester s, String ss, String is, String rs, String ms) {
		this.setNum(n);
		this.setID(i);
		this.setDescription(d);
		this.setLimit(l);
		this.setPrerequisite(p);
		this.setCorequisite(c);
		this.setSemester(s);
		this.setSystemSpec(ss);
		this.setIntelSpec(is);
		this.setRoboSpec(rs);
		this.setMachSpec(ms);
	}
	
	public String getNum() {
		return num;
	}

	public void setNum(String n) {
		this.num = n;
	}

	public String getID() {
		return id;
	}

	public void setID(String i) {
		this.id = i;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String d) {
		this.description = d;
	}
	
	public String getLimit() {
		return limit;
	}

	public void setLimit(String l) {
		this.limit = l;
	}

	public String getPrerequisite() {
		return prerequisite;
	}

	public void setPrerequisite(String p) {
		this.prerequisite = p;
	}

	public String getCorequisite() {
		return corequisite;
	}

	public void setCorequisite(String c) {
		this.corequisite = c;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester s) {
		this.semester = s;
	}
	
	public String getSystemsSpec() {
		return sys_spec;
	}

	public void setSystemSpec(String s) {
		this.sys_spec = s;
	}
	
	public String getRoboSpec() {
		return robo_spec;
	}

	public void setRoboSpec(String s) {
		this.robo_spec = s;
	}
	
	public String getIntelSpec() {
		return intel_spec;
	}

	public void setIntelSpec(String s) {
		this.intel_spec = s;
	}
	
	public String getMachSpec() {
		return mach_spec;
	}

	public void setMachSpec(String s) {
		this.mach_spec = s;
	}

}