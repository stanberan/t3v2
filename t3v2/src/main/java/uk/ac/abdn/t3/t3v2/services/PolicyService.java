package uk.ac.abdn.t3.t3v2.services;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class PolicyService {

	
	
	
	public static boolean checkPolicy(String deviceid, Model newprovenancegraph){
		
		Model policies=getPolicies(deviceid);
		
		
		
		
		
		return true;
	}
	
	
	private Model getPolicies(String deviceid){
		
		Model m=ModelFactory.createDefaultModel();
		m.read("http://t3.abdn.ac.uk/policies/policy.rdf",null,"TTL");
		
		
		
		return null;
	}
	
	
}
