package vn.web.pet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.web.pet.model.Product;
import vn.web.pet.model.SaleOrder;


@Service
public class SaleOrderService extends BaseService<SaleOrder> {
	
	@Override
	public Class<SaleOrder> clazz() {
		// TODO Auto-generated method stub
		return SaleOrder.class;
	}
	
	@Transactional
	public SaleOrder saveOrder(SaleOrder saleOrder) {
		return super.saveOrUpdate(saleOrder);
	}
	
	@Transactional
    public BigDecimal countNewOfMonuth() {
		LocalDate currentDate = LocalDate.now();
        
        // Lấy ra tháng và năm từ ngày hiện tại
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        
        String sql = "SELECT * " +
                "FROM tbl_sale_order " +
                "WHERE MONTH(create_date) = " + currentMonth + " " +
                "AND YEAR(create_date) = " + currentYear;
        List<SaleOrder> saleOrders = super.executeNativeSql(sql); 
        BigDecimal sum = BigDecimal.ZERO;
        for(SaleOrder item: saleOrders) {
        	sum = item.getTotal().add(sum);
        }
        
        return sum;
    }
	
	public List<SaleOrder> findAllActive() {
		return super.executeNativeSql("SELECT * FROM tbl_sale_order WHERE status=1");
	}
}
