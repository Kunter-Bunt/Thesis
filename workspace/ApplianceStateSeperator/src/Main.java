import java.io.*;
import java.sql.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Features.*;
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
			Wirkleistung wirk = new Wirkleistung(result, false);
			System.out.println(wirk.getValue(t));
			FolgeDurchschnitt mean = new FolgeDurchschnitt(result, false, 10);
			System.out.println(mean.getValue(t));
			MaxWirkleistung max = new MaxWirkleistung(result, false, 10);
			System.out.println(max.getValue(t));
			MinPeakWirkleistung minpeak = new MinPeakWirkleistung(result, false, 50);
			System.out.println(minpeak.getValue(t));
			
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
