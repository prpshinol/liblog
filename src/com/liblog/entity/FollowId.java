package com.liblog.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FollowId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class FollowId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer fansId;

	// Constructors

	/** default constructor */
	public FollowId() {
	}

	/** full constructor */
	public FollowId(Integer userId, Integer fansId) {
		this.userId = userId;
		this.fansId = fansId;
	}

	// Property accessors

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "fans_id", nullable = false)
	public Integer getFansId() {
		return this.fansId;
	}

	public void setFansId(Integer fansId) {
		this.fansId = fansId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FollowId))
			return false;
		FollowId castOther = (FollowId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getFansId() == castOther.getFansId()) || (this
						.getFansId() != null && castOther.getFansId() != null && this
						.getFansId().equals(castOther.getFansId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getFansId() == null ? 0 : this.getFansId().hashCode());
		return result;
	}

}