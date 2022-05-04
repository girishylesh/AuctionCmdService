package com.eauction.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eauction.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class AuctionUser implements Serializable {

	private static final long serialVersionUID = -857768027816873795L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(unique = true)
	private String uid;
	
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private Long pin;
	private Long phone;
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
}
