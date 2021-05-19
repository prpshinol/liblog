package com.liblog.entity;

import java.sql.Timestamp;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Follow entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "follow")
public class Follow implements java.io.Serializable {

	// Fields

	private FollowId id;
	private User userByFansId;
	private User userByUserId;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Follow() {
	}

	/** minimal constructor */
	public Follow(FollowId id, User userByFansId, User userByUserId) {
		this.id = id;
		this.userByFansId = userByFansId;
		this.userByUserId = userByUserId;
	}

	/** full constructor */
	public Follow(FollowId id, User userByFansId, User userByUserId,
			Timestamp createTime) {
		this.id = id;
		this.userByFansId = userByFansId;
		this.userByUserId = userByUserId;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "fansId", column = @Column(name = "fans_id", nullable = false)) })
	public FollowId getId() {
		return this.id;
	}

	public void setId(FollowId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fans_id", nullable = false, insertable = false, updatable = false)
	public User getUserByFansId() {
		return this.userByFansId;
	}

	public void setUserByFansId(User userByFansId) {
		this.userByFansId = userByFansId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public User getUserByUserId() {
		return this.userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}