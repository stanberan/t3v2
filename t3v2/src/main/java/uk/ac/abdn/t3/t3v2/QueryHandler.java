package uk.ac.abdn.t3.t3v2;



import org.topbraid.spin.util.JenaUtil;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class QueryHandler {
public static String busgraph="http://t3.abdn.ac.uk:8080/devices/MD5Hash";
static Repository TDB=Repository.getSingleton();


	public static boolean busExists(String graph,String id){

	Model m=TDB.getIndependentModel(graph);
	OntModel ont=JenaUtil.createOntologyModel(OntModelSpec.OWL_MEM,m);	
		Individual tag =ont.getIndividual(Models.INSTANCE_NS+id);		
			if(tag!=null){
				return true;
			}
			return false;
		}
	
	
	public static boolean exists(String graph,String id){

		Model m=TDB.getIndependentModel(graph);
		OntModel ont=JenaUtil.createOntologyModel(OntModelSpec.OWL_MEM,m);	
			Individual tag =ont.getIndividual(Models.graphNS+id);		
				if(tag!=null){
					return true;
				}
				return false;
			}
	
	
	
	public static boolean registerBustStopTag(String identifier,String busURL){

Model graph=ModelFactory.createDefaultModel();

	Resource tag = graph.createResource(busgraph+"/"+identifier);
		Property identifies = ResourceFactory.createProperty(Models.IOTA_NS, "identifies");
		Resource md5= graph.createResource(busgraph+"/"+"MD5Hash");
		//beta 2.2
		Resource busUrl= graph.createResource(busURL);
		Property hasURL= ResourceFactory.createProperty(Models.TTT_NS, "hasURL");
			
		tag.addProperty(identifies, md5);
		tag.addProperty(hasURL, busUrl);
		
		
		boolean b= TDB.addToGraph(graph, busgraph);
		TDB.getIndependentModel(busgraph).write(System.out,"TURTLE");
		return b;
		
	
	}
	
}
