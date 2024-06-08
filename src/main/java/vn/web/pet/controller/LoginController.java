package vn.web.pet.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import vn.web.pet.model.Role;
import vn.web.pet.model.User;
import vn.web.pet.service.RoleService;
import vn.web.pet.service.UserService;

@Controller
public class LoginController extends BaseController{
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "/signup";
	}
	
	@PostMapping("/register")
	public String register(HttpServletRequest request,
						   Model model) {
		User user = new User();
		user.setStatus(true);
		user.setCreateDate(new Date());
		
		user.setUsername(request.getParameter("username"));
		user.setPassword( new BCryptPasswordEncoder(4).encode(request.getParameter("password")));
		
		user.setName(request.getParameter("name"));
		user.setMobile(request.getParameter("mobile"));
		user.setEmail(request.getParameter("email"));
		user.setAddress(request.getParameter("address"));
		
		//Chọn quyền là guest 
		Role role = (Role)roleService.getRoleByName("GUEST");
		user.addRelationalUserRole(role);
		userService.saveOrUpdate(user);
		
		return "redirect:login";
	}
}
