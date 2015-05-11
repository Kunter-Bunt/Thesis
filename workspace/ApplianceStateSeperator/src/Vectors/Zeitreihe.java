/**
 * 
 */
package Vectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Features.Wirkleistung;

/**
 * @author mawo
 *
 */
public class Zeitreihe implements FeatureVector {
	
	ResultSet rs;
	ArrayList<String> names;
	Wirkleistung wirk;
	
	public Zeitreihe(ResultSet resultset, boolean normalisiert){
		rs = resultset;
		wirk = new Wirkleistung(resultset, normalisiert);
		names = new ArrayList<String>();
		for (int i = -10; i < 0; i++) names.add("Wirkleistung t-" + i);
		for (int i = 0; i <= 10; i++) names. add("Wirkleistung t+" + i);
	}

	/* (non-Javadoc)
	 * @see Vectors.FeatureVector#getFeatureVector(int)
	 */
	@Override
	public ArrayList<Double> getFeatureVector(int t) throws SQLException {
		ArrayList<Double> features = new ArrayList<Double>();
		for (int i = -10; i <= 10; i++) features.add(wirk.getValue(t+i));
		return features;
	}

	/* (non-Javadoc)
	 * @see Vectors.FeatureVector#listFeatures()
	 */
	@Override
	public ArrayList<String> listFeatures() {
		return names;
	}

}