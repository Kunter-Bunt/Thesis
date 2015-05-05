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
public class MaxWirkleistung extends Feature {
	private int e;
	private Wirkleistung wirk;
	
	public MaxWirkleistung(ResultSet resultset, boolean normalisiert, int epsilon) {
		super(resultset);
		e = epsilon;
		wirk = new Wirkleistung(resultset, normalisiert);
	}
	
	public double getValue(int Unixtime) throws SQLException {
		rs.first();
		while (!rs.isLast()) {
			int now = rs.getInt("Unixtime");
			rs.next();
			int next = rs.getInt("Unixtime");
			if (now <= Unixtime && next > Unixtime){
				rs.previous();
				double max = wirk.getValue(Unixtime);
	
				for (int i = -e; i <= e; i++) {
					wirk.getValue(Unixtime + i);
					if (max < wirk.getValue(Unixtime + i))
						max = wirk.getValue(Unixtime + i);
				}
				return max;
			}
		}
		return 0;
		
	}
}
