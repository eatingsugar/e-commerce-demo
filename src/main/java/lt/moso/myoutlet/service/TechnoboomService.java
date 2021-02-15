package lt.moso.myoutlet.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lt.moso.myoutlet.entity.Category;
import lt.moso.myoutlet.entity.Img;
import lt.moso.myoutlet.entity.Product;
import lt.moso.myoutlet.entity.SubCategory;

public interface TechnoboomService {

	// CategoryDAO Methods
	public List<Category> getCategories(); // GET all categories

	public Category getCategory(int theCategoryId); // GET category by id

	public void saveCategory(Category theCategory); // SAVE category

	public void deleteCategory(int theCategoryId); // DELETE category by id

	// SubCategoryDAO Methods
	public List<SubCategory> getAllSubCategories(); // GET all subCategories

	public List<SubCategory> getSubCategories(int theCategoryId); // GET subCategories by category_id

	public SubCategory getSubCategory(int theSubCategoryId); // GET subCategory by id

	public void saveSubCategory(SubCategory theSubCategory); // SAVE subCategory

	public void deleteSubCategories(int theCategoryId); // DELETE subCategories by category_id

	public void deleteSubCategory(int theSubCategoryId); // DELETE subCategory by id

	// ProductDAO Methods
	public List<Product> getAllProducts(); // GET all products

	public List<Product> getProducts(int theSubCategoryId); // GET products by sub_category_id

	public List<Product> searchProductByNameAndBrandAndSubCategory(String theProductName, int theSubCategoryId); 
					// GET products that contain searched productName and selected subCategory
	
	public List<Product> searchAdvanced(String theProductName, String[] brands, int theSubCategoryId);
					// GET products that contain searched productName and selected subCategory and selected brands
	
	public List<String> getAllBrands(); // GET all unique brand names
	
	public List<String> getBrandsBySubCategory(int theSubCategoryId); // GET all unique brand names from given subCategory
	
	public Product getProduct(int theProductId); // GET product by id

	public void saveProduct(Product product); // SAVE product

	public void deleteProducts(int theSubCategoryId); // DELETE products by sub_category_id

	public void deleteProduct(int theProductId); // DELETE product by id

	// ImageDAO Methods
	public List<Img> getImgs(int theProductId); // GET images by product_id

	public List<Img> getFirstImgs(); // GET first image of all products

	public Img getImg(int theImgId); // GET image by id

	public void saveImg(MultipartFile file, Product theProduct); // SAVE image

	public void deleteImgs(int theProductId); // DELETE images by product_id

	public void deleteImg(int theImgId); // DELETE image by id
	
	// Utility Methods
	public List<Product> filterByPriceRange(List<Product> products, Double lower, Double upper);
					// GET result products from given products filtered by price 

	public Double getHighestPrice(List<Product> products);
					// GET the highest price from given products

}
