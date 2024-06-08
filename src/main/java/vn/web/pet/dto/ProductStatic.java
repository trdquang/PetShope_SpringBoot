package vn.web.pet.dto;

import org.springframework.beans.factory.annotation.Autowired;

import vn.web.pet.service.UserService;

public class ProductStatic {
	@Autowired
	UserService userService;
	
	int newClient;
	double earnOfMonth;
	int orderOfMounth;
	int productCount;
	
	public ProductStatic() {
	}
	

	public ProductStatic(UserService userService, int newClient, double earnOfMonth, int orderOfMounth,
			int productCount) {
		super();
		this.newClient = newClient;
		this.earnOfMonth = earnOfMonth;
		this.orderOfMounth = orderOfMounth;
		this.productCount = productCount;
	}


	public int getNewClient() {
		return newClient;
	}

	public void setNewClient(int newClient) {
		this.newClient = newClient;
	}

	public double getEarnOfMonth() {
		return earnOfMonth;
	}

	public void setEarnOfMonth(double earnOfMonth) {
		this.earnOfMonth = earnOfMonth;
	}

	public int getOrderOfMounth() {
		return orderOfMounth;
	}

	public void setOrderOfMounth(int orderOfMounth) {
		this.orderOfMounth = orderOfMounth;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	
}
