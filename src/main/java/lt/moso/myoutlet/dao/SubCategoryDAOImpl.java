package lt.moso.myoutlet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lt.moso.myoutlet.entity.SubCategory;

@Repository
public class SubCategoryDAOImpl implements SubCategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProductDAO productDAO;

	@Override
	public List<SubCategory> getAllSubCategories() {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<SubCategory> query = currentSession.createQuery("from SubCategory ORDER BY name ASC", SubCategory.class);
		List<SubCategory> subCategories = query.getResultList();
		return subCategories;
	}

	@Override
	public List<SubCategory> getSubCategories(int theCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<SubCategory> query = currentSession.createQuery("from SubCategory where category_id=:theCategoryId",
				SubCategory.class);
		query.setParameter("theCategoryId", theCategoryId);
		List<SubCategory> subCategories = query.getResultList();
		return subCategories;
	}

	@Override
	public SubCategory getSubCategory(int theSubCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return currentSession.get(SubCategory.class, theSubCategoryId);
	}

	@Override
	public void saveSubCategory(SubCategory theSubCategory) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		currentSession.saveOrUpdate(theSubCategory);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteSubCategory(int theSubCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		productDAO.deleteProducts(theSubCategoryId);
		Query query = currentSession.createQuery("delete from SubCategory where id=:theSubCategoryId");
		query.setParameter("theSubCategoryId", theSubCategoryId);
		query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteSubCategories(int theCategoryId) {

		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		// delete all products from all sub-categories that have category_id theCategoryId
		Query<SubCategory> query1 = currentSession.createQuery("from SubCategory where category_id=:theCategoryId",
				SubCategory.class);
		query1.setParameter("theCategoryId", theCategoryId);
		List<SubCategory> subCategories = query1.getResultList();

		if (!subCategories.isEmpty()) {
			for (SubCategory tempSubCategory : subCategories) {
				productDAO.deleteProducts(tempSubCategory.getId());
			}
		}

		// delete all sub-categories that have category_id theId
		Query query2 = currentSession.createQuery("delete from SubCategory where category_id=:theCategoryId");
		query2.setParameter("theCategoryId", theCategoryId);
		query2.executeUpdate();
	}

}
