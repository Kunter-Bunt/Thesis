package Features;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 */

/**
 * @author mawo
 *
 */
public class Wirkleistung extends Feature {
	private String name;
	
	public Wirkleistung(ResultSet resultset, boolean normalisiert) {
		super(resultset);
		if (normalisiert) name = "NormWirkleistung";
		else name = "Wirkleistung";
	}
	
	public double getValue(int Unixtime) throws SQLException {
		rs.first();
		while (!rs.isLast()) {
			int now = rs.getInt("Unixtime");
			rs.next();
			int next = rs.getInt("Unixtime");
			if (now <= Unixtime && next > Unixtime){
				rs.previous();
				return rs.getDouble(name);
			}
		}
		return 0;
	}
}
