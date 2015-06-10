import java.io.*;
import java.sql.*;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

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
import weka.core.Utils;

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
		
		try{
			
			MultilayerPerceptron mlp = new MultilayerPerceptron();
			Remove rm = new Remove(); 
			
			 mlp.setHiddenLayers("t");
			 mlp.setTrainingTime(5);
			 rm.setAttributeIndices("1"); 
			 
			Display display = new Display();
			Shell shell = new Shell(display);
			Text mlpOpt = new Text(shell, SWT.MULTI | SWT.BORDER);
			Text filOpt = new Text(shell, SWT.MULTI | SWT.BORDER);
			Button compute = new Button(shell, SWT.PUSH);
			Text modelPath = new Text(shell, SWT.MULTI | SWT.BORDER);
			Text dataPath = new Text(shell, SWT.MULTI | SWT.BORDER);
			Button save = new Button(shell, SWT.PUSH);
			
			shell.setText("Appliance State Separator");
			GridLayout gridLayout = new GridLayout();
	        gridLayout.numColumns = 3;
	        gridLayout.horizontalSpacing = 80;
			shell.setLayout(gridLayout);
			shell.setSize(500, 200);
		    compute.setText("Test Network");
		    save.setText("Save Network");
		    modelPath.setText("C:\\Users\\mawo\\Documents\\GitHub\\Thesis\\workspace\\ApplianceStateSeperator\\src\\test.model");
		    dataPath.setText("C:\\Users\\mawo\\Documents\\GitHub\\Thesis\\workspace\\ApplianceStateSeperator\\src\\trainingszeit.arff");
		    dataPath.setSize(150, 25);
		    modelPath.setSize(150, 25);
		    compute.setSize(150, 25);
		    save.setSize(150, 25);
		    mlpOpt.setSize(150, 25);
		    filOpt.setSize(150, 25);
		    
		    String[] options = mlp.getOptions();
			String opt = "";
			for (String option : options) opt += option + " ";
		    mlpOpt.setText(opt);
		    
		    options = rm.getOptions();
			opt = "";
			for (String option : options) opt += option + " ";
		    filOpt.setText(opt);
			
		    compute.addSelectionListener(new SelectionListener() {
	
			      public void widgetSelected(SelectionEvent event) {
			    	  try {
						compute(shell, Utils.splitOptions(mlpOpt.getText()), Utils.splitOptions(filOpt.getText()), dataPath.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
			      }
	
			      public void widgetDefaultSelected(SelectionEvent event) {
			    	  try {
			    		  widgetSelected(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
			      }
			 });
		    
		    save.addSelectionListener(new SelectionListener() {
		    	
			      public void widgetSelected(SelectionEvent event) {
			    	  try {
						save(shell, Utils.splitOptions(mlpOpt.getText()), Utils.splitOptions(filOpt.getText()), dataPath.getText(), modelPath.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
			      }
	
			      public void widgetDefaultSelected(SelectionEvent event) {
			    	  try {
			    		  widgetSelected(event);
					} catch (Exception e) {
						e.printStackTrace();
					}
			      }
			 });
			
			shell.open();	
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) display.sleep();
			}
			display.dispose();
			

		}catch(Exception e) {System.out.println(e);}
		finally {
			db.cleanSQL();
		}
	}
	
	public static void save(Shell s, String[] opt, String[] fil, String dataPath, String modelPath) throws Exception{	
	
	Shell shell = new Shell(s);
	Text results = new Text(shell, SWT.MULTI);
	
	shell.setLayout(new FillLayout());
	shell.setSize(100, 100);
	results.setText("computing...");
	results.setEditable(false);
	

	
		 DataSource source = new DataSource(dataPath);
		 Instances data = source.getDataSet();
		 if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
		 
		 Remove rm = new Remove(); 
		 rm.setOptions(fil);                        
		 
		 MultilayerPerceptron mlp = new MultilayerPerceptron();
		 mlp.setOptions(opt);
		 
		 FilteredClassifier fc = new FilteredClassifier();
		 fc.setFilter(rm);
		 fc.setClassifier(mlp);
		 
	String[] options = mlp.getOptions();
	String title = "MultiLayerPerceptron ";
	for (String option : options) title += option + " ";
	final String tit = title;
	shell.setText(title);
	shell.open();		
	new Thread()
	{
	    public void run() {
	    	BuildModel test = new BuildModel(tit);
			test.start(data, fc, modelPath);
			 
			synchronized(test){
	            try{
	                test.wait();
	            }catch(InterruptedException e){
	                e.printStackTrace();
	            }
			}
			Display.getDefault().asyncExec(new Runnable() {
	               public void run() {
	            	   results.setText(test.res);
	               }
	            });
		
	    }
	}.start();
		 
		
}
	
	public static void compute(Shell s, String[] opt, String[] fil, String dataPath) throws Exception{	
	
		Shell shell = new Shell(s);
		Text results = new Text(shell, SWT.MULTI);
		
		shell.setLayout(new FillLayout());
		shell.setSize(400, 500);
		results.setText("computing...");
		results.setEditable(false);
		
	
		
			 DataSource source = new DataSource(dataPath);
			 Instances data = source.getDataSet();
			 if (data.classIndex() == -1)
				   data.setClassIndex(data.numAttributes() - 1);

			 Remove rm = new Remove(); 
			 rm.setOptions(fil);                        

			 MultilayerPerceptron mlp = new MultilayerPerceptron();
			 mlp.setOptions(opt);
			 
			 FilteredClassifier fc = new FilteredClassifier();
			 fc.setFilter(rm);
			 fc.setClassifier(mlp);
			 
		String[] options = mlp.getOptions();
		String title = "MultiLayerPerceptron ";
		for (String option : options) title += option + " ";
		final String tit = title;
		shell.setText(title);
		shell.open();		
		new Thread()
		{
		    public void run() {
		    	TestModel test = new TestModel(tit);
				test.start(data, fc);
				 
				synchronized(test){
		            try{
		                test.wait();
		            }catch(InterruptedException e){
		                e.printStackTrace();
		            }
				}
				Display.getDefault().asyncExec(new Runnable() {
		               public void run() {
		            	   results.setText(test.res);
		               }
		            });
			
		    }
		}.start();
			 
			
	}
}
