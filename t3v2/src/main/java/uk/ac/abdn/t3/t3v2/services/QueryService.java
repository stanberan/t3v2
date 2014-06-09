package uk.ac.abdn.t3.t3v2.services;

import uk.ac.abdn.t3.t3v2.pojo.DeviceDescription;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class QueryService {

	static InferenceService infService=InferenceService.getService();
	private static QueryService queryService=null;
	
	
	public static QueryService getSingleton(){
		if(queryService ==null){
			queryService=new QueryService();
		}
		return queryService;
		
		
	}
	public DeviceDescription getDeviceDescription(String devid){
		infService=InferenceService.getService();	
		OntModel mainDeviceModel=infService.getDeviceOntModel(devid);	
		DeviceDescription description=infService.getDeviceGeneralData(mainDeviceModel);
		
		return description;
		
	}
	
	
	public void getCapabilitiesStaff(String devid,String userid){
		infService=InferenceService.getService();
		
		OntModel mainDeviceModel=infService.getDeviceOntModel(devid);
		Model currentInferredCapabilities=ModelFactory.createDefaultModel();
		infService.inferCapabilities(mainDeviceModel, currentInferredCapabilities);
		System.out.println("CURRENT INFERRED CAPABILITIES FROM LOOP");
		currentInferredCapabilities.write(System.out, "TTL");
		
		
		
		
	}
	
	
	
}
