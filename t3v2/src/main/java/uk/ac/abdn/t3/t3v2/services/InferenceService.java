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
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.tdb.TDB;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.capabilities.BillingCap;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataCollection;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataGeneration;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataSharing;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataUsage;
import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.pojo.Company;
import uk.ac.abdn.t3.t3v2.pojo.DeviceDescription;
import uk.ac.abdn.t3.t3v2.pojo.PersonalData;

public class InferenceService {

	Repository TDB=Repository.getSingleton();
	static InferenceService infservice=null;
	static{
		SPINModuleRegistry.get().init();
	
		
	}
	
	public static InferenceService getService(){
		if(infservice==null){
			infservice=new InferenceService();
		}
		return infservice;
	}
	

	//OntModel provenanceModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, "http://www.w3.org/ns/prov");


	/*
	
	public OntModel inferCapabilities(String devid, String userid){
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
	//save temp
	TDB.addToGraph(inference, Models.graphNS+userid+devid+"/cap");
	
		
		//construct Object and compare. 
		return instanceModel;
		
	}*/
	
	
	//infer plus comopare
	public OntModel getDeviceOntModel(String devid){
	
		String deviceProvenanceGraph=ModelController.TTT_GRAPH+devid+"/prov";
		String deviceDescriptionGraph=ModelController.TTT_GRAPH+devid+"/data";
		
		Model baseModel=getBaseDeviceModel(deviceProvenanceGraph,deviceDescriptionGraph);
		OntModel deviceOntModel=getDeviceOntModel(baseModel);
		return deviceOntModel;
		
		
	}
	
	public OntModel getDeviceOntModel(Model baseModel){
		OntModel TTT=ModelController.getT3Ont();
		
		OntModel ontDeviceModel=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, baseModel);
		System.out.println("BEFORE");
		ontDeviceModel.write(System.out, "TTL");
		ontDeviceModel.addSubModel(TTT);
		System.out.println("AFTER");
		ontDeviceModel.write(System.out, "TTL");
	
