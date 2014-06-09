package uk.ac.abdn.t3.t3v2.capabilities;

public class PersonalDataSharing implements Capability {
	
	PersonalDataSharing sharing;
	
	String dev_id;
	String producer_uri;
	String consumer_uri;
	String data_uri;
	String data_desc;
	String purpose;
	
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
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Override
	public String toString() {
		return "{\"producer_uri\":" + producer_uri
				+ ", \"consumer_uri\":" + consumer_uri + ", \"data_uri\":" + data_uri
				+ ", \"data_desc\":" + data_desc + ", \"purpose\":" + purpose + "}";
	}
}
