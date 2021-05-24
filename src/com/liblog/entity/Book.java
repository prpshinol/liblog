package com.liblog.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Book entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "book", catalog = "liblog")
public class Book implements java.io.Serializable {

	// Fields

	private Integer bookId;
	private User user;
	private String title;
	private String author;
	private String publisher;
	private String pubdate;
	private Integer language;
	private String isbn;
	private String image;
	private String url;
	private String reason;
	private Integer status;
	private Timestamp createTime;
	private Set<Comment> comments = new HashSet<Comment>(0);
	private Set<Collect> collects = new HashSet<Collect>(0);
	private Set<Vote> votes = new HashSet<Vote>(0);

	//常量
	public static final Integer BOOK_LANGUAGE_CHINESE = 0;//中文
	public static final Integer BOOK_LANGUAGE_FOREIGN = 1;//外文
	public static final Integer BOOK_STATUS_RECOMMENDED = 1;//已被荐购，未同意。
	public static final Integer BOOK_STATUS_AGREED = 2;//同意荐购。
	public static final Integer BOOK_STATUS_FINISHED = 3;//书已入馆。

	// Constructors

	/** default constructor */
	public Book() {
	}

	/** minimal constructor */
	public Book(User user, String title, String author) {
		this.user = user;
		this.title = title;
		this.author = author;
	}

	/** full constructor */
	public Book(User user, String title, String author, String publisher,
			String pubdate, Integer language, String isbn, String image,
			String url, String reason, Integer status, Timestamp createTime,
			Set<Comment> comments, Set<Collect> collects, Set<Vote> votes) {
		this.user = user;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.pubdate = pubdate;
		this.language = language;
		this.isbn = isbn;
		this.image = image;
		this.url = url;
		this.reason = reason;
		this.status = status;
		this.createTime = createTime;
		this.comments = comments;
		this.collects = collects;
		this.votes = votes;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "book_id", unique = true, nullable = false)
	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "author", nullable = false, length = 100)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "publisher", length = 100)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "pubdate", length = 10)
	public String getPubdate() {
		return this.pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	@Column(name = "language")
	public Integer getLanguage() {
		return this.language;
	}

	public void setLanguage(Integer language) {
		this.language = language;
	}

	@Column(name = "isbn", length = 20)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "image", length = 100)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "reason", length = 65535)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
	public Set<Collect> getCollects() {
		return this.collects;
	}

	public void setCollects(Set<Collect> collects) {
		this.collects = collects;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "book")
	public Set<Vote> getVotes() {
		return this.votes;
	}

	public void setVotes(Set<Vote> votes) {
		this.votes = votes;
	}

}