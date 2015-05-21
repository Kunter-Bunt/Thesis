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
		
		ResultSet result = db.getSQL("SELECT * FROM `training` ORDER BY `Unixtime`");
		
		try {
			
			result.first();
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
			FeatureWriter fw = new FeatureWriter(db, false);
			/*fw.zeitreihe("SELECT * FROM `training` ORDER BY `Unixtime`", "INSERT INTO `trainingzeitnorm` (`Unixtime`, "
					+ "`Wirkleistung t-10`, `Wirkleistung t-9`, `Wirkleistung t-8`, `Wirkleistung t-7`, `Wirkleistung t-6`, "
					+ "`Wirkleistung t-5`, `Wirkleistung t-4`, `Wirkleistung t-3`, `Wirkleistung t-2`, `Wirkleistung t-1`, "
					+ "`Wirkleistung t+0`, `Wirkleistung t+1`, `Wirkleistung t+2`, `Wirkleistung t+3`, `Wirkleistung t+4`, "
					+ "`Wirkleistung t+5`, `Wirkleistung t+6`, `Wirkleistung t+7`, `Wirkleistung t+8`, `Wirkleistung t+9`, "
					+ "`Wirkleistung t+10`, `Klasse`)", 1382290611, 1384072259);
			*/
			fw.experte("SELECT * FROM `training` ORDER BY `Unixtime`", "INSERT INTO `experte` (`Unixtime`, `Wirkleistung`, `FolgeDurchschnitt`, `MaxWirkleistung`, `MinPeakWirkleistung`,`Klasse`)", 1382290611, 1384072259);
		} catch (SQLException e) {System.out.println(e);}
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
