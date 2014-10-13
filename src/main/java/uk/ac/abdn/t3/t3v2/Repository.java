package uk.ac.abdn.t3.t3v2;

import uk.ac.abdn.t3.t3v2.models.ModelController;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ReadWrite;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDB;
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
	 public void clearRepo(){
		 try{
				dataset.begin(ReadWrite.WRITE);
		 dataset.getContext().set(TDB.symUnionDefaultGraph, true) ;
		Model defaultModel=dataset.getDefaultModel();
	
		defaultModel.removeAll();
		dataset.commit();
		 }
		 catch(Exception e){
			 dataset.abort();
		 }
		 finally{
		dataset.end();}
	 }
	 public boolean removeNamedGraph(String uri){
		try{
			System.out.println("SET FOR READ");
		 dataset.begin(ReadWrite.READ);
		 boolean found=dataset.containsNamedModel(uri);
		 dataset.end();
		 dataset.begin(ReadWrite.WRITE);
		 if(found){
				System.out.println("It contains model"+uri);
		
			 System.out.println("Found GRAPH for deleting..."+uri);
			 dataset.removeNamedModel(uri);
			 dataset.commit();
			 System.out.println("Graph:"+uri +" was removed from TDB.");
			 return true;
		 }
		 System.out.println("Graph:"+uri +" was not found.");
		 dataset.abort();
		 return false;
		}
		catch(Exception e){
			 System.out.println("Exception:"+uri);
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
	 
	 public void preloadData(){
			Model m=ModelFactory.createDefaultModel();
			m.read("http://t3.abdn.ac.uk/ontologies/simbbox001.ttl",null,"TTL");
			registerDeviceData(ModelController.TTT_GRAPH+"simbbox001"+"/data", m);
			Model m1=ModelFactory.createDefaultModel();
			m1.read("http://t3.abdn.ac.uk/ontologies/simbbox002.ttl",null,"TTL");
			registerDeviceData(ModelController.TTT_GRAPH+"simbbox002"+"/data", m);
			
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
	 
	 public boolean registerDeviceData(String graph,Model data){
			return addToGraph(data,graph);
			
			
		}
	
	 
	 
	 public Model getIndependentModel(String graph){
		 Model m=null;
		 try{
	
		 dataset.begin(ReadWrite.READ);
		 if(dataset.containsNamedModel(graph)){
			 System.out.println("Found GRAPH creating temp Model.."+graph);
			m= dataset.getNamedModel(graph).difference(ModelFactory.createDefaultModel());
			m.setNsPrefixes(ModelController.prefixes);
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
	 
	 

