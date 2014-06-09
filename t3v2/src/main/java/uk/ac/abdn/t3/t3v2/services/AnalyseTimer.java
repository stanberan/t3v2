package uk.ac.abdn.t3.t3v2.services;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import uk.ac.abdn.t3.t3v2.DB;
import uk.ac.abdn.t3.t3v2.Models;
import uk.ac.abdn.t3.t3v2.pojo.Device;

public class AnalyseTimer {
	static DB db=DB.getDB();

	long delay = 10*60*1000; // ms
    LoopTask task = new LoopTask();
    Timer timer = new Timer("TaskName");
   
    public AnalyseTimer(int minutes){
    	if(minutes!=0 && minutes>0){
    	
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
    	InferenceService infService=InferenceService.getService();
    	System.out.println("Executing: Checking provenance "+delay);
    	
    	System.out.println("Getting all devices...");
    	ArrayList<Device> devices=db.getAllDevices();
			
    	
		for(int i=0;i<devices.size();i++){
			Device d=devices.get(i);		
			
			ArrayList<String> users=d.getUsers();
			ArrayList<String> gcms=d.getGoogleId();
			
			OntModel mainDeviceModel=infService.getDeviceOntModel(d.getDevid());
			Model currentInferredCapabilities=ModelFactory.createDefaultModel();
			infService.inferCapabilities(mainDeviceModel, currentInferredCapabilities);
			System.out.println("CURRENT INFERRED CAPABILITIES FROM LOOP");
			currentInferredCapabilities.write(System.out, "TTL");
			for(int j=0; j<users.size();j++){
				String userid=users.get(j);
			    String acceptedCapGraph=Models.graphNS+userid+d.getDevid()+"/cap";
			    String temporaryCapGraph=Models.graphNS+userid+d.getDevid()+"/temporary";
			    
				if(infService.compareCapabilities(acceptedCapGraph, currentInferredCapabilities)){
					System.out.println("CAPABILITIES CHANGEEEEEEEEDDDDDDDD HEEEEEEEEYYYYYYY");
					//save temporary cap
					infService.changeCapabilities(currentInferredCapabilities, temporaryCapGraph);
					//notify user about change so he can retrieve again.
					
				//	NotificationService.notifyUser(gcms.get(j), "Hey, there are new capabilities you did not agreed to, it would be worthwhile to check.");
					
					
				}
		
				
			}
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
    }
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
