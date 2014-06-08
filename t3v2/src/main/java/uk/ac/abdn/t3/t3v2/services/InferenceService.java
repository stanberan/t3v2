package uk.ac.abdn.t3.t3v2.services;

import java.util.ArrayList;

import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.JenaUtil;
import org.topbraid.spin.vocabulary.SPIN;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.ParameterizedSparqlString;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.capabilities.BillingCap;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataCollection;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataGeneration;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataSharing;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataUsage;
import uk.ac.abdn.t3.t3v2.models.ModelController;

public class InferenceService {

	//Repository TDB=Repository.getSingleton();
	static{
		SPINModuleRegistry.get().init();
	
		
	}
	
	
	

	//OntModel provenanceModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, "http://www.w3.org/ns/prov");


	
	
	public OntModel inferCapabilities(String id  ){
		  OntModel tttOnt=ModelController.getT3Ont();
		  OntModel instanceModel;
		   Model inference=ModelFactory.createDefaultModel();
			Model deviceGraph= ModelFactory.createDefaultModel();   //getAllForDevice(id);
			deviceGraph.read("http://t3.abdn.ac.uk/ontologies/simbbox.rdf",null,"TTL");

			instanceModel=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM,deviceGraph);
			instanceModel.addSubModel(inference);
			instanceModel.addSubModel(tttOnt);//getAllForDevice(id);
		SPINModuleRegistry.get().registerAll(tttOnt, null);
		 
		SPINInferences.run(instanceModel, inference, null, null,true, null);  
	   
		inference.write(System.out,"TTL");
	
	//save temporarily in dataset?
		
