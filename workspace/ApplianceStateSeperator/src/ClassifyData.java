import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 * 
 */

/**
 * @author mawo
 *
 */
public class ClassifyData implements Runnable {
	private Thread t;
	   private String threadName;
	   private Instances data;
	   public String res = "";
	   private String modelPath, resultPath;
	   
	   public ClassifyData(String name){
	       threadName = name;
	       System.out.println("Creating " +  threadName );
	   }
	   
	   public void run() {
	      System.out.println("Running " +  threadName );
	      synchronized(this){
		      try { 
		    	  Classifier cls = (Classifier) weka.core.SerializationHelper.read(modelPath);
		    	  
		    	  Instances labeled = new Instances(data);
		    	  for (int i = 0; i < data.numInstances(); i++) {
		    		   double clsLabel = cls.classifyInstance(data.instance(i));
		    		   labeled.instance(i).setClassValue(clsLabel);
		    		 }
		    		 BufferedWriter writer = new BufferedWriter(
		    		                           new FileWriter(resultPath));
		    		 writer.write(labeled.toString());
		    		 writer.newLine();
		    		 writer.flush();
		    		 writer.close(); 
					 res = "finished";
		     } catch (Exception e) {
		         System.out.println(e);
		     }
		     System.out.println("Thread " +  threadName + " exiting.");
		     notifyAll();
	     }
	   }
	   
	   public void start (Instances data, String modelPath, String resultPath)
	   {
		  this.data = data;
		  this.modelPath = modelPath;
		  this.resultPath = resultPath;
	      System.out.println("Starting " +  threadName );
	      if (t == null)
	      {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
}
