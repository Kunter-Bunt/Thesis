/**
 * 
 */
package Vectors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Features.FolgeDurchschnitt;
import Features.MaxWirkleistung;
import Features.MinPeakWirkleistung;
import Features.Wirkleistung;

/**
 * @author mawo
 *
 */
public class Experte implements FeatureVector {
	
	ResultSet rs;
	ArrayList<String> names;
	Wirkleistung wirk;
	FolgeDurchschnitt mean;
	MaxWirkleistung max;
	MinPeakWirkleistung min;
	int ef, em, tp;
	
	public Experte(ResultSet resultset, boolean normalisiert, int epsilonFolge, int epsilonMax, int thresholdPeak){
		rs = resultset;
		ef = epsilonFolge;
		em = epsilonMax;
		tp = thresholdPeak;
		wirk = new Wirkleistung(resultset, normalisiert);
		mean = new FolgeDurchschnitt(resultset, normalisiert, ef);
		max = new MaxWirkleistung(resultset, normalisiert, em);
		min = new MinPeakWirkleistung(resultset, normalisiert, tp);
		names = new ArrayList<String>();
		names.add("Wirkleistung");
		names.add("FolgeDurchschnitt");
		names.add("MaxWirkleistung");
		names.add("MinPeakWirkleistung");
	}

	/* (non-Javadoc)
	 * @see Vectors.FeatureVector#getFeatureVector(int)
	 */
	@Override
	public ArrayList<Double> getFeatureVector(int t) throws SQLException {
		ArrayList<Double> features = new ArrayList<Double>();
		features.add(wirk.getValue(t));
		features.add(mean.getValue(t));
		features.add(max.getValue(t));
		features.add(min.getValue(t));
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