import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Features.*;
import Vectors.*;
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
		
		ResultSet result = db.getSQL("SELECT * FROM `waschmaschine` ORDER BY `Unixtime`");
		
		try {
			
			result.first();
			/*
			 * System.out.println(result.getString("Wirkleistung"));
			result.next();
			System.out.println(result.getString("Wirkleistung"));
			*/
			//sonstige Aktionen
			int t = 1314228360;
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
			
		} catch (SQLException e) {System.out.println("Fehler");}
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
