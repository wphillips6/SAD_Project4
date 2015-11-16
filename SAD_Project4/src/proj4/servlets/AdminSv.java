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

import org.apache.tomcat.util.codec.binary.StringUtils;

import proj4.serverapp.AdminEntry;
import proj4.serverapp.ServerApplication;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class AdminSv extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static AdminEntry ae;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSv() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

	}

	/**
	 * GET requests redirect to the homepage
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get session and check that it is valid and this person is an admin
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if( session == null || ((String)session.getAttribute("isadmin")).compareTo("1") != 0) {
			response.sendRedirect("index.jsp");
		}
		if( ae == null ){
			ae = (AdminEntry)((ServerApplication)session.getAttribute("srvapp")).getAdminEntry();
		}
		// Set response content type
	    response.setContentType("text/html");

	    // Determine which form was submitted and call appropriate methods
	    out.println("Function:  " + request.getParameter("function"));
	    String f = request.getParameter("function");
	    if( f.compareTo("setEnrollLims") == 0) {
	    	
	    } else if( f.compareTo("setCourse") == 0) {
	    	
	    } else if( f.compareTo("setStaffAsts")== 0){
	    	
	    } else if(f.compareTo("getRecsPrefs") == 0){
	    	
	    }
	    else {
	    	response.sendRedirect("logout.jsp");
	    }

	    Map map = request.getParameterMap();
	    for(int i = 0; i < request.getParameterMap().size(); i++ ){
	    	
	    }
	    
	    out.println("<script>setTimeout(function () {window.location.href = 'admin.jsp'; }, 2000);</script>");
	}

}
