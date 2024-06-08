package vn.web.pet.controller.backend;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.web.pet.controller.BaseController;
import vn.web.pet.dto.Jw28Constant;
import vn.web.pet.model.Category;
import vn.web.pet.model.User;
import vn.web.pet.service.CategoryService;
import vn.web.pet.service.UserService;

@Controller
@RequestMapping("/admin/category/")
public class CategoryController extends BaseController implements Jw28Constant{
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
//Xem
	private Page<Category> paginate(List<Category> categories, int page, int size) {
	    int start = (page - 1) * size;
	    int end = Math.min(start + size, categories.size());
	    return new PageImpl<>(categories.subList(start, end), PageRequest.of(page - 1, size), categories.size());
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listCategory(final Model model,
								@RequestParam(name = "page", defaultValue = "1") int page) {
		List<Category> allCategories = categoryService.findAllActive();
		// Tìm tất cả sản phẩm

	    // Tạo một trang mới từ danh sách sản phẩm và số trang được yêu cầu
	    Page<Category> categoryPage = paginate(allCategories, page, SIZE_OF_PAGE);

	    // Danh sách sản phẩm trên trang hiện tại
	    List<Category> categories = categoryPage.getContent();

	    // Số trang tổng cộng
	    int totalPages = categoryPage.getTotalPages();
		
		model.addAttribute("categories", categories);
		model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
	    
		return "backend/category-list";
	}
//Them	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addCategory(final Model model) {
		
		Category category = new Category();	
		category.setCreateDate(new Date());
		model.addAttribute("category", category);
		
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		
		return "backend/category-add";
	}
	
	@RequestMapping(value = "add-save", method = RequestMethod.POST)
	public String addSave(final Model model, 
			@ModelAttribute("category") Category category) {

		categoryService.saveOrUpdate(category);

		return "redirect:/admin/category/add";
	}

//Sua
	@RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
	public String editCategory(final Model model,
			@PathVariable("categoryId") int categoryId) {
		
		//Lay category trong DB
		Category category = categoryService.getById(categoryId);	
		category.setUpdateDate(new Date());
		model.addAttribute("category", category);
		
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		
		return "backend/category-edit";
	}
	
	@RequestMapping(value = "edit-save", method = RequestMethod.POST)
	public String editSave(final Model model, 
			@ModelAttribute("category") Category category) {

		categoryService.saveOrUpdate(category);

		return "redirect:/admin/category/list";
	}
//Xoa
	
	//1. Xoa han trong DB
//	@RequestMapping(value = "delete/{categoryId}", method = RequestMethod.GET)
//	public String deleteCategory(final Model model,
//			@PathVariable("categoryId") int categoryId) {
//		
//		categoryService.deleteCategoryById(categoryId);
//		
//		return "redirect:/admin/category/list";
//	}
	//2. Inactive
	@RequestMapping(value = "delete/{categoryId}", method = RequestMethod.GET)
	public String deleteCategory(final Model model,
			@PathVariable("categoryId") int categoryId) {
		
		Category category = categoryService.getById(categoryId);
		category.setStatus(false);
		categoryService.saveOrUpdate(category);
		
		return "redirect:/admin/category/list";
	}
	
}
