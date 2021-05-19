package com.liblog.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String username;
	private String password;
	private Integer gender;
	private String imgPath;
	private Timestamp createTime;
	private Set<Collect> collects = new HashSet<Collect>(0);
	private Set<Vote> votes = new HashSet<Vote>(0);
	private Set<Comment> comments = new HashSet<Comment>(0);
	private Set<Follow> followsForUserId = new HashSet<Follow>(0);
	private Set<Book> books = new HashSet<Book>(0);
	private Set<Follow> followsForFansId = new HashSet<Follow>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public User(String username, String password, Integer gender,
			String imgPath, Timestamp createTime, Set<Collect> collects,
			Set<Vote> votes, Set<Comment> comments,
			Set<Follow> followsForUserId, Set<Book> books,
			Set<Follow> followsForFansId) {
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.imgPath = imgPath;
		this.createTime = createTime;
		this.collects = collects;
		this.votes = votes;
		this.comments = comments;
		this.followsForUserId = followsForUserId;
		this.books = books;
		this.followsForFansId = followsForFansId;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "username", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "gender")
	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Column(name = "img_path", length = 100)
	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Collect> getCollects() {
		return this.collects;
	}

	public void setCollects(Set<Collect> collects) {
		this.collects = collects;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Vote> getVotes() {
		return this.votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserId")
	public Set<Follow> getFollowsForUserId() {
		return this.followsForUserId;
	}

	public void setFollowsForUserId(Set<Follow> followsForUserId) {
		this.followsForUserId = followsForUserId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFansId")
	public Set<Follow> getFollowsForFansId() {
		return this.followsForFansId;
	}

	public void setFollowsForFansId(Set<Follow> followsForFansId) {
		this.followsForFansId = followsForFansId;
	}

}