		//construct Object and compare. 
		return instanceModel;
		
	}
	
	

	public ArrayList<PersonalDataGeneration> getPersonalDataCap(OntModel instance){
		ArrayList<PersonalDataGeneration> pdgs=new ArrayList<PersonalDataGeneration>();
		ParameterizedSparqlString query=new ParameterizedSparqlString();
	query.setCommandText( ""
				+ "SELECT ?company_uri ?data_uri ?data_desc ?t3desc "
				+ "WHERE {"
				+ "?pdc a ttt:PersonalDataGeneration . "
				+ "?pdc ttt:generates ?data_uri . "
				+ "	?data_uri ttt:description ?data_desc . "
				+ " ?pdc ttt:generatedBy ?company_uri ."
				+ "  ttt:PersonalDataGeneration rdfs:label ?t3desc ."
				+ "   }");

		 query.setNsPrefixes(ModelController.prefixes);
	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
		   
	ResultSet rs = qExec.execSelect() ;
		     try {
		        while(rs.hasNext()){
		        	PersonalDataGeneration pdg=new PersonalDataGeneration();
		        	QuerySolution cap=rs.next();
		        pdg.setCompany_uri(cap.get("company_uri").asResource().getURI());
		        pdg.setData_uri(cap.get("data_uri").asResource().getURI());
		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	   
		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());	 
		        	pdgs.add(pdg);
		        }
		        return pdgs; }
		        catch(Exception e){
		        	e.printStackTrace();
		        	return null;
		        }		     
		      finally { qExec.close(); }

		     
	
			
		
	}
	
	public ArrayList<PersonalDataSharing> getPersonalDataSharing(OntModel instance){
		ArrayList<PersonalDataSharing> pdgs=new ArrayList<PersonalDataSharing>();
		ParameterizedSparqlString query=new ParameterizedSparqlString();
	query.setCommandText( ""
				+ "SELECT ?data_desc ?t3desc ?data_uri ?consumer_uri ?provider_uri ?purpose "
				+ "WHERE {"
				+ "?pdc a ttt:PersonalDataSharing . "
				+ "?pdc ttt:consumes ?data_desc . "
			//TODO: FIX QUERY	+ "	?data_uri ttt:description ?data_desc . "
				+ " ?pdc ttt:provider ?provider_uri ."
				+ " ?pdc ttt:consumer ?consumer_uri ."
				+ " ?pdc ttt:purpose ?purpose ."
				+ "  ttt:PersonalDataSharing rdfs:label ?t3desc ."
				+ "   }");

		 query.setNsPrefixes(ModelController.prefixes);
	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
		   
	ResultSet rs = qExec.execSelect() ;
		     try {
		        while(rs.hasNext()){
		        	PersonalDataSharing pdg=new PersonalDataSharing();
		        	QuerySolution cap=rs.next();
		        pdg.setConsumer_uri(cap.get("consumer_uri").asResource().getURI());
		        pdg.setProducer_uri(cap.get("provider_uri").asResource().getURI());
		      //  pdg.setData_uri(cap.get("data_uri").asResource().getURI());     
		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	
		        pdg.setPurpose(cap.get("purpose").asLiteral().getString());	
		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());
		        	pdgs.add(pdg);
		        }
		        return pdgs; }
		        catch(Exception e){
		        	e.printStackTrace();
		        	return null;
		        }		     
		      finally { qExec.close(); }

	}
		     public ArrayList<PersonalDataCollection> getPersonalDataCollection(OntModel instance){
		 		ArrayList<PersonalDataCollection> pdgs=new ArrayList<PersonalDataCollection>();
		 		ParameterizedSparqlString query=new ParameterizedSparqlString();
		 	query.setCommandText( ""
		 				+ "SELECT ?data_desc ?t3desc ?data_uri ?consumer_uri ?provider_uri "
		 				+ "WHERE {"
		 				+ "?pdc a ttt:PersonalDataCollection . "
		 				+ "?pdc ttt:consumes ?data_uri. "
		 		    	+ "	?data_uri ttt:description ?data_desc . "
		 				+ " ?pdc ttt:provider ?provider_uri ."
		 				+ " ?pdc ttt:consumer ?consumer_uri ."
		 				+ "  ttt:PersonalDataCollection rdfs:label ?t3desc ."
		 				+ "   }");

		 		 query.setNsPrefixes(ModelController.prefixes);
		 	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
		 		   
		 	ResultSet rs = qExec.execSelect() ;
		 		     try {
		 		        while(rs.hasNext()){
		 		        	PersonalDataCollection pdg=new PersonalDataCollection();
		 		        	QuerySolution cap=rs.next();
		 		        pdg.setConsumer_uri(cap.get("consumer_uri").asResource().getURI());
		 		        pdg.setProducer_uri(cap.get("provider_uri").asResource().getURI());
		 		        pdg.setData_uri(cap.get("data_uri").asResource().getURI());     
		 		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	
		 		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());
		 		        	pdgs.add(pdg);
		 		        }
		 		        return pdgs; }
		 		        catch(Exception e){
		 		        	e.printStackTrace();
		 		        	return null;
		 		        }		     
		 		      finally { qExec.close(); }

			
		
	}
		     public ArrayList<BillingCap> getBillingCap(OntModel instance){
			 		ArrayList<BillingCap> pdgs=new ArrayList<BillingCap>();
			 		ParameterizedSparqlString query=new ParameterizedSparqlString();
			 	query.setCommandText( ""
			 				+ "SELECT ?data_desc ?t3desc ?data_uri ?provider_uri "
			 				+ "WHERE {"
			 				+ "?pdc a ttt:BillingCap. "
			 				+ "?pdc ttt:consumes ?data_uri. "
			 		    	+ "	?data_uri ttt:description ?data_desc . "
			 				+ " ?pdc ttt:provider ?provider_uri ."
			 				+ " ?pdc ttt:consumer ?consumer_uri ."
			 				+ "  ttt:PersonalDataCollection rdfs:label ?t3desc ."
			 				+ "   }");

			 		 query.setNsPrefixes(ModelController.prefixes);
			 	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
			 		   
			 	ResultSet rs = qExec.execSelect() ;
			 		     try {
			 		        while(rs.hasNext()){
			 		        	BillingCap pdg=new BillingCap();
			 		        	QuerySolution cap=rs.next();
			 		     
			 		        pdg.setProducer_uri(cap.get("provider_uri").asResource().getURI());
			 		        pdg.setData_uri(cap.get("data_uri").asResource().getURI());     
			 		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	
			 		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());
			 		        	pdgs.add(pdg);
			 		        }
			 		        return pdgs; }
			 		        catch(Exception e){
			 		        	e.printStackTrace();
			 		        	return null;
			 		        }		     
			 		      finally { qExec.close(); }

				
			
		}
		     public ArrayList<PersonalDataUsage> getPersonalDataUsage(OntModel instance){
			 		ArrayList<PersonalDataUsage> pdgs=new ArrayList<PersonalDataUsage>();
			 		ParameterizedSparqlString query=new ParameterizedSparqlString();
			 	query.setCommandText( ""
			 				+ "SELECT ?purpose ?data_desc ?t3desc ?data_uri ?consumer_uri ?provider_uri "
			 				+ "WHERE {"
			 				+ "?pdc a ttt:PersonalDataUsage . "
			 				+ "?pdc ttt:consumes ?data_uri. "
			 		    	+ "	?data_uri ttt:description ?data_desc . "
			 				+ " ?pdc ttt:producer ?provider_uri ."
			 				+ " ?pdc ttt:consumer ?consumer_uri ."
			 				+ " ?pdc ttt:purpose ?purpose ."
			 				+ "  ttt:PersonalDataUsage rdfs:label ?t3desc ."
			 				+ "   }");

			 		 query.setNsPrefixes(ModelController.prefixes);
			 	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
			 		   
			 	ResultSet rs = qExec.execSelect() ;
			 		     try {
			 		        while(rs.hasNext()){
			 		        	PersonalDataUsage pdg=new PersonalDataUsage();
			 		        	QuerySolution cap=rs.next();
			 		        pdg.setConsumer_uri(cap.get("consumer_uri").asResource().getURI());
			 		        pdg.setProducer_uri(cap.get("provider_uri").asResource().getURI());
			 		        pdg.setData_uri(cap.get("data_uri").asResource().getURI());     
			 		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	
			 		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());
			 		       pdg.setPurpose(cap.get("t3desc").asLiteral().getString());
			 		        	pdgs.add(pdg);
			 		        }
			 		        return pdgs; }
			 		        catch(Exception e){
			 		        	e.printStackTrace();
			 		        	return null;
			 		        }		     
			 		      finally { qExec.close(); }

				
			
		}
	
	public Model getAllForDevice(String id){
		
		Model prov=null;//TDB.getIndependentModel(Models.graphNS+id+"/prov");
		Model data=null;//TDB.getIndependentModel(Models.graphNS+id+"/data");
	
	prov.add(data);
		
		return prov;
		
	}
	
	public static void main(String[] args){
		InferenceService inf=new InferenceService();
		OntModel d=inf.inferCapabilities("simbbox001");
	ArrayList<PersonalDataGeneration> arra=inf.getPersonalDataCap(d);
	
	ArrayList<PersonalDataSharing> arra1=inf.getPersonalDataSharing(d);
	ArrayList<PersonalDataCollection> arra2=inf.getPersonalDataCollection(d);
	ArrayList<PersonalDataUsage> arra3=inf.getPersonalDataUsage(d);
	ArrayList<BillingCap> arra4=inf.getBillingCap(d);
	System.out.println(arra.toString());
	System.out.println(arra1.toString());
	System.out.println(arra2.toString());
	System.out.println(arra3.toString());
	System.out.println(arra4.toString());

	
	}
	
	
}
