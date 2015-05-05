/**
 * 
 */
package Vectors;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author mawo
 *
 */
public interface FeatureVector {
	
	public ArrayList<Double> getFeatureVector(int t) throws SQLException;
	
	public ArrayList<String> listFeatures();
	

}
