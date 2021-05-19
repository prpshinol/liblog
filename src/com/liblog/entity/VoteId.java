package com.liblog.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VoteId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VoteId implements java.io.Serializable {

	// Fields

	private Integer bookId;
	private Integer userId;

	// Constructors

	/** default constructor */
	public VoteId() {
	}

	/** full constructor */
	public VoteId(Integer bookId, Integer userId) {
		this.bookId = bookId;
		this.userId = userId;
	}

	// Property accessors

	@Column(name = "book_id", nullable = false)
	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VoteId))
			return false;
		VoteId castOther = (VoteId) other;

		return ((this.getBookId() == castOther.getBookId()) || (this
				.getBookId() != null && castOther.getBookId() != null && this
				.getBookId().equals(castOther.getBookId())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBookId() == null ? 0 : this.getBookId().hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}