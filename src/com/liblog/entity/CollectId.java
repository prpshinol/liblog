package com.liblog.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CollectId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class CollectId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer bookId;

	// Constructors

	/** default constructor */
	public CollectId() {
	}

	/** full constructor */
	public CollectId(Integer userId, Integer bookId) {
		this.userId = userId;
		this.bookId = bookId;
	}

	// Property accessors

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "book_id", nullable = false)
	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CollectId))
			return false;
		CollectId castOther = (CollectId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getBookId() == castOther.getBookId()) || (this
						.getBookId() != null && castOther.getBookId() != null && this
						.getBookId().equals(castOther.getBookId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getBookId() == null ? 0 : this.getBookId().hashCode());
		return result;
	}

}