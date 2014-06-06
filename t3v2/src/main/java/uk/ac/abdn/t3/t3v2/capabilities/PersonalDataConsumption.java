package uk.ac.abdn.t3.t3v2.capabilities;

public class PersonalDataConsumption implements Capability{
	
	String typeURI=null;

	
	String descriptionOfCapability="Personal Data Consumption"; //TODO pull from REPO
	String purpose="Purpose was not find in provenance";
	String consumer="Consumer origin not found in provenance";
	String consumerURL="Consumer have not published URL";
	String consumes="";
	String consumerLogo;
	
	@Override
	public String getTypeURI() {
		
		return typeURI;
	}
}
