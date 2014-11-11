package uk.ac.abdn.t3.t3v2.services;

import java.util.Date;

import org.topbraid.spin.inference.SPINInferences;
import org.topbraid.spin.system.SPINModuleRegistry;

import uk.ac.abdn.t3.t3v2.DB;
import uk.ac.abdn.t3.t3v2.models.ModelController;

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
	Model policies=ModelController.getPolicy();
		newProvGraph.addSubModel(policies);
			System.out.println("Entered Inference:"+newProvGraph.size());
			System.out.println("Initializing Registry..."+new Date().toString());
			SPINModuleRegistry.get().init();
			System.out.println("Registering rules..."+new Date().toString());
			SPINModuleRegistry.get().registerAll(newProvGraph, null);
			System.out.println("Running inferences..."+new Date().toString());
			long start=System.currentTimeMillis();
			SPINInferences.run(newProvGraph, inferredTriples, null, null,true, null);
			//remove policy
			newProvGraph.remove(policies);
			long finish=System.currentTimeMillis();
			System.out.println("POLICY CHECK SIZE"+inferredTriples.size());
			System.out.println("Done Running Inference..."+new Date().toString());
			
			System.out.println("Exited Inference");
			if(inferredTriples.size()>0){
				inferredTriples.write(System.out,"TTL");
				return true;
			}
			return false;
		//	DB.getDB().trackPolicy(finish-start,devid,rules.size(),inferedCapabilities.size());
		
		
		
	}
	
	
	
	
	
}
