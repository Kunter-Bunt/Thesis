import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;


/**
 * 
 */

/**
 * @author mawo
 *
 */
public class TestModel implements Runnable {
	   private Thread t;
	   private String threadName;
	   private Instances data;
	   private FilteredClassifier fc;
	   public String res = "";
	   
	   public TestModel(String name){
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      synchronized(this){
		      try { 
					 
					 Evaluation eval = new Evaluation(data);
					 eval.crossValidateModel(fc, data, 10, new Random(1));
					 
					 res = eval.toSummaryString(false) + "\n" + eval.toClassDetailsString() + "\n" + eval.toMatrixString();
		     } catch (Exception e) {
		         System.out.println(e);
		     }
		     System.out.println("Thread " +  threadName + " exiting.");
		     notifyAll();
	     }
	   }
	   
	   public void start (Instances data, FilteredClassifier fc)
	   {
		  this.data = data;
		  this.fc = fc;
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }

}
