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
	
	
	public ArrayList<Capability> getCapabilitiesArray(OntModel ontDeviceModel,Model currentInfferedCapabilities){
		infService=InferenceService.getService();
		if(currentInfferedCapabilities==null){
			System.out.println("!!!!!!!!!!!!!Current inferred capabilities is NULL!!!!!!!!!!!!!");
			currentInfferedCapabilities=ModelFactory.createDefaultModel();
		}
		ontDeviceModel.addSubModel(currentInfferedCapabilities);
		
		ArrayList<PersonalDataGeneration> generation= infService.getPersonalDataCap(ontDeviceModel);
		ArrayList<PersonalDataCollection> collection= infService.getPersonalDataCollection(ontDeviceModel);
		ArrayList<PersonalDataSharing> consumption=infService.getPersonalDataSharing(ontDeviceModel);
		ArrayList<BillingCap> billing=infService.getBillingCap(ontDeviceModel);
		ArrayList<PersonalDataUsage> usage=infService.getPersonalDataUsage(ontDeviceModel);
		
		ontDeviceModel.removeSubModel(currentInfferedCapabilities);
		ArrayList<Capability> capabilities=new ArrayList<Capability>();
		
		capabilities.addAll(generation);
		System.out.println("GENERATION"+capabilities.toString());
		capabilities.addAll(collection);
		capabilities.addAll(consumption);
		capabilities.addAll(usage);
		capabilities.addAll(billing);
		
		return capabilities;
		
	}
	
		public Capability containsCap(ArrayList<Capability>cap, Capability c){
			for(int i=0;i<cap.size();i++){
			 if(cap.get(i).getType().equals(c.getType()) && cap.get(i).getCompany_logo().equals(c.getCompany_logo())){
				 return cap.get(i);
			 }
			}
			return null;
		}
	
	  public ArrayList<Capability> getHeaders(ArrayList<Capability>cap){
	
	    	 ArrayList<Capability> headers=new ArrayList<Capability>();
	    	 if(headers.size()==0 &&cap.size()!=0){
    			 headers.add(cap.get(0));
    		 }
	    	 for(int i=0; i<cap.size();i++){
	    		Capability c=cap.get(i);
	    			Capability found=containsCap(headers,c);
	    			if(found==null){
	    				headers.add(c);
	    				}
	    			}
	    	     	 
	    	 if(headers.size() != 0){
	    		 return headers;
	    	 }
	    	 return headers;
	  }
	    		 

	
	
	
}
