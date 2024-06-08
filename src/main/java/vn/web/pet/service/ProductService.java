package vn.web.pet.service;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.web.pet.dto.Jw28Constant;
import vn.web.pet.dto.SearchModel;
import vn.web.pet.model.Product;
import vn.web.pet.model.ProductImage;
import vn.web.pet.model.User;

@Service
public class ProductService extends BaseService<Product> implements Jw28Constant {

	@Override
	public Class<Product> clazz() {
		// TODO Auto-generated method stub
		return Product.class;
	}
	
	public List<Product> findAllActive() {
		return super.executeNativeSql("SELECT * FROM tbl_product WHERE status=1");
	}
	
	@Transactional
    public int countNewOfMonuth() {
		LocalDate currentDate = LocalDate.now();
        
        // Lấy ra tháng và năm từ ngày hiện tại
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        
        String sql = "SELECT * " +
                "FROM tbl_product " +
                "WHERE MONTH(create_date) = " + currentMonth + " " +
                "AND YEAR(create_date) = " + currentYear;
        List<Product> products = super.executeNativeSql(sql); 
        int cnt = 0;
        if(products != null && !products.isEmpty())
        	cnt = products.size();
        return cnt;
    }
	
	public List<Product> searchByName(String keyWord) {
		keyWord = keyWord.toLowerCase();
		keyWord = keyWord.trim();
		String sql = "SELECT * FROM tbl_product p WHERE LOWER(p.name) LIKE '%" + keyWord + "%'";
		return super.executeNativeSql(sql);
	}
	
	public List<Product> searchByCategoryId(int categoryId) {
		String sql = "SELECT * FROM tbl_product p WHERE p.category_id = " + categoryId;
		return super.executeNativeSql(sql);
	}
//Search product
	
	public List<Product> searchProductByUser(int categoryId, String keyWord, String beginPriceRaw, String endPriceRaw){
		String sql = "SELECT * FROM tbl_product p WHERE 1=1";
		keyWord = keyWord.toLowerCase();
		keyWord = keyWord.trim();
		if(categoryId != 0)
			sql += " and category_id = '" + categoryId + "'";
		if(!keyWord.isEmpty()) {
			sql += " AND (LOWER(p.name) like '%" + keyWord + "%'" +
					" OR LOWER(p.short_description) like '%" + keyWord + "%'" +
					" OR LOWER(p.detail_description) like '%" + keyWord + "%'" +
					" OR LOWER(p.seo) like '%" + keyWord + "%')";
		}
		
		double beginPrice = 0, endPrice = 0, err = 0;
		try {
			beginPrice = Double.parseDouble(beginPriceRaw);
			endPrice = Double.parseDouble(endPriceRaw);
		} catch (Exception e) {
			// TODO: handle exception
			err = 1;
		}
		
		if(err == 0)
			sql += " AND p.sale_price BETWEEN " + beginPrice + " AND " + endPrice;
		
//		if(beginPrice >= 0 && endPrice >= 0) {
//			sql += " AND p.sale_price BETWEEN " + beginPrice + " AND " + endPrice;
//		}
		
		return super.executeNativeSql(sql);
	}
	
