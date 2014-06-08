package uk.ac.abdn.t3.t3v2.capabilities;

public class PersonalDataGeneration {

	
	String dev_id;
	String company_uri;
	String data_uri;
	
	String data_desc;

	public String getDev_id() {
		return dev_id;
	}

	public void setDev_id(String dev_id) {
		this.dev_id = dev_id;
	}

	public String getCompany_uri() {
		return company_uri;
	}

	public void setCompany_uri(String company_uri) {
		this.company_uri = company_uri;
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
		return "PersonalDataGeneration [dev_id=" + dev_id + ", company_uri="
				+ company_uri + ", data_uri=" + data_uri + ", data_desc="
				+ data_desc + "]";
	}
	
	
}
