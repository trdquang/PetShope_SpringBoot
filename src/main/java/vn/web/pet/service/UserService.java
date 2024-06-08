package vn.web.pet.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.web.pet.model.Product;
import vn.web.pet.model.User;

@Service
public class UserService extends BaseService<User> {
	
	@Override
	public Class<User> clazz() {
		return User.class;
	}
	
	public List<User> findAllActive() {
		return super.executeNativeSql("SELECT * FROM tbl_user WHERE status=1");
	}
	
	@Transactional
	public void deleteUserById(int id) {
		super.deleteById(id);
	}
	
	@Transactional
    public int countNewOfMonuth() {
		LocalDate currentDate = LocalDate.now();
        
        // Lấy ra tháng và năm từ ngày hiện tại
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        
        String sql = "SELECT * " +
                "FROM tbl_user " +
                "WHERE MONTH(create_date) = " + currentMonth + " " +
                "AND YEAR(create_date) = " + currentYear;
        
        List<User> users = super.executeNativeSql(sql); 
        int cnt = 0;
        if(users != null && !users.isEmpty())
        	cnt = users.size();
        return cnt;
    }
}
