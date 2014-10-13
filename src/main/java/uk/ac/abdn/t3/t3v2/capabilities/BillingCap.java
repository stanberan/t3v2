package uk.ac.abdn.t3.t3v2.capabilities;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class BillingCap implements Capability {
	String dev_id;
	String type;
	String producer_uri;   //AGENT who owns billing server!
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

	public String getProducer_uri() {
		return producer_uri;
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
	
	public String getDev_id() {
		return dev_id;
	}
	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}
	public void setProducer_uri(String producer_uri) {
		this.producer_uri = producer_uri;
	}
	@Override
	public String toString() {
		return "BillingCap [provider_uri=" + producer_uri + ", data_uri="
				+ data_uri + ", data_desc=" + data_desc + "]";
	}

	@Override
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
		if(o!=null && o instanceof BillingCap){
		return ((BillingCap) o).getProducer_uri().equals(this.getProducer_uri());
	
		}
		return true;
	
	}
	   public boolean equals(Object object)
	    {
	       if(object!=null && object instanceof BillingCap){
	    	   String x1=this.getProducer_uri();
	    	   String x2=((BillingCap)object).getProducer_uri();
	    	   String y1=this.getData_desc();
	    	   String y2=((BillingCap)object).getData_desc();
	    	   
	    	if(x1.equals(x2)&& y1.equals(y2)){
	    		return true;
	    	}
	    	   
	       }
	       return false;
	    }
	   public int hashCode(){
			return 0;
		
		   }

}
