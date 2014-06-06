package uk.ac.abdn.t3.t3v2;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.UpdateAction;

public class Repository {

	private Dataset dataset;
	
	private static Repository singleton=null;
	 
	private Repository (){
		dataset=TDBFactory.createDataset(Configuration.TDB_DIR);
	}
	
	
	public static Repository getSingleton(){
		if(singleton==null){
			singleton=new Repository();
		}
		return singleton;
	}
	 
	 public void restart(){
		 dataset.end();
		 dataset.close();
		 singleton=new Repository();
	 }
	 public boolean removeNamedGraph(String uri){
		try{
			System.out.println("SET FOR READ");
		 dataset.begin(ReadWrite.READ);
		 if(dataset.containsNamedModel(uri)){
				System.out.println("It contains model"+uri);
			 dataset.begin(ReadWrite.WRITE);
			 System.out.println("Found GRAPH for deleting..."+uri);
			 dataset.removeNamedModel(uri);
			 dataset.commit();
			 System.out.println("Graph:"+uri +" was removed from TDB.");
			 return true;
		 }
		 System.out.println("Graph:"+uri +" was not found.");
		 return false;
		}
		catch(Exception e){
			 System.out.println("Exception:"+uri);
			dataset.abort();
			e.printStackTrace();
			System.out.println("Issue when removing Graph! Data may be corrupted");
			return false;
		}
		finally{
			 System.out.println("Finally:Start");
		 dataset.end();
		 System.out.println("Finally:End");
		}
	 }
	 
	 public boolean addToGraph(Model m,String graph){
		try{
		 
		 dataset.begin(ReadWrite.WRITE);
	 Model g=dataset.getNamedModel(graph);
	 System.out.println("PASSED MODEL");
	 m.write(System.out,"TURTLE");
		g.add(m);
		dataset.commit();
		return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally{
			dataset.end();
		}
	 
}
	 public void read(){
		 dataset.begin(ReadWrite.READ);
		 System.out.println("TDB READ");
	 }
	 public void  write(){
		 dataset.begin(ReadWrite.WRITE);
		 System.out.println("TDB Write");
		 
	 }
	 public void end(){
     dataset.end();		 
	 }
	 
	
	 
	 
	 public Model getIndependentModel(String graph){
		 Model m=null;
		 try{
	
		 dataset.begin(ReadWrite.READ);
		 if(dataset.containsNamedModel(graph)){
			 System.out.println("Found GRAPH creating temp Model.."+graph);
			m= dataset.getNamedModel(graph).difference(ModelFactory.createDefaultModel());
		 }
		return m;
		}
		catch(Exception e){
			e.printStackTrace();
		return null;
		}
		finally{dataset.end();	 
		   }
		 
	 }
		 
		 
		 
	 }
	 
	 

