package loganson_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FindUserServlet")
public class FindUserServlet extends HttpServlet{
	public static final long serialVersionUID= 1L;
	
	protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("user_id");
		System.out.println("userId is : " + userId);
		
		JDBCConnector myJDBC = new JDBCConnector();
		String username = myJDBC.FindUsername(userId);
		//System.out.println(username);
		out.println(username);
	}
}
