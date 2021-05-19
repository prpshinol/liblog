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
 * Collect entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "collect")
public class Collect implements java.io.Serializable {

	// Fields

	private CollectId id;
	private Book book;
	private User user;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public Collect() {
	}

	/** minimal constructor */
	public Collect(CollectId id, Book book, User user) {
		this.id = id;
		this.book = book;
		this.user = user;
	}

	/** full constructor */
	public Collect(CollectId id, Book book, User user, Timestamp createTime) {
		this.id = id;
		this.book = book;
		this.user = user;
		this.createTime = createTime;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "bookId", column = @Column(name = "book_id", nullable = false)) })
	public CollectId getId() {
		return this.id;
	}

	public void setId(CollectId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false, insertable = false, updatable = false)
	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}