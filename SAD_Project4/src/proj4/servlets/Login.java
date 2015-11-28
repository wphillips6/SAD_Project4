package proj4.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proj4.serverapp.ServerApplication;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServerApplication sa;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		sa = new ServerApplication();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		// Set response content type
	    response.setContentType("text/html");

	    // Actual login logic goes here.
	    // Sanitize the input before passing to model
	    // Get return value and redirect or present error
	    // Add copy of object to the session
	    
	    // Debug statements
	    PrintWriter out = response.getWriter();
	    out.println("<h1>" + session.getId() + "</h1>");
	    
	    // Validate user and determine role
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    if( (username.isEmpty()) || (password.isEmpty()) ) {
	    	response.sendRedirect("loginerror.jsp");
	    }
	    else{
	    	int loginValidation = sa.validateUser(username, password);
		    //if( request.getParameter("username").compareTo("user1") == 0){
		    if( loginValidation == 2 ) {  // This is a student login
		    	session.setAttribute("username", username);
		    	session.setAttribute("isadmin", "0");
		    	session.setAttribute("srvapp", sa);
		    	session.setAttribute("student", sa.getStudent(username));
		    	response.sendRedirect("student.jsp");
		    //} else if( request.getParameter("username").compareTo("user2") == 0){
		    } else if( loginValidation == 1 ){  // This is an admin login
		    	session.setAttribute("username", username);
		    	session.setAttribute("isadmin", "1");
		    	session.setAttribute("srvapp", sa);
		    	session.setAttribute("administrator", sa.getAdmin(username));
		    	response.sendRedirect("admin.jsp");
		    } else {
		    	response.sendRedirect("loginerror.jsp");
		    }
	    }
	    
	    //TODO:  Add logging here
	}
}
