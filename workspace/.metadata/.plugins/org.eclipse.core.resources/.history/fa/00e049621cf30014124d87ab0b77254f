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
public abstract class Feature {
	ResultSet rs;
	
	public Feature(ResultSet resultset) {
		rs = resultset;
	}
	
	abstract double getValue(int Unixtime) throws SQLException;
}
