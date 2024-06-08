package vn.web.pet.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class Cart {
	private ArrayList<CartProduct> cartProducts = new ArrayList<CartProduct>();
	
	//tim hang trong gio theo id
	public int findProductById(int productId) {
		for(int index = 0; index < cartProducts.size(); index ++) {
			if(cartProducts.get(index).getId() == productId)
				return index;
		}
		return -1;
	}
	
	//tinh tong tien gio hang
	public BigDecimal totalCartPrice() {
		BigDecimal total = BigDecimal.ZERO;
		for(CartProduct cartProduct: this.cartProducts) {
			total = total.add(cartProduct.totalPrice());
		}
		return total;
	}
	
	public BigInteger totalCartProduct() {
		BigInteger total = BigInteger.ZERO;
		for(CartProduct cartProduct: this.cartProducts) {
			total = total.add(cartProduct.getQuantity());
		}
		return total;
	}
	
	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public Cart(ArrayList<CartProduct> cartProducts) {
		super();
		this.cartProducts = cartProducts;
	}

	public ArrayList<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(ArrayList<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}
	
}
