package lt.moso.myoutlet.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import lt.moso.myoutlet.entity.Img;

@Repository
public class ImgDAOImpl implements ImgDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Img> getImgs(int theProductId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query<Img> query = currentSession.createQuery("from Img where product_id=:theProductId", Img.class);
		query.setParameter("theProductId", theProductId);
		List<Img> imgs = query.getResultList();
		return imgs;
	}

	@Override
	public List<Img> getFirstImgs() {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		
		// get all images
		Query<Img> query = currentSession.createQuery("from Img", Img.class);
		List<Img> allImgs = query.getResultList();
		
		// filter only first images by product_id
		List<Img> firstImgs = new ArrayList<>();
		List<Integer> productIds = new ArrayList<>();
		for (Img tempImg : allImgs) {
			if (!productIds.contains(tempImg.getId())) {
				firstImgs.add(tempImg);
				productIds.add(tempImg.getId());
			}
		}
		
		return firstImgs;
	}
	
	@Override
	public Img getImg(int theImgId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		return currentSession.get(Img.class, theImgId);
	}

	@Override
	public void saveImg(Img theImg) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		currentSession.saveOrUpdate(theImg);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteImg(int theImgId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query query = currentSession.createQuery("delete from Img where id=:theImgId");
		query.setParameter("theImgId", theImgId);
		query.executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteImgs(int theProductId) {
		Session currentSession = null;
		if (entityManager == null || (currentSession = entityManager.unwrap(Session.class)) == null) {
			throw new NullPointerException("factory is not a hibernate factory");
		}
		Query query = currentSession.createQuery("delete from Img where product_id=:theProductId");
		query.setParameter("theProductId", theProductId);
		query.executeUpdate();
	}

	

}
