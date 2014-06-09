package uk.ac.abdn.t3.t3v2.capabilities;

public class BillingCap {
	String dev_id;
	String producer_uri;   //AGENT who owns billing server!
	String data_uri;
	String data_desc;
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
	
	
}