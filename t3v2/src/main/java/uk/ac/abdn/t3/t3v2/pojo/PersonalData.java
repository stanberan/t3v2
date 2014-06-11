package uk.ac.abdn.t3.t3v2.pojo;

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
	
}
