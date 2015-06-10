import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
public class BuildModel implements Runnable {
	   private Thread t;
	   private String threadName;
	   private Instances data;
	   private FilteredClassifier fc;
	   public String res = "";
	   private String path;
	   
	   public BuildModel(String name){
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      synchronized(this){
		      try { 
					 fc.buildClassifier(data);
					 ObjectOutputStream oos = new ObjectOutputStream(
	                            new FileOutputStream(path));
	 oos.writeObject(fc);
	 oos.flush();
	 oos.close();
					 res = "finished";
		     } catch (Exception e) {
		         System.out.println(e);
		     }
		     System.out.println("Thread " +  threadName + " exiting.");
		     notifyAll();
	     }
	   }
	   
	   public void start (Instances data, FilteredClassifier fc, String path)
	   {
		  this.data = data;
		  this.fc = fc;
		  this.path = path;
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}
