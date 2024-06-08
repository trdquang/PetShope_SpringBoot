package vn.web.pet.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.web.pet.model.Category;

@Service
public class CategoryService extends BaseService<Category> {

	@Override
	public Class<Category> clazz() {
		// TODO Auto-generated method stub
		return Category.class;
	}
	
	public List<Category> findAllActive() {
		String sql = "SELECT * FROM tbl_category WHERE status=1";
		return super.executeNativeSql(sql);
	}
	
	@Transactional
	public void deleteCategoryById(int categoryId) {
		super.deleteById(categoryId);
	}
	
}
