package lt.moso.myoutlet.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "brand")
	private String brand;

	@Column(name = "price")
	private Double price;

	@Column(name = "sale_price")
	private Double salePrice;

	@Column(name = "sale_percent")
	private Double salePercent;

	@Column(name = "active")
	private int active;

	@Column(name = "detail")
	private String detail;

	@Column(name = "upload_time")
	private Date uploadTime;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "sub_category_id", referencedColumnName = "id")
	private SubCategory subCategory;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Img> images;

	public Product() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		if (salePrice != null) {
			this.salePrice = salePrice;
		} else {
			this.salePrice = 0D;
		}
	}

	public Double getSalePercent() {
		return salePercent;
	}

	public void setSalePercent(Double salePercent) {
		this.salePercent = salePercent;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public List<Img> getImages() {
		return images;
	}

	public void setImages(List<Img> images) {
		this.images = images;
	}

}
