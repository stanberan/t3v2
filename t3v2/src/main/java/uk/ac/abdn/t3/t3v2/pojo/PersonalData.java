package uk.ac.abdn.t3.t3v2.pojo;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class PersonalData {

	String uri;
	String description;
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	   public boolean equals(Object object)
	    {
	       if(object!=null && object instanceof PersonalData){
	    	   
	    	if(this.getDescription().equals(((PersonalData)object).getDescription())){
	    		return true;
	    	}
	    	   
	       }
	       return false;
	    }
	   public int hashCode(){
			  String str=this.getDescription();
			return str.hashCode() ;}
		public String toJson(){
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
			try {
				return mapper.writeValueAsString(this);
			} catch (Exception e) {
			}
			return "{\"error\":\"couldnotgenerate json"+this.toString()+"\"}";
		
			
		}
	
}
