package de.java2enterprise.onlineshop;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the VALUATION database table.
 * 
 */
public class Valuation implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private Long productId;

	private Integer stars;

	private Date timeSubmitted;

	private Long userId;

	private String valuationcomment;

	public Valuation() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Date getTimeSubmitted() {
		return timeSubmitted;
	}

	public void setTimeSubmitted(Date timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getValuationcomment() {
		return valuationcomment;
	}

	public void setValuationcomment(String valuationcomment) {
		this.valuationcomment = valuationcomment;
	}

}