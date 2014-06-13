package uk.ac.abdn.t3.t3v2.services;

import java.util.ArrayList;

import uk.ac.abdn.t3.t3v2.capabilities.BillingCap;
import uk.ac.abdn.t3.t3v2.capabilities.Capability;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataCollection;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataGeneration;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataSharing;
import uk.ac.abdn.t3.t3v2.capabilities.PersonalDataUsage;
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
	
	
	public ArrayList<Capability> getCapabilitiesStaff(String devid,Model currentInfferedCapabilities){
		infService=InferenceService.getService();
		
		OntModel ontDeviceModel=infService.getDeviceOntModel(devid);   //check
		ontDeviceModel.addSubModel(currentInfferedCapabilities); 
		System.out.println("ONTOLOGY BASE MODEL BEFORE CAPABILITY INFERENCE");
		ontDeviceModel.writeAll(System.out, "TTL");
		infService.inferCapabilities(ontDeviceModel, currentInfferedCapabilities);
		System.out.println("CURRENT INFERRED CAPABILITIES FROM LOOP");
		currentInfferedCapabilities.write(System.out, "TTL");
		
		ArrayList<PersonalDataGeneration> generation= infService.getPersonalDataCap(ontDeviceModel);
		ArrayList<PersonalDataCollection> collection= infService.getPersonalDataCollection(ontDeviceModel);
		ArrayList<PersonalDataSharing> consumption=infService.getPersonalDataSharing(ontDeviceModel);
		ArrayList<BillingCap> billing=infService.getBillingCap(ontDeviceModel);
		ArrayList<PersonalDataUsage> usage=infService.getPersonalDataUsage(ontDeviceModel);
		
		ArrayList<Capability> capabilities=new ArrayList<Capability>();
		
		capabilities.addAll(generation);
		capabilities.addAll(collection);
		capabilities.addAll(consumption);
		capabilities.addAll(usage);
		capabilities.addAll(generation);
		capabilities.addAll(billing);
		
		return capabilities;
		
	}
	
	
	
}
