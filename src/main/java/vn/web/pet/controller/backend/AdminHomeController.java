package vn.web.pet.controller.backend;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.web.pet.controller.BaseController;
import vn.web.pet.service.ProductService;
import vn.web.pet.service.SaleOrderService;
import vn.web.pet.service.UserService;

@Controller
public class AdminHomeController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;
	@Autowired
	SaleOrderService saleOrderService;
	
	
	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String viewHomePage(Model model) {
		int newClient = userService.countNewOfMonuth();
		int newProduct = productService.countNewOfMonuth();
		BigDecimal newTotalPrice = saleOrderService.countNewOfMonuth();
		int totalProduct = productService.findAllActive().size();
		
		model.addAttribute("newClient", newClient);
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("newTotalPrice", newTotalPrice);
		model.addAttribute("totalProduct", totalProduct);
		return "backend/home";
	}

}
