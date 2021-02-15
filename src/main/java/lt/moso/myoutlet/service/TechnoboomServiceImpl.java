package lt.moso.myoutlet.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lt.moso.myoutlet.dao.CategoryDAO;
import lt.moso.myoutlet.dao.ImgDAO;
import lt.moso.myoutlet.dao.ProductDAO;
import lt.moso.myoutlet.dao.SubCategoryDAO;
import lt.moso.myoutlet.entity.Category;
import lt.moso.myoutlet.entity.Img;
import lt.moso.myoutlet.entity.Product;
import lt.moso.myoutlet.entity.SubCategory;

@Service
public class TechnoboomServiceImpl implements TechnoboomService {

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private ImgDAO imgDAO;

	// CategoryDAO Methods
	@Override
	@Transactional
	public List<Category> getCategories() {
		return categoryDAO.getCategories();
	}

	@Override
	@Transactional
	public Category getCategory(int theCategoryId) {
		return categoryDAO.getCategory(theCategoryId);
	}

	@Override
	@Transactional
	public void saveCategory(Category theCategory) {
		categoryDAO.saveCategory(theCategory);
	}

	@Override
	@Transactional
	public void deleteCategory(int theCategoryId) {
		categoryDAO.deleteCategory(theCategoryId);
	}

	// SubCategoryDAO Methods
	@Override
	@Transactional
	public List<SubCategory> getAllSubCategories() {
		return subCategoryDAO.getAllSubCategories();
	}

	@Override
	@Transactional
	public List<SubCategory> getSubCategories(int theCategoryId) {
		return subCategoryDAO.getSubCategories(theCategoryId);
	}

	@Override
	@Transactional
	public SubCategory getSubCategory(int theSubCategoryId) {
		return subCategoryDAO.getSubCategory(theSubCategoryId);
	}

	@Override
	@Transactional
	public void saveSubCategory(SubCategory theSubCategory) {
		subCategoryDAO.saveSubCategory(theSubCategory);
	}

	@Override
	@Transactional
	public void deleteSubCategories(int theCategoryId) {
		subCategoryDAO.deleteSubCategories(theCategoryId);
	}

	@Override
	@Transactional
	public void deleteSubCategory(int theSubCategoryId) {
		subCategoryDAO.deleteSubCategory(theSubCategoryId);
	}

	// ProductDAO Methods
	@Override
	@Transactional
	public List<Product> getAllProducts() {
		return productDAO.getAllProducts();
	}

	@Override
	@Transactional
	public List<Product> getProducts(int theSubCategoryId) {
		return productDAO.getProducts(theSubCategoryId);
	}

	@Override
	@Transactional
	public List<Product> searchProductByNameAndBrandAndSubCategory(String theProductName, int theSubCategoryId) {
		return productDAO.searchProductByNameAndBrandAndSubCategory(theProductName, theSubCategoryId);
	}

	@Override
	@Transactional
	public List<Product> searchAdvanced(String theProductName, String[] brands, int theSubCategoryId) {
		return productDAO.searchAdvanced(theProductName, brands, theSubCategoryId);
	}
	
	@Override
	@Transactional
	public List<String> getAllBrands() {
		return productDAO.getAllBrands();
	}

	@Override
	@Transactional
	public List<String> getBrandsBySubCategory(int theSubCategoryId) {
		return productDAO.getBrandsBySubCategory(theSubCategoryId);
	}

	@Override
	@Transactional
	public Product getProduct(int theProductId) {
		return productDAO.getProduct(theProductId);
	}

	@Override
	@Transactional
	public void saveProduct(Product theProduct) {
		theProduct.setUploadTime(new Date());
		productDAO.saveProduct(theProduct);
	}

	@Override
	@Transactional
	public void deleteProducts(int theSubCategoryId) {
		productDAO.deleteProducts(theSubCategoryId);
	}

	@Override
	@Transactional
	public void deleteProduct(int theProductId) {
		productDAO.deleteProduct(theProductId);
	}

	// ImgDAO Methods
	@Override
	@Transactional
	public List<Img> getImgs(int theProductId) {
		return imgDAO.getImgs(theProductId);
	}

	@Override
	@Transactional
	public List<Img> getFirstImgs() {
		return imgDAO.getFirstImgs();
	}

	@Override
	@Transactional
	public Img getImg(int theImgId) {
		return imgDAO.getImg(theImgId);
	}

	@Override
	@Transactional
	public void saveImg(MultipartFile file, Product theProduct) {
		Img theImg = new Img();
		try {
			if (file.getBytes().length != 0) {
				theImg.setImg(Base64.getEncoder().encodeToString(file.getBytes()));
				theImg.setProduct(theProduct);
				imgDAO.saveImg(theImg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void deleteImgs(int theProductId) {
		imgDAO.deleteImgs(theProductId);
	}

	@Override
	@Transactional
	public void deleteImg(int theImgId) {
		imgDAO.deleteImg(theImgId);
	}

	// Utility Methods
	@Override
	public List<Product> filterByPriceRange(List<Product> products, Double lower, Double upper) {
		return products
				.stream()
				.filter(p -> (p.getSalePrice() >= lower && p.getSalePrice() <= upper))
				.collect(Collectors.toList());
	}

	@Override
	public Double getHighestPrice(List<Product> products) {
		if (products.size() == 0) {
			return 0.0;
		}
		return products
				.stream()
				.mapToDouble(p -> p.getSalePrice())
				.max()
				.orElseThrow(NoSuchElementException::new);
	}

}
