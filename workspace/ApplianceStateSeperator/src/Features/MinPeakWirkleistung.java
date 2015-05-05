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
public class MinPeakWirkleistung extends Feature {
	private int e;
	//private Wirkleistung wirk;
	private String name;
	
	public MinPeakWirkleistung(ResultSet resultset, boolean normalisiert, int threshold) {
		super(resultset);
		e = threshold;
		if (normalisiert) name = "NormWirkleistung";
		else name = "Wirkleistung";
		//wirk = new Wirkleistung(resultset, normalisiert);
	}
	
	public double getValue(int Unixtime) throws SQLException {
		rs.first();
		while (!rs.isLast()) {
			int now = rs.getInt("Unixtime");
			rs.next();
			int next = rs.getInt("Unixtime");
			if (now <= Unixtime && next > Unixtime){
				rs.previous();
				double min = rs.getDouble(name);
				while (rs.getDouble(name) < e) {
					if (min > rs.getDouble(name))
						min = rs.getDouble(name);
					rs.previous();
				}
				return min;
			}
		}
		return 0;
	}
}
