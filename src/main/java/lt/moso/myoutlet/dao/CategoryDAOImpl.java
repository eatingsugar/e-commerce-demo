package lt.moso.myoutlet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lt.moso.myoutlet.entity.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SubCategoryDAO subCategoryDAO;

	@Override
	public List<Category> getCategories() {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<Category> query = currentSession.createQuery("from Category ORDER BY name ASC", Category.class);
		List<Category> categories = query.getResultList();
		return categories;
	}

	@Override
	public Category getCategory(int theCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return currentSession.get(Category.class, theCategoryId);
	}

	@Override
	public void saveCategory(Category theCategory) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		currentSession.saveOrUpdate(theCategory);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteCategory(int theCategoryId) {

		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		// delete all subCategories that have category_id theId
		subCategoryDAO.deleteSubCategories(theCategoryId);

		// delete all categories that have id theId
		Query query = currentSession.createQuery("delete from Category where id=:categoryId");
		query.setParameter("categoryId", theCategoryId);
		query.executeUpdate();
	}

}