	public List<Product> searchProduct(SearchModel productSearch) {
		String sql = "SELECT * FROM tbl_product p WHERE 1=1";
		
		//Tim theo status
		if (productSearch.getStatus() != 2) {//Co chon active or inactive
			sql += " AND p.status=" + productSearch.getStatus();
		}
		
		//Tim them theo category
		if (productSearch.getCategoryId() != 0) {//Co chon category
			sql += " AND p.category_id=" + productSearch.getCategoryId();
		}
		
		//Tim them theo keyword
		
		if (productSearch.getKeyword() != null) {
			String keyword = productSearch.getKeyword().toLowerCase();
			sql += " AND (LOWER(p.name) like '%" + keyword + "%'" +
					" OR LOWER(p.short_description) like '%" + keyword + "%'" +
					" OR LOWER(p.seo) like '%" + keyword + "%')";
		}
		
		//Tim kiem tu ngay den ngay
		String beginDate = productSearch.getBeginDate();
		String endDate = productSearch.getEndDate();
		if (beginDate != null && endDate != null) {
			sql += " AND p.create_date BETWEEN '" + beginDate + "' AND '" + endDate + "'";
		}
		//System.out.println(sql);
		return super.executeNativeSql(sql);
	}
	
//Phuong thuc kiem tra (1) file co duoc upload khong?
	public boolean isUploadFile(MultipartFile file) {
		if (file == null || file.getOriginalFilename().isEmpty()) {
			return false; //Khong upload
		}
		return true; //Co upload
	}
	
//Phuong thuc kiem tra danh sach file co upload file nao khong?
	public boolean isUploadFiles(MultipartFile[] files) {
		if (files == null || files.length == 0) {
			return false; //Khong upload file
		}
		return true; //Co upload it nhat 1 file
	}
//------------------Save new product to database-------------------------------------------------
	@Transactional
	public Product saveAddProduct(Product product, MultipartFile avatarFile, 
			MultipartFile[] imageFiles) throws IOException {
		
		//Luu avatar file
		if (isUploadFile(avatarFile)) { //Co upload file avatar
			//Luu file vao thu muc Product/Avatar
			String path = FOLDER_UPLOAD + "Product/Avatar/" 
											+ avatarFile.getOriginalFilename();
			File file = new File(path);
			avatarFile.transferTo(file);
			//Luu duong dan cua avatar vao bang tbl_product
			product.setAvatar("Product/Avatar/" + avatarFile.getOriginalFilename());
		}
		//Luu images file
		if (isUploadFiles(imageFiles)) { //Co upload danh sach anh
			//Duyet danh sach file images
			for (MultipartFile imageFile : imageFiles) {
				if (isUploadFile(imageFile)) { //File co upload
					
					//Luu file vao thu muc Product/Image/
					String path = FOLDER_UPLOAD + "Product/Image/" + 
										imageFile.getOriginalFilename();
					File file = new File(path);
					imageFile.transferTo(file);
					
					//Luu duong dan cua cac image vao tbl_product_image
					ProductImage productImage = new ProductImage();
					productImage.setTitle(imageFile.getOriginalFilename());
					productImage.setPath("Product/Image/" 
										+ imageFile.getOriginalFilename());
					productImage.setStatus(Boolean.TRUE);
					productImage.setCreateDate(new Date());
					
					//luu duong dan anh sang bang tbl_product_image
					product.addRelationalProductImage(productImage); 
				}
			}
		}
		
		return super.saveOrUpdate(product);
	}

//----------------Save edit product-----------------------------------------------
	@Transactional
	public Product saveEditProduct(Product product, MultipartFile avatarFile, 
			MultipartFile[] imageFiles) throws IOException {
		
		//Lay product trong DB
		Product dbProduct = super.getById(product.getId());
		
		//Luu avatar file moi neu nguoi dung co upload avatar
		if (isUploadFile(avatarFile)) { //Co upload file avatar mới
			
			//Xoa avatar cu (Xoa file avatar)
			String path = FOLDER_UPLOAD + dbProduct.getAvatar();
			File file = new File(path);
			file.delete();
			
			//Luu file avatar moi vao thu muc Product/Avatar
			path = FOLDER_UPLOAD + "Product/Avatar/" + avatarFile.getOriginalFilename();
			file = new File(path);
			avatarFile.transferTo(file);
			//Luu duong dan cua avatar moi vao bang tbl_product
			product.setAvatar("Product/Avatar/" + avatarFile.getOriginalFilename());
		}
		else { //Nguoi dung khong upload avatar file
			//Giu nguyen avatar cu
			product.setAvatar(dbProduct.getAvatar());
		}
		//Luu images file
		if (isUploadFiles(imageFiles)) { //Co upload danh sach anh them 
			//Duyet danh sach file images
			for (MultipartFile imageFile : imageFiles) {
				if (isUploadFile(imageFile)) { //File co upload
					
					//Luu file vao thu muc Product/Image/
					String path = FOLDER_UPLOAD + "Product/Image/" + 
										imageFile.getOriginalFilename();
					File file = new File(path);
					imageFile.transferTo(file);
					
					//Luu duong dan vao tbl_product_image
					ProductImage productImage = new ProductImage();
					productImage.setTitle(imageFile.getOriginalFilename());
					productImage.setPath("Product/Image/" + 
											imageFile.getOriginalFilename());
					productImage.setStatus(Boolean.TRUE);
					productImage.setCreateDate(new Date());
					
					//luu (doi tuong product image) duong dan anh sang bang tbl_product_image
					product.addRelationalProductImage(productImage); 
				}
			}
		}
		
		return super.saveOrUpdate(product);
	}
//---------------------Delete product-----------------------
	
