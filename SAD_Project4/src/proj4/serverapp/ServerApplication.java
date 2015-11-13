package proj4.serverapp;

import java.sql.Connection;

import proj4.common.Student;
import proj4.common.Professor;
import proj4.common.Course;
import proj4.common.TeacherAssistant;

public class ServerApplication {

	public StudentEntry se;
	public AdminEntry ae;
	public Connection dbConnection;
	public StudentEntry myStudentEntry;
	public AdminEntry myAdminEntry;

	public ServerApplication() {
	}

	public void fail() {
	}

	public static void main( String[] args) {
	}

	public int getCourseDemand(String courseID) {
		return 0;
	}

	public Course getCourse(String courseID) {
		return null;
	}

	public Student getStudent(String studentID) {
		return null;
	}

	public TeacherAssistant getTA(String taID) {
		return null;
	}

	public Professor getProf(String profID) {
		return null;
	}

	public int validateUser(String usrname, String pwd) {
		return 0;
	}

	public Course getCourseByName(String name) {
		return null;
	}

}