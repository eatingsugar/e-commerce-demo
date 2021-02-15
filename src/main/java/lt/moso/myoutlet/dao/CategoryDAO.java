package lt.moso.myoutlet.dao;

import java.util.List;

import lt.moso.myoutlet.entity.Category;

public interface CategoryDAO{
	
	public List<Category> getCategories();
	
	public Category getCategory(int theCategoryId);
	
	public void saveCategory(Category theCategory);
	
	public void deleteCategory(int theCategoryId);
	
}
