package vn.web.pet.dto;

public class Contact {
	
	private String name;
	private String email;
	private String mobile;
	private String address;
	private String message;
	//Alt_Shift_S + O
	public Contact() {
		super();
	}
	public Contact(String name, String email, String mobile, String address, String message) {
		super();
		this.name = name; 
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.message = message;
	}
	//Alt_Shift_S + R
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
