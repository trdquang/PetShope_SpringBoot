package vn.web.pet.controller.backend;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.web.pet.controller.BaseController;
import vn.web.pet.dto.Jw28Constant;
import vn.web.pet.model.Category;
import vn.web.pet.model.SaleOrder;
import vn.web.pet.service.SaleOrderService;

@Controller
public class SaleOrderController extends BaseController implements Jw28Constant{
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	private Page<SaleOrder> paginate(List<SaleOrder> saleOrders, int page, int size) {
	    int start = (page - 1) * size;
	    int end = Math.min(start + size, saleOrders.size());
	    return new PageImpl<>(saleOrders.subList(start, end), PageRequest.of(page - 1, size), saleOrders.size());
	}
	
	@GetMapping("/admin/order/list")
	public String listOrder(Model model,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		List<SaleOrder> allSaleOrders = saleOrderService.findAllActive();
		// Tìm tất cả sản phẩm

	    // Tạo một trang mới từ danh sách sản phẩm và số trang được yêu cầu
	    Page<SaleOrder> saleOrderPage = paginate(allSaleOrders, page, SIZE_OF_PAGE);

	    // Danh sách sản phẩm trên trang hiện tại
	    List<SaleOrder> saleOrders = saleOrderPage.getContent();

	    // Số trang tổng cộng
	    int totalPages = saleOrderPage.getTotalPages();
		
		model.addAttribute("saleOrders", saleOrders);
		model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);
	    
		
		BigDecimal total = BigDecimal.ZERO;
		for(SaleOrder saleOrder: saleOrders) {
			total = total.add(saleOrder.getTotal());
		}
		
		model.addAttribute("totalSales", total);
		
		return "backend/order-list";
	}
	
	@GetMapping(value = "/admin/order/delete/{saleOrderId}")
	public String deleteCategory(final Model model,
			@PathVariable("saleOrderId") int saleOrderId) {
		
		SaleOrder saleOrder = saleOrderService.getById(saleOrderId);
		saleOrder.setStatus(false);
		saleOrderService.saveOrUpdate(saleOrder);
		
		return "redirect:/admin/order/list";
	}
}
