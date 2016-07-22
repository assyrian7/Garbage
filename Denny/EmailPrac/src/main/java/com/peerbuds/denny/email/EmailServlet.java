package com.peerbuds.denny.email;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmailServlet
 */
@WebServlet("/EmailServlet")
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EmailSessionBean emailBean;  
	
    public EmailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
    		String to = request.getParameter("to");
    		String subject = request.getParameter("subject");
    		String body = request.getParameter("body");
    		
    		emailBean.sendEmail(to, subject, body);
    		
    		response.setContentType("text/html;charset=UTF-8");
    		PrintWriter out = response.getWriter();
    		
    		try 
    		{
    			out.println("<html>");
    			out.println("<head>");
    			out.println("<title>Servlet EmailServlet</title>");
    			out.println("</head>");
    			out.println("<body>");
    			out.println("<h1>Form Submitted</h1>");
    			out.println("</body>");
    			out.println("</html>");
    		} finally {
    			out.close();
    		}
    	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
}
