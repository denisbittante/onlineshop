package de.java2enterprise.onlineshop.model;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
	private static final long serialVersionUID = -6604363993041715170L;

	private Float avgStar;
	private Long buyer;
	private Integer cntReview;
	private String description;
	private byte[] foto;
	private Long id;
	private Double price;
	private Long seller;
	private Date sold;
	private String title;

	public Item() {
	}

	public Item(String title, String description, Double price, Long seller, Integer cntReview, Float avgStar) {
		this.title = title;
		this.description = description;
		this.price = price;
		this.seller = seller;
		this.cntReview = cntReview;
		this.avgStar = avgStar;
	}

	public Float getAvgStar() {
		return avgStar;
	}

	public Long getBuyer() {
		return this.buyer;
	}

	public Integer getCntReview() {
		return cntReview;
	}

	public String getDescription() {
		return description;
	}

	public byte[] getFoto() {
		return foto;
	}

	public Long getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public Long getSeller() {
		return seller;
	}

	public Date getSold() {
		return sold;
	}

	public String getTitle() {
		return title;
	}

	public void setAvgStar(Float avgStar) {
		this.avgStar = avgStar;
	}

	public void setBuyer(Long buyer) {
		this.buyer = buyer;
	}

	public void setCntReview(Integer cntReview) {
		this.cntReview = cntReview;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setSeller(Long seller) {
		this.seller = seller;
	}

	public void setSold(Date sold) {
		this.sold = sold;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "[" + getId() + "," + getTitle() + "," + getDescription() + "," + getPrice() + "," + getSeller() + ","
				+ getBuyer() + "," + getSold() + "]";
	}

}
