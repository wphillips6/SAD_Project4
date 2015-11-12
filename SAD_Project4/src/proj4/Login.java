package proj4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// Set response content type
	    response.setContentType("text/html");

	    // Actual logic goes here.
	    PrintWriter out = response.getWriter();
	    out.println("<h1>" + session.getId() + "</h1>");
	    if( request.getParameter("username").compareTo("user1") == 0){
	    	session.setAttribute("username", "user1");
	    	session.setAttribute("isadmin", "0");
	    	response.sendRedirect("student.jsp");
	    } else if( request.getParameter("username").compareTo("user2") == 0){
	    	session.setAttribute("username", "user2");
	    	session.setAttribute("isadmin", "1");
	    	response.sendRedirect("admin.jsp");
	    } else {
	    	out.println("<h1>" + "Bad command or file name" + "</h1>");
	    }
	}

}
