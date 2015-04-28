/**
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * @author mawo
 *
 */
public class DBConnect {
	public DBConnect() {
		super();
	}
	
	Connection conn = null;
	
	public void loadDriver() {
		 try {


	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception ex) {
	            System.out.println("Failed to load JDBC");
	        }
	}
	
	public void connect() {
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" +
		                                   "user=monty&password=greatsqldb");

		    // Do something with the Connection
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public void getSQL() {
		Statement stmt = null;
		ResultSet rs = null;
	}

}
