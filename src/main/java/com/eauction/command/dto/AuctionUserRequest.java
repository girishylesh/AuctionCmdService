package com.eauction.command.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import com.eauction.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionUserRequest {
	@NotEmpty(message = "First name is required.")
	@Size(min = 5, max = 30, message = "The length of first name must be between 2 and 100 characters.")
	private String firstName;
	@NotEmpty(message = "Last name is required.")
	@Size(min = 5, max = 30, message = "The length of last name must be between 2 and 100 characters.")
	private String lastName;
	private String address;
	private String city;
	private String state;
	@Pattern(regexp="[0-9]{6}",message="The phone number must be 6 digits") 
	private String pin;
	@Pattern(regexp="[0-9]{10}",message="The phone number must be 10 digits") 
	private String phone;
	@NotEmpty(message = "Email address is required.")
	@Email(message = "Email address is invalid.", flags = { Flag.CASE_INSENSITIVE })
	private String email;
	private UserType userType;
}
