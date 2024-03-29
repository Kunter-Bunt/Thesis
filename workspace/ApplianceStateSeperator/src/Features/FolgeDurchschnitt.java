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
public class FolgeDurchschnitt extends Feature {
	private int e;
	private Wirkleistung wirk;
	
	public FolgeDurchschnitt(ResultSet resultset, boolean normalisiert, int epsilon) {
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
				
				double mean = 0;
				for (int i = 0; i < e; i++) {
					mean += wirk.getValue(Unixtime + i)/e ;
				}
				
				return mean;
			}
		}
		return 0;
	}
}
