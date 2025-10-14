package com.splitwiseClone.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Version;

@Entity
@Table(name = "expenses")
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	private String description;
	private BigDecimal amount;
	
	@CreationTimestamp
	private ZonedDateTime createdAt;
	
	@ManyToOne(fetch = FetchType.EAGER) //user who paid the expense
	@JoinColumn(name = "paid_by_user_id")
	private User paidBy;
	
	@ManyToOne(fetch = FetchType.EAGER) //group this expense belongs to
	@JoinColumn(name = "group_id")
	private Group group;
	
	@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //an expense has many splits
	private List<Split> splits;

	@Enumerated(EnumType.STRING)
	private SplitType splitType; // EQUAL, EXACT, PERCENT

	public Expense() {
	}

	public Expense(String description, BigDecimal amount, User paidBy, Group group) {
		super();
		this.description = description;
		this.amount = amount;
		this.paidBy = paidBy;
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public List<Split> getSplits() {
		return splits;
	}

	public void setSplits(List<Split> splits) {
		this.splits = splits;
	}

	public SplitType getSplitType() {
		return splitType;
	}

	public void setSplitType(SplitType splitType) {
		this.splitType = splitType;
	}
}
