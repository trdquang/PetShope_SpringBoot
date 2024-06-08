package vn.web.pet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.web.pet.model.ProductImage;

@Service
public class ProductImageService extends BaseService<ProductImage> {

	@Override
	public Class<ProductImage> clazz() {
		// TODO Auto-generated method stub
		return ProductImage.class;
	}
	
	public List<ProductImage> getProductImagesByProductId(int productId) {
		String sql = "SELECT * FROM tbl_product_image WHERE product_id=" + productId;
		return super.executeNativeSql(sql);
	}
}
