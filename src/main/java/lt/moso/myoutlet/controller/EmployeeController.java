package lt.moso.myoutlet.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lt.moso.myoutlet.entity.Category;
import lt.moso.myoutlet.entity.Img;
import lt.moso.myoutlet.entity.Product;
import lt.moso.myoutlet.entity.SubCategory;
import lt.moso.myoutlet.service.TechnoboomService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private TechnoboomService technoboomService;

	public EmployeeController(TechnoboomService theTechnoboomService) {
		technoboomService = theTechnoboomService;
	}

	// LIST METHODS
	@GetMapping("/listCategories")
	public String listCategories(Model model) {
		List<Category> categories = technoboomService.getCategories();
		model.addAttribute("categories", categories);
		model.addAttribute("allCategories", categories);
		return "employees/list-categories";
	}

	@GetMapping("/searchSubCategories")
	public String listCategories(@RequestParam("categoryId") int theCategoryId, Model model) {
		List<Category> allCategories = technoboomService.getCategories();
		if (theCategoryId != 0) {
			Category category = technoboomService.getCategory(theCategoryId);
			model.addAttribute("categories", category);
		} else {
			model.addAttribute("categories", allCategories);
		}
		model.addAttribute("allCategories", allCategories);
		return "/employees/list-categories";
	}

	@GetMapping("/listProducts")
	public String listProducts(Model model) {
		List<Product> products = technoboomService.getAllProducts();
		List<SubCategory> subCategories = technoboomService.getAllSubCategories();
		model.addAttribute("products", products);
		model.addAttribute("subCategories", subCategories);
		return "employees/list-products";
	}

	@GetMapping("/searchProducts")
	public String searchProduct(@RequestParam("productName") String theProductName,
			@RequestParam("subCategoryId") int theSubCategoryId, Model model) {
		List<Product> products = technoboomService.searchProductByNameAndBrandAndSubCategory(theProductName,
				theSubCategoryId);
		List<SubCategory> subCategories = technoboomService.getAllSubCategories();
		model.addAttribute("products", products);
		model.addAttribute("subCategories", subCategories);
		return "/employees/list-products";
	}

	// ADD METHODS
	@GetMapping("/category/showFormForAdd")
	public String showFormForAddCategory(Model model) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "employees/form-category";
	}

	@GetMapping("/subCategory/showFormForAdd")
	public String showFormForAddSubCategory(Model model) {
		SubCategory subCategory = new SubCategory();
		List<Category> categories = technoboomService.getCategories();
		model.addAttribute("subCategory", subCategory);
		model.addAttribute("categories", categories);
		return "employees/form-sub-category";
	}

	@GetMapping("/product/showFormForAdd")
	public String showFormForAddProduct(Model model) {
		Product product = new Product();
		List<SubCategory> subCategories = technoboomService.getAllSubCategories();
		List<Img> productImages = new ArrayList<>();
		model.addAttribute("product", product);
		model.addAttribute("productImages", productImages);
		model.addAttribute("subCategories", subCategories);
		return "employees/form-product";
	}

	// UPDATE METHODS
	@GetMapping("/category/showFormForUpdate")
	public String showFormForUpdateCategory(@RequestParam("categoryId") int theCategoryId, Model model) {
		Category category = technoboomService.getCategory(theCategoryId);
		model.addAttribute("category", category);
		return "employees/form-category";
	}

	@GetMapping("/subCategory/showFormForUpdate")
	public String showFormForUpdateSubCategory(@RequestParam("subCategoryId") int theSubCategoryId, Model model) {
		SubCategory subCategory = technoboomService.getSubCategory(theSubCategoryId);
		List<Category> categories = technoboomService.getCategories();
		model.addAttribute("subCategory", subCategory);
		model.addAttribute("categories", categories);
		return "employees/form-sub-category";
	}

	@GetMapping("/product/showFormForUpdate")
	public String showFormForUpdateProduct(@RequestParam("productId") int theProductId, Model model) {
		Product product = technoboomService.getProduct(theProductId);
		List<SubCategory> subCategories = technoboomService.getAllSubCategories();
		List<Img> productImages = technoboomService.getImgs(theProductId);
		model.addAttribute("product", product);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("productImages", productImages);
		return "employees/form-product";
	}

	// SAVE METHODS
	@PostMapping("/category/save")
	public String saveCategory(@ModelAttribute("category") Category theCategory) {
		technoboomService.saveCategory(theCategory);
		return "redirect:/employees/listCategories";
	}

	@PostMapping("/subCategory/save")
	public String saveSubCategory(@ModelAttribute("subCategory") SubCategory theSubCategory) {
		technoboomService.saveSubCategory(theSubCategory);
		return "redirect:/employees/listCategories";
	}

	@PostMapping("/product/save")
	public String saveProduct(@RequestParam("photos") MultipartFile[] files,
			@ModelAttribute("product") Product theProduct) throws IOException {

		Double price = theProduct.getPrice();
		Double salePrice = theProduct.getSalePrice();

		if (salePrice == 0) {
			theProduct.setSalePrice(price);
			theProduct.setSalePercent(0D);
		} else {
			theProduct.setSalePercent((price - salePrice) / price * 100D);
		}

		technoboomService.saveProduct(theProduct);
		for (MultipartFile tempFile : files) {
			technoboomService.saveImg(tempFile, theProduct);
		}
		return "redirect:/employees/listProducts";
	}

	// DELETE METHODS
	@GetMapping("/category/delete")
	public String deleteCategory(@RequestParam("categoryId") int theCategoryId) {
		technoboomService.deleteCategory(theCategoryId);
		return "redirect:/employees/listCategories";
	}

	@GetMapping("/subCategory/delete")
	public String deleteSubCategory(@RequestParam("subCategoryId") int theSubCategoryId) {
		technoboomService.deleteSubCategory(theSubCategoryId);
		return "redirect:/employees/listCategories";
	}

	@GetMapping("/product/delete")
	public String deleteProduct(@RequestParam("productId") int theProductId) {
		technoboomService.deleteProduct(theProductId);
		return "redirect:/employees/listProducts";
	}

	@GetMapping("/image/delete")
	public String deleteImg(@RequestParam("imageId") int theImgId, Model model) {

		Product product = technoboomService.getImg(theImgId).getProduct();
		List<SubCategory> subCategories = technoboomService.getAllSubCategories();

		technoboomService.deleteImg(theImgId);

		List<Img> productImages = technoboomService.getImgs(product.getId());

		model.addAttribute("product", product);
		model.addAttribute("subCategories", subCategories);
		model.addAttribute("productImages", productImages);
		return "redirect:/employees/product/showFormForUpdate?productId=" + product.getId();
	}

	// OTHER
	@GetMapping("")
	public String listProductsRedirect() {
		return "redirect:/employees/listProducts";
	}

}
