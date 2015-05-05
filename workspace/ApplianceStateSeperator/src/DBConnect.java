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
	private String url;
	Statement stmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	public DBConnect(String DatenbankUrl) {
		url = DatenbankUrl;
		loadDriver();
		connect();
	}
	

	
	private void loadDriver() {
		 try {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
			    System.out.println("Successfully loaded Driver");
	        } catch (Exception ex) {
	            System.out.println("Failed to load JDBC");
	        }
	}
	
	private void connect() {
		try {
		    conn = DriverManager.getConnection(url);
		    System.out.println("Connected");
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public ResultSet getSQL(String statement) {
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(statement);
		    return rs;
		}
		catch (SQLException ex){
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
			return null;
		}
	}
	
	public void cleanSQL(){
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException sqlEx) { }
	
	        rs = null;
	    }
	
	    if (stmt != null) {
	        try {
	            stmt.close();
	        } catch (SQLException sqlEx) { }
	
	        stmt = null;
	    }
	}
}
