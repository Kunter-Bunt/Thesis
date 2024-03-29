import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 */

/**
 * @author mawo
 *
 */
public class Expander {
	private DBConnect db;
	
	public Expander(DBConnect database) {
		db = database;
	}
	
	public void expand(String input, String output, int start, int length) throws SQLException{
		String values;
		ResultSet rs = db.getSQL(input);
		rs.first();
		for (int i = start; i < rs.getInt("Unixtime"); i++){
			values = " VALUES (" + i + ", ";
			values += (i - start) + ", ";
			values += rs.getDouble("Spannung") + ", ";
			values += 0 + ", ";
			values += 0 + ", ";
			values += 0 + ")";
			//System.out.println(output + values);
			db.setSQL(output + values);
		}
		while (!rs.isLast()) {
			int now = rs.getInt("Unixtime");
			rs.next();
			int next = rs.getInt("Unixtime");
			//System.out.println(next);
			rs.previous();
			//System.out.println(next - now);
			for (int i = 0; i < next - now; i++){
				values = " VALUES (" + (rs.getInt("Unixtime") + i) + ", ";
				values += (rs.getInt("Unixtime") + i - start) + ", ";
				values += rs.getDouble("Spannung") + ", ";
				values += rs.getDouble("Stromstärke") + ", ";
				values += rs.getInt("Wirkleistung") + ", ";
				values += 0 + ")";
				//System.out.println(output + values);
				db.setSQL(output + values);
			}
			rs.next();	
		}
		for (int i = 0; i < length - (rs.getInt("Unixtime") - start); i++){
			values = " VALUES (" + (rs.getInt("Unixtime") + i) + ", ";
			values += (rs.getInt("Unixtime") + i - start) + ", ";
			values += rs.getDouble("Spannung") + ", ";
			values += rs.getDouble("Stromstärke") + ", ";
			values += rs.getInt("Wirkleistung") + ", ";
			values += 0 + ")";
			//System.out.println(output + values);
			db.setSQL(output + values);
		}
		
	}

}
