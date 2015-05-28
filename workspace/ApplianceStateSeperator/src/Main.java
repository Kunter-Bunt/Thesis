import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Features.*;
import Vectors.*;

import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.Evaluation;

/**
 * 
 */

/**
 * @author mawo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DBConnect db = new DBConnect("jdbc:mysql://localhost:3306/testdatenbank?" +
                "user=root");
		
		
		ResultSet result = db.getSQL("SELECT * FROM `training` ORDER BY `Unixtime`");
		
		try {
			
			//result.first();
			/*
			 * System.out.println(result.getString("Wirkleistung"));
			result.next();
			System.out.println(result.getString("Wirkleistung"));
			*/
			//sonstige Aktionen
			/*int t = 1382295291;
			System.out.println("");
			System.out.print("Zeitreihe: ");
			Zeitreihe zeitreihe = new Zeitreihe(result, false);
			ArrayList<Double> z = zeitreihe.getFeatureVector(t);
			for (double wert: z) System.out.print(wert + " ");
			System.out.println("");
			System.out.println("");
			
			System.out.print("Experte: ");
			Experte experte = new Experte(result, false, 10, 10, 50);
			ArrayList<Double> e = experte.getFeatureVector(t);
			for (double wert: e) System.out.print(wert + " ");
			System.out.println("");
			System.out.println("");
			*/
			
			//Expander ex = new Expander(db);
			//expand profil0
			//ex.expand("SELECT * FROM `profil8` ORDER BY `Unixtime`", "INSERT INTO profil8training (`Unixtime`,`Reltime`,`Spannung`,`Stromstärke`,`Wirkleistung`, `Klasse`)", 1384066710, 5550);
			//FeatureWriter fw = new FeatureWriter(db, false);
			/*fw.zeitreihe("SELECT * FROM `training` ORDER BY `Unixtime`", "INSERT INTO `trainingzeitnorm` (`Unixtime`, "
					+ "`Wirkleistung t-10`, `Wirkleistung t-9`, `Wirkleistung t-8`, `Wirkleistung t-7`, `Wirkleistung t-6`, "
					+ "`Wirkleistung t-5`, `Wirkleistung t-4`, `Wirkleistung t-3`, `Wirkleistung t-2`, `Wirkleistung t-1`, "
					+ "`Wirkleistung t+0`, `Wirkleistung t+1`, `Wirkleistung t+2`, `Wirkleistung t+3`, `Wirkleistung t+4`, "
					+ "`Wirkleistung t+5`, `Wirkleistung t+6`, `Wirkleistung t+7`, `Wirkleistung t+8`, `Wirkleistung t+9`, "
					+ "`Wirkleistung t+10`, `Klasse`)", 1382290611, 1384072259);
			*/
			//fw.experte("SELECT * FROM `training` ORDER BY `Unixtime`", "INSERT INTO `experte` (`Unixtime`, `Wirkleistung`, `FolgeDurchschnitt`, `MaxWirkleistung`, `MinPeakWirkleistung`,`Klasse`)", 1382290611, 1384072259);
			 
			 DataSource source = new DataSource("C:\\Users\\mawo\\Documents\\GitHub\\Thesis\\workspace\\ApplianceStateSeperator\\src\\trainingszeit.arff");
			 Instances data = source.getDataSet();
			 if (data.classIndex() == -1)
				   data.setClassIndex(data.numAttributes() - 1);
			 
			 System.out.println("Successfully loaded Data\n");
			 
			 //String[] optionsrm = weka.core.Utils.splitOptions("-R 1");
			 /*
			 NumericToNominal ntn = new NumericToNominal();
			 ntn.setAttributeIndices("last");
			 ntn.setInputFormat(data);                          
			 Instances newData = Filter.useFilter(data, ntn);
			 */
			 Remove rm = new Remove(); 
			 rm.setAttributeIndices("1");                        
			 //remove.setOptions(optionsrm);                           
			 //remove.setInputFormat(data);  
			 //Instances newData = Filter.useFilter(data, rm);  
			 
			 MultilayerPerceptron mlp = new MultilayerPerceptron();
			 mlp.setHiddenLayers("t");
			 mlp.setTrainingTime(5);
			 
			 FilteredClassifier fc = new FilteredClassifier();
			 fc.setFilter(rm);
			 fc.setClassifier(mlp);
			 
			 Evaluation eval = new Evaluation(data);
			 eval.crossValidateModel(fc, data, 10, new Random(1));
			 System.out.println(eval.toSummaryString(false));
			 System.out.println(eval.toClassDetailsString());
			 System.out.println(eval.toMatrixString());
			
		} catch (Exception e) {System.out.println(e);}
		finally {
			db.cleanSQL();
		}
		
		System.out.println("Finished!");

		/*
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Super geiles Fenster!");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
		*/

	}
}
