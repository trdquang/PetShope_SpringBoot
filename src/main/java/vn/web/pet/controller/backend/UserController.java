package vn.web.pet.controller.backend;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.web.pet.controller.BaseController;
import vn.web.pet.dto.Jw28Constant;
import vn.web.pet.model.Role;
import vn.web.pet.model.User;
import vn.web.pet.service.RoleService;
import vn.web.pet.service.UserService;

@Controller
public class UserController extends BaseController implements Jw28Constant{
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	
	private Page<User> paginate(List<User> users, int page, int size) {
	    int start = (page - 1) * size;
	    int end = Math.min(start + size, users.size());
	    return new PageImpl<>(users.subList(start, end), PageRequest.of(page - 1, size), users.size());
	}
	
	@GetMapping("/admin/user/list")
	public String userList(Model model,
						   @RequestParam(name = "page", defaultValue = "1") int page) {
		List<User> allUsers = userService.findAllActive();
		// Tìm tất cả sản phẩm

	    // Tạo một trang mới từ danh sách sản phẩm và số trang được yêu cầu
	    Page<User> userPage = paginate(allUsers, page, SIZE_OF_PAGE);

	    // Danh sách sản phẩm trên trang hiện tại
	    List<User> users = userPage.getContent();

	    // Số trang tổng cộng
	    int totalPages = userPage.getTotalPages();
		
		model.addAttribute("users", users);
		model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
		return "backend/user-list";
	}
	
	@GetMapping(value = "/admin/user/edit/{userId}")
	public String editUser(final Model model,
			@PathVariable("userId") int userId) {
		
		//Lay category trong DB
		User user = userService.getById(userId);	
		user.setUpdateDate(new Date());
		model.addAttribute("user", user);
		
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		
		return "backend/user-edit";
	}
	
	@PostMapping(value = "/admin/user/edit-save")
	public String editSave(final Model model, 
			@ModelAttribute("user") User user) {

		userService.saveOrUpdate(user);

		return "redirect:/admin/user/list";
	}
	
	@GetMapping(value = "admin/user/delete/{userId}")
	public String deleteCategory(final Model model,
			@PathVariable("userId") int userId) {
		
		User user = userService.getById(userId);
		user.setStatus(false);
		userService.saveOrUpdate(user);
		
		return "redirect:/admin/user/list";
	}
	
	@GetMapping("/admin/user/add")
	String addGet() {
		return "backend/user-add";
	}
	
	@PostMapping("/admin/user/add-save")
	String addPost(HttpServletRequest request) {
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
		return "redirect:/admin/user/add";
	}
}
