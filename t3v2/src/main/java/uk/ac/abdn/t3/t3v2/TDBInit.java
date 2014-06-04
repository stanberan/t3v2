package uk.ac.abdn.t3.t3v2;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.UpdateAction;

public class TDBInit {

	static String directory = "/Users/stanislavberan/MyDatabases/Dataset2" ;
	static Dataset dataset=TDBFactory.createDataset(directory) ;
	 
	
	 public static Model getNamedGraph(String namedGraph){
		
	return dataset.getNamedModel(namedGraph);
	
		 
	 }
	 
	
	 public static  void removeNamedGraph(String uri){
		 dataset.begin(ReadWrite.WRITE);
		 if(dataset.containsNamedModel(uri)){
			 System.out.println("Found GRAPH for delete..."+uri);
		 }
		 dataset.removeNamedModel(uri);
		dataset.commit();
		 dataset.end();
	 }
	 
	 public static void addToGraph(Model m,String graph){
			dataset.begin(ReadWrite.WRITE);
	 Model g=dataset.getNamedModel(graph);
	 System.out.println("PASSED MODEL");
	 m.write(System.out,"TURTLE");
		g.add(m);
		dataset.commit();
		dataset.end();
		System.out.println("From TDB");
		
		dataset.begin(ReadWrite.READ);
		g.write(System.out,"TURTLE");
		System.out.println("From TDB2");
		
	
		Model m2=TDBInit.getNamedGraph(graph);
		m2.write(System.out,"TURTLE");
		dataset.end();
	 
}
	 public static void read(){
		 dataset.begin(ReadWrite.READ);
		 System.out.println("TDB READ");
	 }
	 public static  void  write(){
		 dataset.begin(ReadWrite.WRITE);
		 System.out.println("TDB Write");
		 
	 }
	 public static void end(){
		
     dataset.end();
		 
	 }
}

