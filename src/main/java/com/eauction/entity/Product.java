package com.eauction.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eauction.enums.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Product implements Serializable {
	private static final long serialVersionUID = 1881534830321772103L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private long productId;

	@Column(unique = true)
	private String uid;
	
	private String name;
	private String shortDesc;
	private String detailedDesc;
	private Double startingPrice;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	private LocalDate bidEndDate;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private AuctionUser auctionUser;
}
