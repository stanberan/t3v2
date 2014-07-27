package uk.ac.abdn.t3.t3v2;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.abdn.t3.t3v2.capabilities.Capability;
import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.services.InferenceService;
import uk.ac.abdn.t3.t3v2.services.QueryService;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class CapabilityMatchingService {

	
	
	static QueryService queryService=QueryService.getSingleton();
	static InferenceService inferenceService=InferenceService.getService();
	
	
	
	
	
	public synchronized static JSONObject capabilityMatch(String devid, String uid){

		OntModel mainModel=inferenceService.getDeviceOntModel(devid);
		//placeholder for
	
		Model currentCap=ModelFactory.createDefaultModel();	
		inferenceService.inferCapabilities(mainModel, currentCap);

		
		
		
		System.out.println("GETTING CAPABILITIES");
		//geet realtime cap of device
		ArrayList<Capability>currentCapabilitiesArray=queryService.getCapabilitiesArray(mainModel,currentCap);
	   System.out.println("XXXXXXXXXXXXXXXXCURRENT CAPABILITIESXXXXXXXXXXXXXX");
		currentCap.write(System.out,"TTL");
		//add it to the list of temporary capabilities for the user and device
		inferenceService.changeTemporaryCap(currentCap, ModelController.TTT_GRAPH+devid+uid+"/temp");
		//get accepted capabilities of user for the specific iot device.
		Model acceptedCap=inferenceService.getAcceptedCapabilities(uid,devid);
		   System.out.println("XXXXXXXXXXXXXXXXACCEPTEDXXXXXXXXXXXXXX");
			if(acceptedCap!=null){
		   acceptedCap.write(System.out,"TTL");
			}
	//	boolean different=inferenceService.compareCapabilities(acceptedCap, currentCap);
		//getAcceptedCapabilities of the Device
		
		
		//check if the capabilities are different in the graph level. (is isomorphic)
	
		//for interface
	//CHANGE old
			ArrayList<Capability>acceptedCapabilityArray=queryService.getCapabilitiesArray(mainModel,acceptedCap);
			
	//HEADERS
			ArrayList<Capability>currentHeaders=queryService.getHeaders(currentCapabilitiesArray);
			System.out.println("HEADERS CURRENT"+currentHeaders.size()+currentHeaders.toString());
			ArrayList<Capability>acceptedHeaders=queryService.getHeaders(acceptedCapabilityArray);
			System.out.println("HEADERS CURRENT"+acceptedHeaders.size()+acceptedHeaders.toString());
		
			JSONArray h=new JSONArray();
			for(Capability c: currentHeaders){
				h.put(new JSONObject(c.toJson()));
			}
			JSONObject j= getNewCapabilities(currentCapabilitiesArray,acceptedCapabilityArray);
			j.put("headers", h);
			return j;
	//	return getNewCapabilities(currentCapabilitiesArray,acceptedCapabilityArray);
		
			
		}
		
	
		
	

	
	
	//compareMethod
		
		

	
	private static JSONObject getNewCapabilities(ArrayList<Capability>currentCap,ArrayList<Capability> acceptedCap){
		JSONObject jsondata=new JSONObject();
		ArrayList<Capability> newCapabilities=new ArrayList<Capability>();
		ArrayList<Capability> oldCapabilities=new ArrayList<Capability>();
		JSONArray currentCapJson=new JSONArray();
		
		for (Capability current: currentCap){
			currentCapJson.put(new JSONObject(current.toJson()));
			if(!acceptedCap.contains(current)){
				
				newCapabilities.add(current);
System.out.println("New capability detected"+current.toJson());
			}

		}
		
		
		jsondata.put("currentCapabilities", currentCapJson);
		
		if(newCapabilities.size()==0){
			jsondata.put("different", false);
			return jsondata;
		}
		else{
		
		
		JSONArray newCapJson=new JSONArray();
		
		
		for(Capability c:newCapabilities){
		newCapJson.put(new JSONObject(c.toJson()));
		}

		jsondata.put("newcapabilities", newCapJson);

		}
		
		
		return jsondata;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
