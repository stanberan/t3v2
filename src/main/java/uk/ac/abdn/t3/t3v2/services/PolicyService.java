package uk.ac.abdn.t3.t3v2.services;

import java.util.Date;

import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.system.SPINModuleRegistry;

import uk.ac.abdn.t3.t3v2.DB;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class PolicyService {

	
	
	
	public static boolean checkPolicy(String deviceid, OntModel newprovenancegraph){
		
	//	Model policies=getPolicies(deviceid);
		return inferProhibitionsObligations(newprovenancegraph);
		
	}
	
	private static boolean inferProhibitionsObligations(OntModel newProvGraph){
		Model inferredTriples=ModelFactory.createDefaultModel();
		SPINModuleRegistry.get().reset();
			System.out.println("Entered Inference:"+newProvGraph.size());
			System.out.println("Initializing Registry..."+new Date().toString());
			SPINModuleRegistry.get().init();
			System.out.println("Registering rules..."+new Date().toString());
			SPINModuleRegistry.get().registerAll(getPolicies(), null);
			System.out.println("Running inferences..."+new Date().toString());
			long start=System.currentTimeMillis();
			SPINInferences.run(newProvGraph, inferredTriples, null, null,true, null);
			long finish=System.currentTimeMillis();
			System.out.println("POLICY CHECK SIZE"+inferredTriples.size());
			System.out.println("Done Running Inference..."+new Date().toString());
			
			System.out.println("Exited Inference");
			if(inferredTriples.size()>0){
				inferredTriples.write(System.out,"TTL");
				return true;
			}
			return false;
		//	DB.getDB().trackInference(finish-start,devid,rules.size(),inferedCapabilities.size());
		
		
		
	}
	
	
	private static  OntModel getPolicies(){
		
		Model m=ModelFactory.createDefaultModel();
		m.read("http://t3.abdn.ac.uk/policies/t3policy.rdf",null,"TTL");
		OntModel ontM=ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RDFS_INF, m);
		
		
		return ontM;
	}
	
	
}
