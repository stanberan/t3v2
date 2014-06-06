package uk.ac.abdn.t3.t3v2.services;

import org.topbraid.spin.system.SPINModuleRegistry;
import org.topbraid.spin.vocabulary.SPIN;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;

import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.Repository;
import uk.ac.abdn.t3.t3v2.models.ModelController;

public class InferenceService {

	Repository TDB=Repository.getSingleton();
	static{
		
	
		
	}
	
	public void inferCapabilities(String id  ){
			SPINModuleRegistry.get().init();
		//SPINModuleRegistry.get().registerAll(RULES_M, null);
			Model m=	getAllForDevice(id);
			System.setProperty("http.proxyHost", "proxy.abdn.ac.uk");
			  System.setProperty("http.proxyPort", "8080");
	Model model=ModelFactory.createInfModel(ReasonerRegistry.getOWLReasoner(),ModelController.ALL_OM,m );
	
	model.write(System.out,"N3");
	System.err.println("Printing DIFFERENCE");
	Model diff=model.difference(m);
	diff.write(System.out,"N3");
		
		
	}
	
	
	public Model getAllForDevice(String id){
		
		Model prov=TDB.getIndependentModel(Models.graphNS+id+"/prov");
		Model data=TDB.getIndependentModel(Models.graphNS+id+"/data");
		
		prov.add(data);
		
		return prov;
		
	}
}
