package vn.web.pet.controller.frontend;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.web.pet.controller.BaseController;
import vn.web.pet.dto.Cart;
import vn.web.pet.dto.CartProduct;
import vn.web.pet.dto.Customer;
import vn.web.pet.model.Category;
import vn.web.pet.model.Product;
import vn.web.pet.model.SaleOrder;
import vn.web.pet.model.SaleOrderProduct;
import vn.web.pet.model.User;
import vn.web.pet.service.CategoryService;
import vn.web.pet.service.ProductService;
import vn.web.pet.service.SaleOrderService;

@Controller
public class CartController extends BaseController{

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	CategoryService categoryService;
	
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
	
	
	@RequestMapping (value = "/add-to-cart", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addToCart(
			@RequestBody CartProduct cartProduct,
			final HttpServletRequest request) {
		
		Cart cart = null;
		//tao gio hang (neu chua co)
		HttpSession session = request.getSession();
		if(session.getAttribute(cartSessionName()) == null) {
			cart = new Cart();
			session.setAttribute(cartSessionName(), cart);
		}else {
			cart = (Cart)session.getAttribute(cartSessionName());
		}
		
		//them hang vao gio
		   	// hang chua co trong gio
		int pIndex = cart.findProductById(cartProduct.getId());
		if(pIndex == -1) {
			//th2: hang da co trong gio
			Product dbProduct = productService.getById(cartProduct.getId());
			
			cartProduct.setName(dbProduct.getName());
			cartProduct.setPrice(dbProduct.getPrice());
			cartProduct.setAvatar(dbProduct.getAvatar());
			
			//them vao gio
			cart.getCartProducts().add(cartProduct);
		}else {
			cart.getCartProducts().get(pIndex).updateQuantity( cartProduct.getQuantity().intValue());
//			cart.getCartProducts().add(cartProduct);
		}
		
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("code", 200);
		jsonResult.put("message", "Đã thêm sản phẩm " + 
						cartProduct.getName() + " vào giỏ hàng");
		jsonResult.put("totalCartProducts", cart.totalCartProduct()); 
		
		return ResponseEntity.ok(jsonResult);
	}
	
	@GetMapping("/cart-view")
	public String cartView(HttpServletRequest request,
							Model model) {
		List<Category> categories = categoryService.findAllActive();
		Cart cart = null;
		
		BigInteger totalCartProducts = BigInteger.ZERO;
		BigDecimal totalCartPrice = BigDecimal.ZERO;
		
		HttpSession session = request.getSession();
		if(session.getAttribute(cartSessionName()) != null) {
			cart = (Cart)session.getAttribute(cartSessionName());
			totalCartProducts = cart.totalCartProduct();
			totalCartPrice = cart.totalCartPrice();
		}
		
		model.addAttribute("cart", cart);
		model.addAttribute("totalCartProducts", totalCartProducts);
		model.addAttribute("totalCartPrice", totalCartPrice);
		model.addAttribute("categories", categories);
		return "frontend/cart-view";
	}
	
	@PostMapping("/update-product-quantity")
	public ResponseEntity<Map<String, Object>> updateProductQuantity(
			@RequestBody CartProduct cartProduct,
			final HttpServletRequest request) {
		
		Cart cart = null;
		//Lay gio hang
		HttpSession session = request.getSession();
		cart = (Cart)session.getAttribute(cartSessionName());

		//tim sp trong gio
		int pIndex = cart.findProductById(cartProduct.getId());
		if(cartProduct.getQuantity().longValue() != -1 || 
			cart.getCartProducts().get(pIndex).getQuantity().intValue() > 1 ){
			
			//cap nhat so luong
			cart.getCartProducts().get(pIndex).updateQuantity(
					cartProduct.getQuantity().intValue());
			//session.setAttribute("cart", cart);
		}
		
		Map<String, Object> jsonResult = new HashMap<String, Object>();
//		jsonResult.put("code", 200);
		jsonResult.put("totalCartPrice", toCurrency(cart.totalCartPrice())); 
		jsonResult.put("newQuantity", cart.getCartProducts().get(pIndex).getQuantity()); 
		jsonResult.put("productId", cartProduct.getId()); 
		jsonResult.put("totalCartProducts", cart.totalCartProduct());
		jsonResult.put("totalPrice", toCurrency(cart.getCartProducts().get(pIndex).totalPrice()));
		
		return ResponseEntity.ok(jsonResult);
	}
	
	public StringBuilder toCurrency(BigDecimal num) {
		String str1 = "";
		str1 += num.longValue();
		StringBuilder str = new StringBuilder(str1);
		int i = str.length();
		int j = 0;
		while (i > 0) {
			j ++;
			i --;
			if (i > 0 && j % 3 == 0) {
				str = str.insert(i, ',');
			}
		}
		return str;
	}
	
	//Luu gio hang
	@RequestMapping(value = "/place-order", method = RequestMethod.POST)
	ResponseEntity<Map<String, Object>> placeOrder(
			@RequestBody Customer customer,
			final HttpServletRequest request) throws IOException {
		
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		
		//Kiem tra thong tin customer bat buoc
		if (customer.getTxtName() == null || customer.getTxtName().length() <= 0) {
			jsonResult.put("message", "Bạn chưa nhập họ tên");
		}
		else if (customer.getTxtMobile() == null || customer.getTxtMobile().length() <= 0) {
			jsonResult.put("message", "Bạn chưa nhập số điện thoại");
		}
		else {					
			HttpSession session = request.getSession();
			if (session.getAttribute(cartSessionName()) == null) {
				jsonResult.put("message", "Bạn chưa có giỏ hàng");
			}
			else {
				Cart cart = (Cart)session.getAttribute(cartSessionName());
				//Luu cac san pham trong gio hang vao DB: tbl_sale_order_product
				SaleOrder saleOrder = new SaleOrder();
				for (CartProduct cartProduct : cart.getCartProducts()) {
					SaleOrderProduct saleOrderProduct = new SaleOrderProduct();
					//Lay product trong db
					Product dbProduct = productService.getById(cartProduct.getId());
					saleOrderProduct.setProduct(dbProduct);
					saleOrderProduct.setQuantity(cartProduct.getQuantity().intValue());
					
					saleOrder.addRelationalSaleOrderProduct(saleOrderProduct);
				}
				//Luu don hang vao tbl_sale_order
				Calendar cal = Calendar.getInstance();
				String code = customer.getTxtMobile() + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + 
						cal.get(Calendar.DAY_OF_MONTH);	
				saleOrder.setCode(code);
 				
				User user = new User();
 				user.setId(2);
 				saleOrder.setUser(user);
 				
 				saleOrder.setCustomerName(customer.getTxtName());
 				saleOrder.setCustomerMobile(customer.getTxtMobile());
 				saleOrder.setCustomerEmail(customer.getTxtEmail());
 				saleOrder.setCustomerAddress(customer.getTxtAddress());
 				
 				saleOrder.setCreateDate(new Date());
 				saleOrder.setTotal(cart.totalCartPrice());
 				
 				saleOrderService.saveOrder(saleOrder);
 				
 				jsonResult.put("message", "Bạn đã đặt hàng thành công, cảm ơn bạn");
 				
 				//Xoa gio hang sau khi da dat hang
 				cart = new Cart();
 				session.setAttribute(cartSessionName(), cart);
			}
			
		}
		return ResponseEntity.ok(jsonResult);
	}
	
}
