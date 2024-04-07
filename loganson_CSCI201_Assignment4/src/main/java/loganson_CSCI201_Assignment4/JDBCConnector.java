package loganson_CSCI201_Assignment4;

import java.sql.*;
import java.util.List;

public class JDBCConnector {
	void insertNewUser(String username, String password, String email) {
	    Connection conn = null;
	    PreparedStatement st = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
	        String sql = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
	        st = conn.prepareStatement(sql);
	        st.setString(1, username);
	        st.setString(2, password);
	        st.setString(3, email);
	        st.executeUpdate();
	    } catch (SQLException sqle) {
	        System.out.println("SQLException: " + sqle.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    } finally {
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException on close: " + sqle.getMessage());
	        }
	    }
	}

	
	void insertRestaurant(List<Business> restList) {
	    Connection conn = null;
	    PreparedStatement st = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
	        for (int i = 0; i < restList.size(); i++) {
		        String sql = "INSERT INTO Restaurants (restaurant_id, name, address, phone, cuisine, price, rating, websiteUrl, pictureUrl) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		        st = conn.prepareStatement(sql);
		        
		        st.setString(1, restList.get(i).getId());
		        String sql2 = "SELECT * FROM Restaurants WHERE restaurant_id = ?";
		        PreparedStatement st2 = conn.prepareStatement(sql2);
		        st2.setString(1, restList.get(i).getId());
		        ResultSet rs = st2.executeQuery();
		        if (rs.next()) {
		        	continue;
		        }
		        
		        st.setString(2, restList.get(i).getName());
		        st.setString(3, restList.get(i).getLocation().getAddress1() + " " + restList.get(i).getLocation().getCity() + " " + restList.get(i).getLocation().getZipCode());
		        st.setString(4, restList.get(i).getDisplayPhone());
		        st.setString(5, restList.get(i).getCategories().get(0).getAlias());
		        st.setString(6, restList.get(i).getPrice());
		        st.setString(7, String.valueOf(restList.get(i).getRating()));
		        st.setString(8, restList.get(i).getUrl());
		        st.setString(9, restList.get(i).getImageUrl());
		        
		        st.executeUpdate();
	        }
	    } catch (SQLException sqle) {
	        System.out.println("User are repeatedly searching for the same restaurants!");
	    } catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    } finally {
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException on close: " + sqle.getMessage());
	        }
	    }
	}
	
	int registerUser(String username, String password, String email) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = (Statement) conn.createStatement();
		    rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM USERS WHERE username ='" + username + "'");
		    int countUser = 0;
		    while (rs.next()) {
		    	countUser++;
		    }
		    
		    rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM USERS WHERE email ='" + email + "'");
		    int countEmail = 0;
		    while (rs.next()) {
		    	countEmail++;
		    }

		    if (countEmail != 0) {
		    	return -2;
		    }
		    if (countUser != 0) {
		    	return -1;
		    }

		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return 0;
	}
	
	String restaurantID(String restaurantName, String restaurantAddress) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String restID = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = conn.prepareStatement("SELECT * FROM Restaurants WHERE name = ? AND address = ?");
		    st.setString(1, restaurantName);
		    st.setString(2, restaurantAddress);
		    rs = st.executeQuery();

		    while (rs.next()) {
		    	restID = rs.getString("restaurant_id");
		    	return restID;
		    }
		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return restID;
	}
	
	String FindReservations(String userId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String result = "[";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = conn.prepareStatement("SELECT * FROM Reservations WHERE user_id = ?");
		    st.setString(1, userId);
		    rs = st.executeQuery();

		    while (rs.next()) {
		    	String restID = rs.getString("restaurant_id");
		    	st = conn.prepareStatement("SELECT * FROM Restaurants WHERE restaurant_id = ?");
			    st.setString(1, restID);
			    ResultSet rs2 = st.executeQuery();
			    while (rs2.next()) {
			    	result += "{";
			    	result += "\"" + "date\": \"" + rs.getString("date") + "\", ";
			    	result += "\"" + "time\": \"" + rs.getString("time") + "\", ";
			    	result += "\"" + "name\": \"" + rs2.getString("name") + "\", ";
			    	result += "\"" + "address\": \"" + rs2.getString("address") + "\", ";
			    	result += "\"" + "display_phone\": \"" + rs2.getString("phone") + "\", ";
			    	result += "\"" + "cuisine\": \"" + rs2.getString("cuisine") + "\", ";
			    	result += "\"" + "price\": \"" + rs2.getString("price") + "\", ";
			    	result += "\"" + "rating\": \"" + rs2.getString("rating") + "\", ";
			    	result += "\"" + "img_url\": \"" + rs2.getString("pictureUrl") + "\", ";
			    	result += "\"" + "url\": \"" + rs2.getString("websiteUrl") + "\"}, ";
			    }
		    }
		    if (result.length()>=3) {
		    	result = result.substring(0,result.length()-2);
		    }
		    result += "]";
		    System.out.println(result);
	    	return result;
		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return result;
	}
	
	String FindFavorites(String userId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String result = "[";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = conn.prepareStatement("SELECT * FROM Favorites WHERE user_id = ?");
		    st.setString(1, userId);
		    rs = st.executeQuery();

		    while (rs.next()) {
		    	String restID = rs.getString("restaurant_id");
		    	st = conn.prepareStatement("SELECT * FROM Restaurants WHERE restaurant_id = ?");
			    st.setString(1, restID);
			    ResultSet rs2 = st.executeQuery();
			    while (rs2.next()) {
			    	result += "{";
			    	result += "\"" + "name\": \"" + rs2.getString("name") + "\", ";
			    	result += "\"" + "address\": \"" + rs2.getString("address") + "\", ";
			    	result += "\"" + "display_phone\": \"" + rs2.getString("phone") + "\", ";
			    	result += "\"" + "cuisine\": \"" + rs2.getString("cuisine") + "\", ";
			    	result += "\"" + "price\": \"" + rs2.getString("price") + "\", ";
			    	result += "\"" + "rating\": \"" + rs2.getString("rating") + "\", ";
			    	result += "\"" + "img_url\": \"" + rs2.getString("pictureUrl") + "\", ";
			    	result += "\"" + "url\": \"" + rs2.getString("websiteUrl") + "\"}, ";
			    }
		    }
		    if (result.length()>=3) {
		    	result = result.substring(0,result.length()-2);
		    }
		    result += "]";
		    System.out.println(result);
	    	return result;
		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return result;
	}
	
	
	String FindUsername(String userId) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		String username = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = conn.prepareStatement("SELECT * FROM Users WHERE user_id = ?");
		    st.setString(1, userId);
		    rs = st.executeQuery();

		    while (rs.next()) {
		    	username = rs.getString("username");
		    	return username;
		    }
		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return username;
	}
	
	void insertReservation(String name, String address, String time, String userID, String date) {
	    Connection conn = null;
	    PreparedStatement st = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
	        String sql = "INSERT INTO Reservations (user_id, restaurant_id, date, time) VALUES (?, ?, ?, ?)";
	        st = conn.prepareStatement(sql);
	        st.setString(1, userID);
	        String restID = restaurantID(name, address);
	        st.setString(2, restID);
	        st.setString(3, date);
	        st.setString(4, time);
	        st.executeUpdate();
	    } catch (SQLException sqle) {
	        System.out.println("SQLException: " + sqle.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    } finally {
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException on close: " + sqle.getMessage());
	        }
	    }
		
	}
	
	void removeFavorite(String name, String address, String userID) {
		Connection conn = null;
	    PreparedStatement st = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
	        String sql = "DELETE FROM Favorites WHERE user_id = ? AND restaurant_id = ?";
	        st = conn.prepareStatement(sql);
	        st.setString(1, userID);
	        String restID = restaurantID(name, address);
	        st.setString(2, restID);
	        if (restID != null && !restID.equals("")) {
	        	st.executeUpdate();
	        }
	    } catch (SQLException sqle) {
	        System.out.println("SQLException: " + sqle.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    } finally {
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException on close: " + sqle.getMessage());
	        }
	    }
		
	}
	
	void insertFavorite(String name, String address, String userID) {
		Connection conn = null;
	    PreparedStatement st = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
	        
	        String sql = "INSERT INTO Favorites (user_id, restaurant_id) VALUES (?, ?)";
	        st = conn.prepareStatement(sql);
	        st.setString(1, userID);
	        String restID = restaurantID(name, address);
	        st.setString(2, restID);
	        
	        if (restID != null && !restID.equals("")) {
	        	String sql2 = "SELECT COUNT(*) FROM Favorites WHERE user_id = ? AND restaurant_id = ?";
	        	PreparedStatement st2 = conn.prepareStatement(sql2);
	            st2.setString(1, userID);
	            st2.setString(2, restID);
	            ResultSet rs = st2.executeQuery();
	            if (rs.next() && rs.getInt(1) == 0) { 
	            	st.executeUpdate();
	            }
	        }
	    } catch (SQLException sqle) {
	        System.out.println("SQLException: " + sqle.getMessage());
	    } catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    } finally {
	        try {
	            if (st != null) {
	                st.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException sqle) {
	            System.out.println("SQLException on close: " + sqle.getMessage());
	        }
	    }
		
	}
	
	int loginUser(String username, String password) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/JoesTable?user=root&password=Song@pegasus2023");
		    st = (Statement) conn.createStatement();
		    rs = ((java.sql.Statement) st).executeQuery("SELECT * FROM USERS WHERE username ='" + username + "'");
	
		    int count = 0;
		    while (rs.next()) {
		    	String getPassword = rs.getString("password");
		    	String getuserID = rs.getString("user_id");
		    	if (password.equals(getPassword)) {
		    		return Integer.parseInt(getuserID);
		    	}
		    	else {
		    		return -1;
		    	}
		    }
		    if (count == 0) {
		    	return -2;
		    }
		}catch (SQLException sqle) {
		    System.out.println("SQLException: " + sqle.getMessage());
		}catch (ClassNotFoundException ex) {
	        System.out.println("MySQL Driver not found!");
	    }finally {
		    try {
		        if (st != null) {
		            st.close();
		        }
		        if (conn != null) {
		            conn.close();
		        }
		    } 
		    catch (SQLException sqle) {
		        System.out.println("SQLException on close: " + sqle.getMessage());
		    }
		}
		return 0;
	}

}
