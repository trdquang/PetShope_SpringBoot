package vn.web.pet.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CartProduct {
	private int id;
	private String name;
	private BigInteger quantity;
	private BigDecimal price;
	private String avatar;
	
	public BigDecimal totalPrice() {
		return price.multiply(new BigDecimal(quantity));
	}
	
	//phuong thuc tang/giam them so luong cua san pham
	public void updateQuantity(int quantityForUpdate) {
		this.quantity = this.quantity.add(BigInteger.valueOf(quantityForUpdate) );
	}
	
	public CartProduct() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getQuantity() {
		return quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public CartProduct(int id, String name, BigInteger quantity, BigDecimal price, String avatar) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.avatar = avatar;
	}
}
