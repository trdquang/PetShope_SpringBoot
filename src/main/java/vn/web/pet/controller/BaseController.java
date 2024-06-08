package vn.web.pet.controller;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.web.pet.dto.Cart;
import vn.web.pet.model.User;


public class BaseController {
	
	public String cartSessionName() {
		
		User user = new User();
		String cartSessionName = "";
		
		Object loginedUser = 
				SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (loginedUser != null && loginedUser instanceof UserDetails) {
			user = (User) loginedUser;
			cartSessionName = "cart" + user.getId();
		}else
			cartSessionName = "cart";
		
	
		return cartSessionName;
	}
	
	@ModelAttribute("title")
	public String projectTitle() {
		return "Javaweb 28";
	}
	
	@ModelAttribute("totalCartProducts")
	public BigInteger getTotalCartProducts(final HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute(cartSessionName()) == null) {
			return BigInteger.ZERO;
		}else {
			Cart cart = (Cart)session.getAttribute(cartSessionName());
			return cart.totalCartProduct();
		}
	}
	
	// Lay thong tin cua user dang nhap
		@ModelAttribute("loginedUser")
		public User getLoginedUser() {

			Object loginedUser = 
					SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (loginedUser != null && loginedUser instanceof UserDetails) {
				User user = (User) loginedUser;
				return user;
			}
			
			return new User();
		}
		// Kiem tra da login hay chua?
			@ModelAttribute("isLogined")
			public boolean isLogined() {
				Object loginedUser = SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();

				if (loginedUser != null && loginedUser instanceof UserDetails) {
					return true;
				}
				return false;
			}

}
