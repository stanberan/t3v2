package uk.ac.abdn.t3.t3v2.capabilities;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class PersonalDataGeneration implements Capability {

	String type;
	
	
	String data_desc;
	String dev_id;
	String generatedBy;
	String data_uri;
	String generatedBy_logo;
	
	

	public String getGeneratedBy_logo() {
		return generatedBy_logo;
	}

	public void setGeneratedBy_logo(String generatedBy_logo) {
		this.generatedBy_logo = generatedBy_logo;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDev_id() {
		return dev_id;
	}

	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}

	public String getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(String company_uri) {
		this.generatedBy = company_uri;
	}

	public String getData_uri() {
		return data_uri;
	}

	public void setData_uri(String data_uri) {
		this.data_uri = data_uri;
	}

	public String getData_desc() {
		return data_desc;
	}

	public void setData_desc(String data_desc) {
		this.data_desc = data_desc;
	}

	@Override
	public String toString() {
		return "PersonalDataGeneration [dev_id=" + dev_id + ", generatedBy="
				+ generatedBy + ", data_uri=" + data_uri + ", data_desc="
				+ data_desc + "]";
	}
	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
		}
		return "{\"error\":\"couldnotgenerate json"+this.toString()+"\"}";
	}
	
	public boolean compareHeaders(Object o){
		if(o instanceof PersonalDataGeneration){
		return ((PersonalDataGeneration) o).getGeneratedBy().equals(this.getGeneratedBy());
	
		}
		return false;
	
	}
	
	   public boolean equals(Object object)
	    {
	       if(object!=null && object instanceof PersonalDataGeneration){
	    	   String x1=this.getData_desc();
	    	   String x2=((PersonalDataGeneration)object).getData_desc();
	    	   String y1=this.getGeneratedBy();
	    	   String y2=((PersonalDataGeneration)object).getGeneratedBy();
	    	   
	    	   
	    	if(x1.equals(x2)&& y1.equals(y2)){
	    		return true;
	    	}
	    	   
	       }
	       return false;
	    }
	   public int hashCode(){
			  String str=this.getData_desc()+this.getGeneratedBy();
			return str.hashCode() ;}
	
	
}
