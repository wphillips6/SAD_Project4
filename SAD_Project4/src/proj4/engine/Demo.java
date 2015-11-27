package proj4.engine;

public class Demo {

	public static void main(String[] args) {
		String courseFileName = "/home/ubuntu/Documents/Project 4/Course.csv";
		String studentFileName = "/home/ubuntu/Documents/Project 4/Student.csv";
		String professorFileName = "/home/ubuntu/Documents/Project 4/Professor.csv";
		String taFileName = "/home/ubuntu/Documents/Project 4/TA.csv";
		
		ComputationalEngine scheduler = new ComputationalEngine();
		scheduler.CalculateSchedule(courseFileName, studentFileName, professorFileName, taFileName);
	}
}