		return ontDeviceModel;
				

		
		
	}
	public Model getAcceptedCapabilities(String acceptedGraph){	
		Model m=TDB.getIndependentModel(acceptedGraph);
		System.out.print("Retrieving Accepted cap from Graph "+acceptedGraph);
		return m;
		
	}
	public void inferCapabilities(OntModel deviceOntModel, Model inferedCapabilities){

			deviceOntModel.addSubModel(inferedCapabilities);
		SPINModuleRegistry.get().registerAll(ModelController.getT3Ont(), null);
		 
		SPINInferences.run(deviceOntModel, inferedCapabilities, null, null,true, null);  
	 
	//TDB.addToGraph(inference, Models.graphNS+"usersimbbox001/"+type);
	
		
	}
	public void changeCapabilities(Model cap,String graph){
		TDB.removeNamedGraph(graph);
		TDB.addToGraph(cap, graph);
		System.out.println("Capabilities removed and updated!"+graph);
	}
	
	//currentGraph different?
	
	public boolean compareCapabilities (String acceptedGraph,Model currentModel){
	
		System.out.println("________CURRENTT_______");
		currentModel.write(System.out,"TTL");
		if(currentModel!=null){			
			Model acceptedCap=TDB.getIndependentModel(acceptedGraph);
			if(acceptedCap==null){
				System.out.println("ACCEPTED NULL");
				acceptedCap=ModelFactory.createDefaultModel();
				return true;
			}
		
			boolean iso=acceptedCap.isIsomorphicWith(currentModel);
			
			  Model difference=currentModel.difference(acceptedCap);
				
			    if(!difference.isEmpty()){ 
			    
			    System.out.println("Difference");
			    difference.write(System.out,"TTL");
			    //compute cap and send notification to user. 
			
			    }
			
			if(iso){
				System.out.println("XXXXXXXXXXXXXXIDENTICALXXXXXXXXXXXXXXX");
				return false;
			}
			else{
				System.out.println("XXXXXXXXX NEW TRIPLESXXXXXXXXXX");
				return true;
			}
			
		  
		   
		}
		return true;
		 
	}
	public ArrayList<PersonalData> filterPD(ArrayList<PersonalData> source){
		ArrayList<PersonalData> data=new ArrayList<PersonalData>();
		for(PersonalData d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	public ArrayList<PersonalDataCollection> filterPDC(ArrayList<PersonalDataCollection> source){
		ArrayList<PersonalDataCollection> data=new ArrayList<PersonalDataCollection>();
		for(PersonalDataCollection d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	public ArrayList<BillingCap> filterBil(ArrayList<BillingCap> source){
		ArrayList<BillingCap> data=new ArrayList<BillingCap>();
		for(BillingCap d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	public ArrayList<PersonalDataGeneration> filterPDG(ArrayList<PersonalDataGeneration> source){
		ArrayList<PersonalDataGeneration> data=new ArrayList<PersonalDataGeneration>();
		for(PersonalDataGeneration d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	public ArrayList<PersonalDataSharing> filterPDSh(ArrayList<PersonalDataSharing> source){
		ArrayList<PersonalDataSharing> data=new ArrayList<PersonalDataSharing>();
		for(PersonalDataSharing d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	public ArrayList<PersonalDataUsage> filterPDU(ArrayList<PersonalDataUsage> source){
		ArrayList<PersonalDataUsage> data=new ArrayList<PersonalDataUsage>();
		for(PersonalDataUsage d: source){
			if(!data.contains(d)){
				data.add(d);
			}			
		}
		return data;	
		
	}
	
	

	public PersonalData getPersonalData(String uri,Model basedevm){
	//	ArrayList<PersonalData>data =new ArrayList<PersonalData>();
	 	PersonalData d=new PersonalData();
				ParameterizedSparqlString query=new ParameterizedSparqlString();
				query.setCommandText( ""
							+ "SELECT ?uri ?desc "
							+ "WHERE {"
							+ "?uri a ttt:PersonalData . "
							+ "?uri ttt:description ?desc . "				
							+ "}");
					
					 query.setNsPrefixes(ModelController.prefixes);
					query.setIri("uri", uri);
				QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),basedevm);
				ResultSet rs=qExec.execSelect();
				try {
			        if(rs.hasNext()){
			  
			        	QuerySolution q=rs.next();
			        	d.setDescription(q.getLiteral("desc").getString());
			        	d.setUri(q.getResource("uri").getURI());
			        
			      
			        }
			        return d; }
			        catch(Exception e){
			        	e.printStackTrace();
			        	return null;
			        }		     
			      finally { qExec.close(); }
				
			}	
	public ArrayList<PersonalData> getPersonalData(Model basedevm){
		ArrayList<PersonalData>data =new ArrayList<PersonalData>();
				ParameterizedSparqlString query=new ParameterizedSparqlString();
				query.setCommandText( ""
							+ "SELECT ?uri ?desc "
							+ "WHERE {"
							+ "?uri a ttt:PersonalData . "
							+ "?uri ttt:description ?desc . "				
							+ "}");
					
					 query.setNsPrefixes(ModelController.prefixes);
				//	query.setIri("uri", uri);
				QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),basedevm);
				ResultSet rs=qExec.execSelect();
				try {
			        while(rs.hasNext()){
			        	PersonalData d=new PersonalData();
			        	QuerySolution q=rs.next();
			        	d.setDescription(q.getLiteral("desc").getString());
			        	d.setUri(q.getResource("uri").getURI());
			        
			        	
			        	data.add(d);
			      
			        }
			        
			        return filterPD(data); }
			        catch(Exception e){
			        	e.printStackTrace();
			        	return null;
			        }		     
			      finally { qExec.close(); }
				
			}	

	public ArrayList<Company> getCompanies(Model basedevm){
ArrayList<Company>companies =new ArrayList<Company>();
		ParameterizedSparqlString query=new ParameterizedSparqlString();
		query.setCommandText( ""
					+ "SELECT ?uri ?logo ?name ?tel ?url ?email ?address "
					+ "WHERE {"
					+ "?uri a foaf:Organization . "
					+ "OPTIONAL{?uri foaf:name ?name .} "
					+ "	OPTIONAL {?uri ns:hasAddress ?address . }"
					+ " OPTIONAL {?uri foaf:phone ?tel .}"
					+ " OPTIONAL {?uri foaf:homepage ?url .}"
					+ " OPTIONAL {?uri foaf:mbox ?email . }"
					+ " OPTIONAL {?uri foaf:logo ?logo . }"				
					+ "}");
			
			 query.setNsPrefixes(ModelController.prefixes);
			
		QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),basedevm);
		ResultSet rs=qExec.execSelect();
		try {
	        while(rs.hasNext()){
	        	Company c=new Company();
	        	QuerySolution q=rs.next();
	        	c.setAddress(q.getLiteral("address").getString());
	        	c.setEmail(q.getResource("email").getURI());
	        	c.setTel(q.getLiteral("tel").getString());
	        	c.setLogo(q.getResource("logo").getURI());
	        	c.setUri(q.getResource("uri").getURI());
	        	
	        	companies.add(c);
	      
	        }
	        return companies; }
	        catch(Exception e){
	        	e.printStackTrace();
	        	Company c=new Company();
	        	c.setAddress("OOPS:"+e.getMessage());
	        	companies.add(c);
	        	return companies;
	        }		     
	      finally { qExec.close(); }
		
	}	
	
	
	public Company getCompany(String uri, Model basedevm){
	Company c=new Company();
		ParameterizedSparqlString query=new ParameterizedSparqlString();
		query.setCommandText( ""
					+ "SELECT ?logo ?name ?tel ?url ?email ?address "
					+ "WHERE {"
					+ "?uri a foaf:Organization . "
					+ "?uri foaf:name ?name . "
					+ "	?uri ns:hasAddress ?address . "
					+ " ?uri foaf:phone ?tel ."
					+ " ?uri foaf:homepage ?url ."
					+ " ?uri foaf:mbox ?email ."				
					+ "}");
			
			 query.setNsPrefixes(ModelController.prefixes);
			 query.setIri("uri", uri);
		QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),basedevm);
		ResultSet rs=qExec.execSelect();
		try {
	        if(rs.hasNext()){
	        	QuerySolution q=rs.next();
	        	c.setAddress(q.getLiteral("address").getString());
	        	c.setEmail(q.getResource("email").getURI());
	        	c.setTel(q.getLiteral("tel").getString());
	        	c.setLogo(q.getResource("logo").getURI());
	        	c.setUri(uri);
	      
	        }
	        return c; }
	        catch(Exception e){
	        	e.printStackTrace();
	        	return null;
	        }		     
	      finally { qExec.close(); }
		
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
		        return filterPDG(pdgs); }
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
				+ "?pdc ttt:consumes ?data_uri . "
			    +"?data_uri ttt:description ?data_desc . "
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
		       pdg.setData_uri(cap.get("data_uri").asResource().getURI());     
		        pdg.setData_desc(cap.get("data_desc").asLiteral().getString());	
		        pdg.setPurpose(cap.get("purpose").asLiteral().getString());	
		        pdg.setDev_id(cap.get("t3desc").asLiteral().getString());
		        	pdgs.add(pdg);
		        }
		        return filterPDSh(pdgs); }
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
		 		        return filterPDC(pdgs); }
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
			 				+ "  ttt:BillingCap rdfs:label ?t3desc ."
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
			 		        return filterBil(pdgs); }
			 		        catch(Exception e){
			 		        	e.printStackTrace();
			 		        	return null;
			 		        }		     
			 		      finally { qExec.close(); }

				
			
		}
		     public DeviceDescription getDeviceGeneralData(OntModel instance){
		    	 
		    	 ParameterizedSparqlString query=new ParameterizedSparqlString();
				 	query.setCommandText( ""
				 				+ "SELECT ?iotdev ?device_name ?manufacturer ?owner ?securityDescription ?deviceDescription ?logo ?typeDescription "
				 				+ "WHERE {"
				 				+ "?iotdev a ttt:TelematicsDevice .  "
				 				+ "?iotdev foaf:name ?device_name . "
				 		    	+ "?iotdev ttt:manufacturer ?manufacturer. "
				 				+ " ?iotdev ttt:owner ?owner. "
				 				+ " ?iotdev ttt:securityDescription ?securityDescription . "
				 				+ " ?iotdev ttt:deviceDescription ?deviceDescription . "
				 				+ " ?iotdev ttt:typeDescription ?typeDescription . "
				 				+ " ?iotdev ttt:pictureURL ?logo . "
				 				+ "   }");

				 		 query.setNsPrefixes(ModelController.prefixes);
				 	
				 	QueryExecution qExec=QueryExecutionFactory.create(query.asQuery(),instance);	
		    	 
				 	ResultSet rs = qExec.execSelect() ;
		 		     try {
		 		    	 System.out.println("Has NEXT?...");
		 		        if(rs.hasNext()){
		 		        	 System.out.print("true");
		 		        	DeviceDescription d=new DeviceDescription();
		 		        	QuerySolution sol=rs.next();
		 		       d.setDev_uri(sol.get("iotdev").asResource().getURI());
		 		       d.setDeviceDescription(sol.get("deviceDescription").toString());
		 		       d.setSecurityDescription(sol.get("securityDescription").toString());
		 		       d.setTypeDescription(sol.get("typeDescription").toString());
		 		       d.setLogo(sol.get("logo").toString());
		 		       d.setManufacturer(sol.get("manufacturer").asResource().getURI());
		 		      d.setOwner(sol.get("owner").asResource().getURI());
		 		      d.setName(sol.get("device_name").toString());
		 		      System.out.println(d.toJson());
		 		      return d;
		 		        }
		 		       System.out.print("false");
		 		       }
		 		        catch(Exception e){
		 		        	e.printStackTrace();
		 		        	return null;
		 		        }		     
		 		      finally { qExec.close(); }

		    	 return null;
		    	 
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
	
	public Model getBaseDeviceModel(String deviceProvGraph, String deviceDescriptionGraph ){
		
		Model prov=TDB.getIndependentModel(deviceProvGraph);
		Model data=TDB.getIndependentModel(deviceDescriptionGraph);
		Model baseDeviceModel=ModelFactory.createDefaultModel();
	if(prov!=null){
	baseDeviceModel.add(prov);
	System.out.println("Prov from:"+deviceProvGraph +" added.");
	}
	if(data!=null){
		baseDeviceModel.add(data);
		System.out.println("Description from: "+deviceDescriptionGraph +" added.");
	}
	if(!baseDeviceModel.isEmpty()){
    
		return baseDeviceModel;
	}
	return null;
		
	}
	
	public static void main(String[] args){
		InferenceService inf=new InferenceService();
	//	OntModel device=inf.getDeviceOntModel(devid, userid);
		
		Model accepted=ModelFactory.createDefaultModel();
		accepted.read("http://t3.abdn.ac.uk/ontologies/accepted.ttl",null,"TTL");
		
		Model temporary=ModelFactory.createDefaultModel();
		accepted.read("http://t3.abdn.ac.uk/ontologies/temporary.ttl",null,"TTL");
		inf.TDB.addToGraph(temporary, "http://t3.abdn.ac.uk/t3v2/1/device/usersimbbox001/cap");
		inf.TDB.addToGraph(accepted, "http://t3.abdn.ac.uk/t3v2/1/device/usersimbbox001/permcap");
		
		
		/*
		
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
*/
	
	}
	
	
	//load data
	//getInfDeviceOntModel (String devid, 
	
}
