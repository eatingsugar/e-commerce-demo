package lt.moso.myoutlet.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lt.moso.myoutlet.entity.Category;
import lt.moso.myoutlet.entity.Product;
import lt.moso.myoutlet.entity.SubCategory;
import lt.moso.myoutlet.service.TechnoboomService;

@Controller
public class HomeController {

	private TechnoboomService technoboomService;

	public HomeController(TechnoboomService theTechnoboomService) {
		technoboomService = theTechnoboomService;
	}

	// HOME METHODS
	@GetMapping("/")
	public String showHome(Model model) {
		List<Category> categories = technoboomService.getCategories();
		List<Product> allProducts = technoboomService.getAllProducts();
		model.addAttribute("products", allProducts);
		model.addAttribute("categories", categories);
		return "landing/home";
	}

	@GetMapping("/searchProducts")
	public String searchProducts(@RequestParam("productName") String theProductName, Model model) {
		List<Category> categories = technoboomService.getCategories();
		List<Product> products = technoboomService.searchProductByNameAndBrandAndSubCategory(theProductName, 0);
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("searchedText", theProductName);
		return "landing/home";
	}

	// SUB-CATEGORY PRODUCTS METHODS
	@GetMapping("/listProducts")
	public String listProducts(@RequestParam("subCategoryId") int theSubCategoryId, Model model) {
		
		List<Category> categories = technoboomService.getCategories();
		List<Product> products = technoboomService.getProducts(theSubCategoryId);
		List<String> brands = technoboomService.getBrandsBySubCategory(theSubCategoryId);
		SubCategory theSubCategory = technoboomService.getSubCategory(theSubCategoryId);
		Category theCategory = theSubCategory.getCategory();
		Double highestPrice = technoboomService.getHighestPrice(products);
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("brands", brands);
		model.addAttribute("category", theCategory);
		model.addAttribute("subCategory", theSubCategory);
		model.addAttribute("highestPrice", highestPrice);
		return "landing/list-products";
	}

	@GetMapping("/advancedSearchProducts")
	public String advancedSearchProducts(
			@RequestParam(name = "selectedBrands", required = false) String[] selectedBrands,
			@RequestParam(name = "lowerBound", required = false) Double lowerBound,
			@RequestParam(name = "upperBound", required = false) Double upperBound,
			@RequestParam("subCategoryId") int theSubCategoryId,
			@RequestParam("productName") String theProductName, Model model) {
		
		List<Category> categories = technoboomService.getCategories();
		List<Product> products = technoboomService.searchAdvanced(theProductName, selectedBrands, theSubCategoryId);
		List<String> brands = technoboomService.getBrandsBySubCategory(theSubCategoryId);
		SubCategory theSubCategory = technoboomService.getSubCategory(theSubCategoryId);
		Category theCategory = theSubCategory.getCategory();
		Double highestPrice = technoboomService.getHighestPrice(products);
		
		if (lowerBound == null || !(lowerBound >= 0D)) {
			lowerBound = 0D;
		}
		if (upperBound == null || !(upperBound >= 0D) || upperBound >= highestPrice) {
			upperBound = highestPrice;
		}
		products = technoboomService.filterByPriceRange(products, lowerBound, upperBound);
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("brands", brands);
		model.addAttribute("category", theCategory);
		model.addAttribute("subCategory", theSubCategory);
		model.addAttribute("searchedText", theProductName);
		model.addAttribute("selectedBrands", selectedBrands);
		model.addAttribute("highestPrice", highestPrice);
		model.addAttribute("lowerBound", lowerBound);
		model.addAttribute("upperBound", upperBound);
		return "landing/list-products";
	}

	@GetMapping("/showProduct")
	public String showProduct(@RequestParam("productId") int theProductId, Model model) {
		
		List<Category> categories = technoboomService.getCategories();
		Product product = technoboomService.getProduct(theProductId);
		SubCategory theSubCategory = product.getSubCategory();
		Category theCategory = theSubCategory.getCategory();
		List<String> brands = technoboomService.getBrandsBySubCategory(theSubCategory.getId());
		
		model.addAttribute("categories", categories);
		model.addAttribute("product", product);
		model.addAttribute("category", theCategory);
		model.addAttribute("subCategory", theSubCategory);
		model.addAttribute("brands", brands);
		return "landing/show-product";
	}
	
}
