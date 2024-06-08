package vn.web.pet.controller.frontend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.web.pet.controller.BaseController;
import vn.web.pet.dto.Jw28Constant;
import vn.web.pet.dto.ProductStatic;
import vn.web.pet.model.Category;
import vn.web.pet.model.Product;
import vn.web.pet.model.ProductImage;
import vn.web.pet.model.User;
import vn.web.pet.service.CategoryService;
import vn.web.pet.service.ProductImageService;
import vn.web.pet.service.ProductService;
import vn.web.pet.service.UserService;

@Controller
public class HomeController extends BaseController implements Jw28Constant{
	@Autowired
	UserService userService;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductImageService productImageService;
	
	 public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
	
	private Page<Product> paginate(List<Product> products, int page, int size) {
	    int start = (page - 1) * size;
	    int end = Math.min(start + size, products.size());
	    return new PageImpl<>(products.subList(start, end), PageRequest.of(page - 1, size), products.size());
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model, 
			           @RequestParam(name = "page", defaultValue = "1") int page,
			           @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
			           @RequestParam(name = "beginPrice", defaultValue = "") String beginPrice,
			           @RequestParam(name = "endPrice", defaultValue = "") String endPrice,
			           @RequestParam(name = "categoryId", defaultValue = "0") int categoryId) {
		ProductStatic productStatic = new ProductStatic();
		System.out.println("new = " + productStatic.getNewClient());
		
		List<Category> categories = categoryService.findAllActive();
		List<Product> allProducts = productService.searchProductByUser(categoryId, keyWord, beginPrice, endPrice);
		// Tìm tất cả sản phẩm

	    // Tạo một trang mới từ danh sách sản phẩm và số trang được yêu cầu
	    Page<Product> productPage = paginate(allProducts, page, SIZE_OF_BIG_PAGE);

	    // Danh sách sản phẩm trên trang hiện tại
	    List<Product> products = productPage.getContent();

	    // Số trang tổng cộng
	    int totalPages = productPage.getTotalPages();
	    
	    //-----------------------Xư ly phan login-----------------------------------------------
	    User user = new User();
	    String userName = "";
		Object loginedUser = 
				SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (loginedUser != null && loginedUser instanceof UserDetails) {
			user = (User) loginedUser;
			userName = user.getName();
		}else
			userName = null;
		
		
	    model.addAttribute("products", products);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("keyWord", keyWord.trim());
	    model.addAttribute("beginPrice", beginPrice);
	    model.addAttribute("endPrice", endPrice);
	    model.addAttribute("userName", capitalizeFirstLetter(userName));
	    model.addAttribute("categories", categories);
		return "frontend/index";
	}
	
	@GetMapping("/product-detail/{productId}")
	public String productDetail(Model model,
		@PathVariable("productId") int productId) {
		//lay sp trong db
		List<Category> categories = categoryService.findAllActive();
		Product product = productService.getById(productId);
		List<Product> allProducts = productService.searchByCategoryId(product.getCategory().getId());
		List<Product>productHot = new ArrayList<Product>();
		int sizeOfHost = Math.min(5, allProducts.size());
		for(int i = 0; i < sizeOfHost; i++)
			productHot.add(allProducts.get(i));
		
		model.addAttribute("product", product);
		
		//lay cac anh cua san pham
		List<ProductImage> productImages = productImageService.getProductImagesByProductId(productId);
		model.addAttribute("productImages", productImages);
		
		model.addAttribute("categories", categories);
		model.addAttribute("productHot", productHot);
		
		return "frontend/product-detail";
	}
	
	@GetMapping("/lienhe")
	public String lienHe(Model model) {
		List<Category> categories = categoryService.findAllActive();
		model.addAttribute("categories", categories);
		return "frontend/lienhe";
	}
}