	@Autowired 
	private ProductImageService productImageService;
	
	@Transactional
	public void deleteProduct(Product product) {
		//+ Lay danh sach anh cua product trong tbl_product_image
		
		List<ProductImage> productImages = 
				productImageService.getProductImagesByProductId(product.getId());
		
		//Xoa lan luot cac anh cua product trong Product/Image va
		//Xoa lan luot cac duong dan anh trong tbl_product_image
		for (ProductImage productImage : productImages) {
			
			//Xoa file trong thu muc Product image (truoc)
			String path = FOLDER_UPLOAD + productImage.getPath();
			File file = new File(path);
			file.delete();
			
			//Xoa ban ghi thong tin anh trong tbl_product_image
//			product.removeRelationalProductImage(productImage); //restrict
		}
		
		//Xoa file avatar trong thu muc Product/Avatar
		String path = FOLDER_UPLOAD + product.getAvatar();
		File file = new File(path);
		file.delete();
		
		//Xoa product trong db
		super.delete(product);
	}
	

//-----------------------Search product----------------------------
//	public List<Product> searchProduct(SearchModel productSearch) {
//		//Tao cau lenh sql
//		String sql = "SELECT * FROM tbl_product p WHERE 1=1";
//		
//		//Tim kiem voi tieu chi status
//		if (productSearch.getStatus() != 2) { //Co chon Active/ Inactive
//			sql += " AND p.status=" + productSearch.getStatus();
//		}
//			
//		//Tim kiem voi tieu chi category
//		if (productSearch.getCategoryId() != 0) {
//			sql += " AND p.category_id=" + productSearch.getCategoryId();
//		}
//		
//		
////		//Tim kiem voi tieu chi keyword		
//		if (!StringUtils.isEmpty(productSearch.getKeyword())) {
//			
//			String keyword = productSearch.getKeyword().toLowerCase();
//			
//			sql += 	" AND (LOWER(p.name) like '%" + keyword + "%'" +
//					" OR LOWER(p.short_description) like '%" + keyword + "%'" +
//					" OR LOWER(p.seo) like '%" + keyword + "%')";					
//		}
//		
//		//Tim kiem voi ngay thang
//		if (!StringUtils.isEmpty(productSearch.getBeginDate()) &&
//				!StringUtils.isEmpty(productSearch.getEndDate())) {
//			
//			String beginDate = productSearch.getBeginDate();
//			String endDate = productSearch.getEndDate();
//			
//			sql += " AND p.create_date BETWEEN '" + beginDate + "' AND '" + endDate + "'";
//		}	
//		
//		return super.executeNativeSql(sql);
//	}	
////		System.out.println("sql: " + sql);
////		return super.executeNativeSql(sql, productSearch.getCurrentPage(), productSearch.getSizeOfPage());
//	}
	
	
}
