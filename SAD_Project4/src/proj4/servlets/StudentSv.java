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
		//session.
		// Set response content type
	    response.setContentType("text/html");

	    // Actual logic goes here.
	    PrintWriter out = response.getWriter();
	    out.println("Function:  " + request.getParameter("function"));
	    Map map = request.getParameterMap();
	    for(int i = 0; i < request.getParameterMap().size(); i++ ){
	    	
	    }
	}

}
