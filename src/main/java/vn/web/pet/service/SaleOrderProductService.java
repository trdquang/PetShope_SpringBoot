package vn.web.pet.service;

import org.springframework.stereotype.Service;

import vn.web.pet.model.SaleOrderProduct;

@Service
public class SaleOrderProductService extends BaseService<SaleOrderProduct> {

	@Override
	public Class<SaleOrderProduct> clazz() {
		// TODO Auto-generated method stub
		return SaleOrderProduct.class;
	}
}
