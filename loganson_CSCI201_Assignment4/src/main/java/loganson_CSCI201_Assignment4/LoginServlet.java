package loganson_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	public static final long serialVersionUID= 1L;
	
	
	protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String UserName = request.getParameter("Username");
		String Password = request.getParameter("Password");
		
		JDBCConnector myJDBC = new JDBCConnector();
		
		int result = myJDBC.loginUser(UserName, Password);
		
		if (result == -1) {
			out.print("password");
		}
		else if (result == -2) {
			out.print("exist");
		}
		else {
			out.print(result);
		}
	}
	
}