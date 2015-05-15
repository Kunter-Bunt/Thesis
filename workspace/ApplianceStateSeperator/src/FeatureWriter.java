import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Vectors.Zeitreihe;

/**
 * 
 */

/**
 * @author mawo
 *
 */
public class FeatureWriter {
	private DBConnect db;
	private String name;
	
	public FeatureWriter (DBConnect database, boolean normalisiert) {
		db = database;
		if (normalisiert) name = "NormWirkleistung";
		else name = "Wirkleistung";
	}
	
	public void zeitreihe (String input, String output, int start, int end) throws SQLException{
		ResultSet rs = db.getSQL(input);
		String values;
		rs.first();
		/*
		for (int i = 0; i <= 10; i++) {
			values = " VALUES (" + rs.getInt("Unixtime") + ", ";
			
			for (int k = i; k <= 10; k++) values += 0 + ", ";
			for (int j = i; j > 0; j--) rs.previous();
			for (int l = -i; l <= 10; l++) {
				values += rs.getDouble(name) + ", ";
				rs.next();
			}
			for (int m = 0; m < 10; m++) rs.previous();
			
			values += rs.getInt("Klasse") + ")";
			db.setSQL(output + values);
			rs.next();
		}*/
		for (int m = 0; m <= 10; m++) rs.next();
		while (rs.getInt("Unixtime") <= end-10) {
			values = " VALUES (" + rs.getInt("Unixtime") + ", ";
			
			for (int i = 0; i <= 10; i++) rs.previous();
			for (int i = -10; i <= 10; i++) {
				values += rs.getDouble(name) + ", ";
				rs.next();
			}
			for (int i = 0; i < 10; i++) rs.previous();
			
			values += rs.getInt("Klasse") + ")";
			System.out.println(output+ values);
			db.setSQL(output + values);
			rs.next();
		}
		/*
		for (int i = 10; i >= 0; i--) {
			values = " VALUES (" + rs.getInt("Unixtime") + ", ";

			for (int m = 0; m <= 10; m++) rs.previous();
			for (int l = -10; l <= i; l++) {
				values += rs.getDouble(name) + ", ";
				rs.next();
			}			
			for (int k = i; k <= 10; k++) values += 0 + ", ";
			for (int j = i; j < 10; j++) rs.previous();
			
			values += rs.getInt("Klasse") + ")";
			db.setSQL(output + values);
			rs.next();
		}*/
	}

}
