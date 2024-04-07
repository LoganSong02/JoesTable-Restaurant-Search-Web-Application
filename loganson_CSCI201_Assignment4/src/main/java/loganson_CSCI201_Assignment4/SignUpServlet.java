package loganson_CSCI201_Assignment4;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet{
	public static final long serialVersionUID= 1L;
	
	protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String Email = request.getParameter("Email");
		String Username = request.getParameter("Username");
		String Password = request.getParameter("Password");
		
		JDBCConnector myJDBC = new JDBCConnector();
		
		int userID = myJDBC.registerUser(Username, Password, Email);
		
		if (userID == -1) {
			out.print("Username");
		}
		else if (userID == -2) {
			out.print("Email");
		}
		else {
			myJDBC.insertNewUser(Username, Password, Email);
			userID = myJDBC.loginUser(Username, Password);
			out.print(userID);
		}
		
	}
}