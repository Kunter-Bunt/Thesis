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
			    System.out.println("Loaded Driver");
	        } catch (Exception ex) {
	            System.out.println("Failed to load JDBC");
	        }
	}
	
	public void connect() {
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdatenbank?" +
		                                   "user=root");
		    System.out.println("Connected");
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
		
		try {
		    stmt = conn.createStatement();
		    rs = stmt.executeQuery("SELECT * FROM waschmaschine");
		
		    // or alternatively, if you don't know ahead of time that
		    // the query will be a SELECT...
		
		    if (stmt.execute("SELECT * FROM waschmaschine")) {
		        rs = stmt.getResultSet();
		    }
		
		    rs.first();
		    System.out.println(rs.getString("Wirkleistung"));
		    // Now do something with the ResultSet ....
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		
		        rs = null;
		    }
		
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		
		        stmt = null;
		    }
		}
	}

}
