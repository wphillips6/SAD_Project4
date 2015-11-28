package proj4.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj4.common.Student;
import proj4.serverapp.AdminEntry;
import proj4.serverapp.ServerApplication;
import proj4.serverapp.StudentEntry;

/**
 * Servlet implementation class Student
 */
@WebServlet("/Student")
public class StudentSv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static StudentEntry se;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentSv() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//se = ServerApplication.getStudentEntry();
		//se = null;
//		while(se == null){
//			se = ServerApplication.getStudentEntry();
//		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		if( se == null ){
			se = (StudentEntry)((ServerApplication)session.getAttribute("srvapp")).getStudentEntry();
		}
		
		// Set response content type
	    response.setContentType("text/html");
	    String s = ((Student)session.getAttribute("student")).getUID();

	    // Determine which form was submitted and call appropriate methods
	    out.println("Function:  " + request.getParameter("function"));
	    String f = request.getParameter("function");
	    if( f.compareTo("setPrefs") == 0) {
	    	String newPrefString = "";
	    	String[] prefs = new String[10];
	    	prefs[0] = request.getParameter("pref0");
	    	prefs[1] = request.getParameter("pref1");
	    	prefs[2] = request.getParameter("pref2");
	    	prefs[3] = request.getParameter("pref3");
	    	prefs[4] = request.getParameter("pref4");
	    	prefs[5] = request.getParameter("pref5");
	    	prefs[6] = request.getParameter("pref6");
	    	prefs[7] = request.getParameter("pref7");
	    	prefs[8] = request.getParameter("pref8");
	    	prefs[9] = request.getParameter("pref9");
	    	if(prefs[0].compareTo("0") != 0){
	    		//out.println(prefs[0]);
	    		newPrefString += prefs[0];
	    		for(int i = 1; i < prefs.length; i++) {
	    			//out.println(prefs[i]);
	    			if(prefs[i].compareTo("0") != 0){
	    				//out.println("3");
	    				newPrefString += ","+prefs[i];
	    			} else {
	    				// I hit a 0 or ---- which means I'm ignoring the rest of the preferences
	    				break;
	    			}
	    		}
	    	}
	    	out.println(newPrefString);
	    	se.setDesiredCourses(s, newPrefString);
	    	se.setNumDesiredCourses(s, Integer.parseInt(request.getParameter("numCourses")));
	    	out.println("<a href=\"student.jsp\">Back</a>");
	    } else {

	    }
	    se.CalcAndStoreRecommendations();
	    session.setAttribute("student", se.getStudent(s));
	}

}
