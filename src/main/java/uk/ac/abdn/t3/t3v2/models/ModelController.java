package uk.ac.abdn.t3.t3v2.models;

import java.util.HashMap;

import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.JenaUtil;

import uk.ac.abdn.t3.t3v2.Repository;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.tdb.TDB;

public class ModelController {
	public static HashMap<String,String> prefixes=new HashMap<String,String>();
static{
	System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
	  System.setProperty("http.proxyPort", "8080");

		prefixes.put("ttt", "http://t3.abdn.ac.uk/ontologies/t3.owl#");
		prefixes.put("iota", "http://t3.abdn.ac.uk/ontologies/iota.owl#");
		prefixes.put("ns", "http://www.w3.org/2006/vcard/ns#");
		prefixes.put("prov", "http://www.w3.org/ns/prov#");
		prefixes.put("foaf", "http://xmlns.com/foaf/0.1/");
		prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		prefixes.put("xsd", "http://www.w3.org/2001/XMLSchema#");
		prefixes.put("owl","http://www.w3.org/2002/07/owl#");
}

	final static String INSTANCE="http://t3.abdn.ac.uk/ontologies/instancedata.owl";
	final static String TTT="http://t3.abdn.ac.uk/ontologies/t3v2.owl";
	final static String PROV="http://www.w3.org/ns/prov";
	final static String IOTA="http://t3.abdn.ac.uk/ontologies/iota.owl";
	final static String RULES="http://t3.abdn.ac.uk/ontologies/policy.owl";
	
	public final static String INSTANCE_NS="http://t3.abdn.ac.uk/ontologies/instancedata.owl#";
	public final static String TTT_NS="http://t3.abdn.ac.uk/ontologies/t3.owl#";
	public final static String IOTA_NS="http://t3.abdn.ac.uk/ontologies/iota.owl#";
	public final static String PROV_NS="http://www.w3.org/ns/prov#";
	public final static String TTT_GRAPH="http://t3.abdn.ac.uk/t3v2/1/device/";
	
	public static Model TTT_M=null;
	static Model PROV_M;
	static Model IOTA_M;
	static Model RULES_M;
	static Model POLICY_M;
	
	
//	public static Model ALL_OM=ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
static Repository TDB;//=Repository.getSingleton();
	
	//OntModel provenanceModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, "http://www.w3.org/ns/prov");

	
	
	
public static Model test(){

	System.out.println("Testing");
	Model m=ModelFactory.createDefaultModel();
	m.read("http://t3.abdn.ac.uk/ontologies/simbbox.rdf",null,"TTL");
	m.read("http://t3.abdn.ac.uk/ontologies/t3.owl",null,"RDF/XML");
	m.read("http://t3.abdn.ac.uk/ontologies/iota.owl",null,"RDF/XML");
	m.read(PROV);
	return m;
}


	 public static Model getRules(){
		 if(RULES_M==null){
		RULES_M= ModelFactory.createDefaultModel();
		 RULES_M.read("http://t3.abdn.ac.uk/ontologies/t3rules.ttl",null,"TTL");
		 }
		 return RULES_M;
	 }
	 public static Model getPolicy(){
		 if(POLICY_M==null){
		 POLICY_M=ModelFactory.createDefaultModel();
		 POLICY_M.read("http://t3.abdn.ac.uk/ontologies/t3pdspolrule.ttl",null,"TTL");
		 }
		 return POLICY_M;
	 }
	 
	public static Model getT3Ont(){
		System.getProperties().put("proxySet","true");
		   System.getProperties().put("proxyHost","proxy.abdn.ac.uk");
		   System.getProperties().put("proxyPort",8080);
		    if(TTT_M==null){
		    	TTT_M=ModelFactory.createDefaultModel();
		    	TTT_M.read("http://t3.abdn.ac.uk/ontologies/t3v2-plain.rdf",null,"RDF/XML"); 
		    	//TTT_M.read("http://t3.abdn.ac.uk/ontologies/iota.ttl",null,"TTL");
		    	//TTT_M.read("http://spinrdf.org/sp",null,"RDF/XML");
		    //	TTT_M.write(System.out,"TTL");
		    	//TTT_M.read(PROV);
		    }
		    System.out.println("XXXXXXXXXXXXT3V2.RDF Ont MODELXXXXXXXXXXXXXXX");
//	TTT_M.write(System.out,"TTL");
    System.out.println("XXXXXXXXXXXXEND T3V2.RDF Ont MODELXXXXXXXXXXXXXXX");
		    return TTT_M;
	 
		  
	}
	

public static void main(String[] args){
	ModelController.getT3Ont();
}
}
