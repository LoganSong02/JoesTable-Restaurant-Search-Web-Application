package loganson_CSCI201_Assignment4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/RestaurantSearchServlet")
public class RestaurantSearchServlet extends HttpServlet{
	public static final long serialVersionUID= 1L;
	
	protected void service (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String restaurantName = request.getParameter("restaurantName");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String choice = request.getParameter("choice");
		
		restaurantName = restaurantName.replace(" ", "_").toLowerCase();
		
		String urlString = "https://api.yelp.com/v3/businesses/search?" + "term=" + restaurantName + "&latitude=" + latitude + "&longitude=" + longitude + "&sort_by=" + choice + "&limit=" + "10";
		URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization","Bearer " + "WX_Oat1wlN5qHrX1sJX8VNrIl49MxweWalAoCW39J7LCxzTjxKZH0KAvneyfQCPBRJihR4slmbQpjUrB83s2N5wVIphSixWml9x1AWa6BuKmDJT2d7F7kKsyHHo5ZXYx");
        BufferedReader in2 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String orderInfo = in2.readLine();
        System.out.println(orderInfo);
        int indexOfTotal = orderInfo.indexOf("total\":");
       
        String processString = orderInfo.substring(15,indexOfTotal-3);
        out.write(processString);
        out.flush();
        //System.out.println(processString);
        
        String processString2 = orderInfo.substring(0,indexOfTotal-4) + "]}";
        System.out.println(processString2);
		Gson gson = new Gson();
		Example RT = gson.fromJson(processString2, Example.class);

		List<Business >restList = RT.getBusinesses();
		
		JDBCConnector myJDBC = new JDBCConnector();
		myJDBC.insertRestaurant(restList);

		//out.print("Success");
		
	}
}