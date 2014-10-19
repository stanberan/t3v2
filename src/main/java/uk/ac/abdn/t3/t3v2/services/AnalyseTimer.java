package uk.ac.abdn.t3.t3v2.services;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import uk.ac.abdn.t3.t3v2.CapabilityMatchingService;
import uk.ac.abdn.t3.t3v2.DB;
import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.capabilities.Capability;
import uk.ac.abdn.t3.t3v2.models.ModelController;
import uk.ac.abdn.t3.t3v2.pojo.Device;

public class AnalyseTimer {

	InferenceService inferenceService=InferenceService.getService();
	QueryService queryService=QueryService.getSingleton();
	static DB db=DB.getDB();
	ArrayList<Device> devices;
	ArrayList<String> gcms;
	ArrayList<String> users;
	long delay = 10*60*1000; // ms
    LoopTask task = new LoopTask();
    Timer timer;
    Device d;
    public AnalyseTimer(int minutes){
    	if(minutes!=0 && minutes>0){
    		 timer= new Timer("TaskName"+new Date().getTime());
    		delay=minutes*60*1000;
    	}
    }
    public void start() {
    timer.cancel();
    timer = new Timer("Check for Change in Prov");
    Date executionDate = new Date(); 
    timer.scheduleAtFixedRate(task, executionDate, delay);
    }

    private class LoopTask extends TimerTask {
    public void run() {

    	System.out.println("Executing: Checking provenance "+delay);
    	
    	System.out.println("Getting all devices...");
    	devices=db.getAllDevices();
			
    	
		for(int i=0;i<devices.size();i++){
			d=devices.get(i);		
			
			users=d.getUsers();
			gcms=d.getGoogleId();
			for(int j=0; j<users.size();j++){
				String userid=users.get(j);
				//check
		JSONObject jsondata=CapabilityMatchingService.capabilityMatch(d.getDevid(), userid);
			if(jsondata.getBoolean("different")){
			String message="Some capabilities for the "+d.getDevid()+"has changed.\nClick on this notification to retrieve them.";			
	        JSONObject ob=new JSONObject();
	        ob.put("headers",jsondata.getJSONArray("currentHeaders"));
			ob.put("message", message);
			ob.put("devid", d.getDevid());
			ob.put("time", new Date().getTime());
			
			NotificationService.notifyUser(gcms.get(j), ob);
		
				
			}
		}
    }
			
			
			
			
			
		}
		
		
		
    }
		
		
		public boolean different(String devid,String uid){
			Model currentCap=ModelFactory.createDefaultModel();	
			
			
			//add it to the list of temporary capabilities for the user and device
		
			inferenceService.changeTemporaryCap(currentCap, ModelController.TTT_GRAPH+devid+uid+"/temp");
			//get accepted capabilities of user for the specific iot device.
			Model acceptedCap=inferenceService.getAcceptedCapabilities(uid,devid);
			
			
			//check if the capabilities are different in the graph level. (is isomorphic)
	return inferenceService.compareCapabilities(acceptedCap, currentCap);
		}
		
		
	
    
    public void stopTimer(){
    	timer.cancel();
    	timer.purge();
    }

    public static void main(String[] args) {
    AnalyseTimer executingTask = new AnalyseTimer(1);
    executingTask.start();
    }
	
}
