package lt.moso.myoutlet.dao;

import java.util.List;

import lt.moso.myoutlet.entity.SubCategory;

public interface SubCategoryDAO{
	
	public List<SubCategory> getAllSubCategories();
	
	public List<SubCategory> getSubCategories(int theCategoryId);
	
	public SubCategory getSubCategory(int theSubCategoryId);
	
	public void saveSubCategory(SubCategory theSubCategory);
	
	public void deleteSubCategory(int theSubCategoryId);
	
	public void deleteSubCategories(int theCategoryId);
	
}
