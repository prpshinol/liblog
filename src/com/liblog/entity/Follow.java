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
@Table(name = "follow", catalog = "liblog")
public class Follow implements java.io.Serializable {

	// Fields

	private FollowId id;
	private User fans;
	private User user;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Follow() {
	}

	/** minimal constructor */
	public Follow(FollowId id, User fans, User user) {
		this.id = id;
		this.fans = fans;
		this.user = user;
	}

	/** full constructor */
	public Follow(FollowId id, User fans, User user,
				  Timestamp createTime) {
		this.id = id;
		this.fans = fans;
		this.user = user;
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
	public User getFans() {
		return this.fans;
	}

	public void setFans(User userByFansId) {
		this.fans = userByFansId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User userByUserId) {
		this.user = userByUserId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}