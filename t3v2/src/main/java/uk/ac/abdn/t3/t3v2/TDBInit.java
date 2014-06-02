package uk.ac.abdn.t3.t3v2;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.UpdateAction;

public class TDBInit {

	static String directory = "MyDatabases/Dataset1" ;
	 static Dataset dataset = TDBFactory.createDataset(directory) ;
	
	
	 
	 
	 public static Model getNamedGraph(String namedGraph){
	
		 return dataset.getNamedModel(namedGraph);
		 
		 
		 
	 }
	 
	 
	 public static void updateModel(String prefix, String data,String namedGraph){
		 
		Model m= dataset.getNamedModel(namedGraph);
		String query=prefix+" "
				+ "INSERT DATA{"
				+ data+"}";
		UpdateAction.parseExecute(query, m);
		
		 
		
		 
		 
	 }
	 
}
