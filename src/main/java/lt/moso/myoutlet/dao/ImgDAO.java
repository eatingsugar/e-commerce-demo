package lt.moso.myoutlet.dao;

import java.util.List;

import lt.moso.myoutlet.entity.Img;

public interface ImgDAO {

	public List<Img> getImgs(int theProductId);

	public List<Img> getFirstImgs();

	public Img getImg(int theImgId);

	public void saveImg(Img theImg);

	public void deleteImg(int theImgId);

	public void deleteImgs(int theProductId);

}
