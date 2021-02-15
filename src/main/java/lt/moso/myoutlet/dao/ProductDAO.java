package lt.moso.myoutlet.dao;

import java.util.List;

import lt.moso.myoutlet.entity.Product;

public interface ProductDAO {

	public List<Product> getAllProducts();

	public List<Product> getProducts(int theSubCategoryId);

	public List<Product> searchAdvanced(String theProductName, String[] brands, int theSubCategoryId);
	
	public List<Product> searchProductByNameAndBrandAndSubCategory(String theProductName, int theSubCategoryId);
	
	public List<String> getAllBrands();

	public List<String> getBrandsBySubCategory(int theSubCategoryId);

	public Product getProduct(int theProductId);

	public void saveProduct(Product theProduct);

	public void deleteProduct(int theProductId);

	public void deleteProducts(int theSubCategoryId);

}
