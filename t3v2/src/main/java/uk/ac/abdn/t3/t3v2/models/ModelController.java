package uk.ac.abdn.t3.t3v2.models;

import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.util.JenaUtil;

import uk.ac.abdn.t3.t3v2.Repository;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ModelController {
static{
	//System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
	//  System.setProperty("http.proxyPort", "8080");
//	initialize();
}
	final static String INSTANCE="http://t3.abdn.ac.uk/ontologies/instancedata.owl";
	final static String TTT="http://t3.abdn.ac.uk/ontologies/t3.owl";
	final static String PROV="http://www.w3.org/ns/prov";
	final static String IOTA="http://t3.abdn.ac.uk/ontologies/iota.owl";
	final static String RULES="http://t3.abdn.ac.uk/ontologies/rules.owl";
	
	final static String INSTANCE_NS="http://t3.abdn.ac.uk/ontologies/instancedata.owl#";
	final static String TTT_NS="http://t3.abdn.ac.uk/ontologies/t3.owl#";
	final static String IOTA_NS="http://t3.abdn.ac.uk/ontologies/iota.owl#";
	final static String PROV_NS="http://www.w3.org/ns/prov#";
	
	public static Model TTT_M;
	static Model PROV_M;
	static Model IOTA_M;
	static Model RULES_M;
	
	public static Model ALL_OM=ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
	static Repository TDB=Repository.getSingleton();
	
	//OntModel provenanceModel = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, "http://www.w3.org/ns/prov");

public static Model test(){
	System.getProperties().put("proxySet","true");
	   System.getProperties().put("proxyHost","proxy.abdn.ac.uk");
	   System.getProperties().put("proxyPort",8080);
	System.out.println("Testing");
	Model m=ModelFactory.createDefaultModel();
	m.read("http://t3.abdn.ac.uk/ontologies/simbbox.rdf",null,"TTL");
	m.read("http://t3.abdn.ac.uk/ontologies/t3.owl",null,"RDF/XML");
	m.read("http://t3.abdn.ac.uk/ontologies/iota.owl",null,"RDF/XML");
	m.read(PROV);
	return m;
}
	public static void initialize(){
		
		        final String url = "http://www.w3.org/TR/REC-rdf-syntax/example14.nt";
		        final Model model = ModelFactory.createDefaultModel();
		        model.read(url);
		        model.write(System.out);
		    
		
				TTT_M=ModelFactory.createDefaultModel();
				PROV_M=ModelFactory.createDefaultModel();
				IOTA_M=ModelFactory.createDefaultModel();
				RULES_M=ModelFactory.createDefaultModel();
				
			TTT_M.read(url);
				System.out.println("Going to read prov");
			PROV_M.read(PROV);
			System.out.println("Red");
			IOTA_M.read(IOTA);
		//	RULES_M.read(RULES);
		  
		ALL_OM.add(TTT_M);
		ALL_OM.add(IOTA_M);
		ALL_OM.add(PROV_M);
	//	ALL_OM.addSubModel(RULES_M);
		  
		System.out.println("End of initialize...");  
		  
	}
//	SPINModuleRegistry.get().init();
//	SPINModuleRegistry.get().registerAll(RULES_M, null);
	
}
