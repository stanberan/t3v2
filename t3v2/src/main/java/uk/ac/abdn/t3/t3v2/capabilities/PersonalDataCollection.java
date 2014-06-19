package uk.ac.abdn.t3.t3v2.capabilities;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import uk.ac.abdn.t3.t3v2.pojo.PersonalData;

public class PersonalDataCollection implements Capability{

	
	
	String dev_id;
	String type;
	String producer_uri;
	String consumer_uri;
	String data_uri;
	String data_desc;
	String company_logo;
	public String getCompany_logo() {
		return company_logo;
	}

	public void setCompany_logo(String producer_logo) {
		this.company_logo = producer_logo;
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
	public String getProducer_uri() {
		return producer_uri;
	}
	public void setProducer_uri(String producer_uri) {
		this.producer_uri = producer_uri;
	}
	public String getConsumer_uri() {
		return consumer_uri;
	}
	public void setConsumer_uri(String consumer_uri) {
		this.consumer_uri = consumer_uri;
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
		return "PersonalDataCollection [dev_id=" + dev_id + ", producer_uri="
				+ producer_uri + ", consumer_uri=" + consumer_uri
				+ ", data_uri=" + data_uri + ", data_desc=" + data_desc + "]";
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
		
		if(o!=null && o instanceof PersonalDataUsage){
		return ((PersonalDataCollection) o).getConsumer_uri().equals(this.getConsumer_uri());
	
		}
		return false;
	
	}
	   public boolean equals(Object object)
	    {
	       if(object!=null && object instanceof PersonalDataCollection){
	    	   String x1=this.getConsumer_uri();
	    	   String x2=((PersonalDataCollection)object).getConsumer_uri();
	    	   String y1=this.getData_desc();
	    	   String y2=((PersonalDataCollection)object).getData_desc();
	    	   
	    	if(x1.equals(x2)&& y1.equals(y2)){
	    		return true;
	    	}
	    	   
	       }
	       return false;
	    }
	   public int hashCode(){
			  String str=this.getDev_id()+this.getData_desc()+this.getConsumer_uri();
			return str.hashCode() ;}
	
	
	
	
}
