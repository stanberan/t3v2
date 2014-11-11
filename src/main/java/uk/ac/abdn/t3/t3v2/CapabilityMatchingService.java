package uk.ac.abdn.t3.t3v2;

import java.util.ArrayList;
import java.util.HashMap;

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
		Model baseModel=inferenceService.getBaseDeviceModel(devid);
	mainModel.write(System.out,"TTL");
		Model currentCap=ModelFactory.createDefaultModel();	
		inferenceService.inferCapabilities(mainModel, currentCap,devid);

		
		
		
		System.out.println("GETTING CAPABILITIES");
		//geet realtime cap of device
		ArrayList<Capability>currentCapabilitiesArray=queryService.getCapabilitiesArray(mainModel,currentCap);
	   System.out.println("XXXXXXXXXXXXXXXXCURRENT CAPABILITIESXXXXXXXXXXXXXX");
	//	currentCap.write(System.out,"TTL");
		//add it to the list of temporary capabilities for the user and device
		inferenceService.changeTemporaryCap(currentCap, ModelController.TTT_GRAPH+devid+uid+"/temp");
		//get accepted capabilities of user for the specific iot device.
		Model acceptedCap=inferenceService.getAcceptedCapabilities(uid,devid);
		Model declinedCap=inferenceService.getDeclinedCapabilities(uid,devid);
		
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
			ArrayList<Capability>declinedCapabilityArray=queryService.getCapabilitiesArray(mainModel,declinedCap);
			
	//HEADERS
			ArrayList<Capability>currentHeaders=queryService.getHeaders(currentCapabilitiesArray);
			System.out.println("HEADERS CURRENT"+currentHeaders.size()+currentHeaders.toString());
			ArrayList<Capability>acceptedHeaders=queryService.getHeaders(acceptedCapabilityArray);
			System.out.println("HEADERS ACCEPTED"+acceptedHeaders.size()+acceptedHeaders.toString());
		JSONArray h=new JSONArray();
			for (Capability current: currentHeaders){
				JSONObject o=new JSONObject(current.toJson());
					if(!compareHeaders(current,acceptedHeaders)){
						o.put("new", true);
					
		System.out.println("New header detected"+current.toJson());

					}
					   h.put(o);
				}
			JSONArray a=new JSONArray();
			for(Capability c: acceptedHeaders){
				a.put(new JSONObject(c.toJson()));
			}
			
			JSONObject j= getNewCapabilities(currentCapabilitiesArray,acceptedCapabilityArray);
			boolean same= sameDeclinedAsCurrent(currentCapabilitiesArray,declinedCapabilityArray);
			j.put("acceptedHeaders", a);
			j.put("same", same);
			if(h.length()!=0){
				
			//sorting fix hashmap android would give inapropraite assigning of jsonarrays to its headers -FIXED
			
			JSONObject sorted=mapCap(h,j.getJSONArray("currentCapabilities"));
			System.out.println(sorted.toString(10));
			j.put("sorted", sorted.getJSONArray("sorted"));
			j.put("currentHeaders", sorted.getJSONArray("currentHeaders"));
			}
			
			
			return j;
	//	return getNewCapabilities(currentCapabilitiesArray,acceptedCapabilityArray);
			
			
		}
		private static boolean compareHeaders (Capability c, ArrayList<Capability> all){
			for(Capability al: all){
				if(c.getCompany_logo().equals(al.getCompany_logo()) && c.getType().equals(al.getType())){
					return true;
				}
			}
			return false;
		}
	
	private static JSONObject mapCap(JSONArray headers,JSONArray cap){
		
		HashMap<JSONObject,JSONArray> map=new HashMap<JSONObject,JSONArray>();
		
		for(int i=0 ;i<headers.length();i++){
			map.put(headers.getJSONObject(i), new JSONArray());	
		}
		
		for(int i=0;i<cap.length();i++){
			System.out.println("For Capability:"+cap.getJSONObject(i).toString()+"\n\n");
			for(JSONObject key: map.keySet()){
				String type=cap.getJSONObject(i).getString("type");
				String logo=cap.getJSONObject(i).getString("company_logo");
				if(key.getString("type").equals(type) && key.getString("company_logo").equals(logo)){
					System.out.println("Added to header:"+key.toString());
					map.get(key).put(cap.getJSONObject(i));										
					
				}
			}
		}
				
JSONObject all=new JSONObject();				
JSONArray sorted=new JSONArray();
JSONArray currentHeaders=new JSONArray();
	for(JSONObject key: map.keySet()){
		currentHeaders.put(key);
		sorted.put(map.get(key));
	}
	all.put("sorted", sorted);
	all.put("currentHeaders", currentHeaders );
		return all;
			
		
	}

	

	
	
	//compareMethod
		
	private static boolean sameDeclinedAsCurrent(ArrayList<Capability>currentCap,ArrayList<Capability> decCap){		
		if(decCap.size()==0 && currentCap.size()>0){
			return false;
		}
		
		for (Capability current: currentCap){
		JSONObject o=new JSONObject(current.toJson());
			if(!decCap.contains(current)){
				o.put("new", true);
			return false;
			}
		}
		
		
		return true;
		
		
		}

	
	private static JSONObject getNewCapabilities(ArrayList<Capability>currentCap,ArrayList<Capability> acceptedCap){
		JSONObject jsondata=new JSONObject();
		ArrayList<Capability> newCapabilities=new ArrayList<Capability>();
		ArrayList<Capability> oldCapabilities=new ArrayList<Capability>();
		JSONArray currentCapJson=new JSONArray();
		
		for (Capability current: currentCap){
		JSONObject o=new JSONObject(current.toJson());
			if(!acceptedCap.contains(current)){
				o.put("new", true);
				newCapabilities.add(current);
System.out.println("New capability detected"+current.toJson());


			}
			   currentCapJson.put(o);
		}
		
		
		jsondata.put("currentCapabilities", currentCapJson);
		
		if(newCapabilities.size()==0){
			jsondata.put("different", false);
			return jsondata;
		}
		else{
			jsondata.put("different", true);
		
		
		JSONArray newCapJson=new JSONArray();
		
		
		for(Capability c:newCapabilities){
		newCapJson.put(new JSONObject(c.toJson()));
		}

		jsondata.put("newCapabilities", newCapJson);

		}
		
		
		return jsondata;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
