package lt.moso.myoutlet.dao;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lt.moso.myoutlet.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ImgDAO imgDAO;

	@Override
	public List<Product> getAllProducts() {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<Product> query = currentSession.createQuery("from Product ORDER BY uploadTime DESC", Product.class);
		List<Product> products = query.getResultList();
		return products;
	}

	@Override
	public List<Product> getProducts(int theSubCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<Product> query = currentSession.createQuery(
				"from Product where sub_category_id=:theSubCategoryId ORDER BY uploadTime DESC", Product.class);
		query.setParameter("theSubCategoryId", theSubCategoryId);
		List<Product> products = query.getResultList();
		return products;
	}

	public List<Product> searchAdvanced(String theProductName, String[] brands, int theSubCategoryId) {
		List<Product> products = searchProductByNameAndBrandAndSubCategory(theProductName, theSubCategoryId);

		if (brands != null) {
			List<String> brandsList = Arrays.asList(brands);
			products = products.stream().filter(p -> brandsList.contains(p.getBrand())).collect(Collectors.toList());
			return products;
		}
		return products;
	}

	@Override
	public List<Product> searchProductByNameAndBrandAndSubCategory(String theProductName, int theSubCategoryId) {

		if (theSubCategoryId != 0) {
			List<Product> products = getProducts(theSubCategoryId);
			if (theProductName.length() != 0) {
				products = products.stream()
						.filter(p -> p.getName().toLowerCase().contains(theProductName.toLowerCase())
								|| p.getBrand().toLowerCase().contains(theProductName.toLowerCase()))
						.collect(Collectors.toList());
				return products; // products by subCategoryId and productName/brandName
			}
			return products; // products by subCategory only
		}

		List<Product> products = getAllProducts();

		if (theProductName.length() != 0) {
			products = products.stream()
					.filter(p -> p.getName().toLowerCase().contains(theProductName.toLowerCase())
							|| p.getBrand().toLowerCase().contains(theProductName.toLowerCase()))
					.collect(Collectors.toList());
			return products; // products by productName/brandName only
		}

		return products; // all products
	}
	
	@Override
	public List<String> getAllBrands() {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<String> query = currentSession.createQuery("SELECT brand FROM Product GROUP BY brand", String.class);
		List<String> brands = query.getResultList();
		return brands;
	}

	@Override
	public List<String> getBrandsBySubCategory(int theSubCategoryId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<String> query = currentSession
				.createQuery("SELECT brand FROM Product WHERE sub_category_id=:theSubCategoryId GROUP BY brand",
						String.class);
		query.setParameter("theSubCategoryId", theSubCategoryId);
		List<String> brands = query.getResultList();
		return brands;
	}

	@Override
	public Product getProduct(int theProductId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return currentSession.get(Product.class, theProductId);
	}

	@Override
	public void saveProduct(Product theProduct) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		currentSession.saveOrUpdate(theProduct);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteProduct(int theProductId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		imgDAO.deleteImgs(theProductId);
		Query query = currentSession.createQuery("delete from Product where id=:theProductId");
		query.setParameter("theProductId", theProductId);
		query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteProducts(int theSubCategoryId) {

		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}

		// delete all images from all products that have sub_category_id
		// theSubCategoryId
		Query<Product> query1 = currentSession.createQuery("from Product where sub_category_id=:theSubCategoryId",
				Product.class);
		query1.setParameter("theSubCategoryId", theSubCategoryId);
		List<Product> products = query1.getResultList();

		if (!products.isEmpty()) {
			for (Product tempProduct : products) {
				imgDAO.deleteImgs(tempProduct.getId());
			}
		}

		Query query = currentSession.createQuery("delete from Product where sub_category_id=:theSubCategoryId");
		query.setParameter("theSubCategoryId", theSubCategoryId);
		query.executeUpdate();
	}

}
