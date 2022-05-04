package com.eauction.command.dto;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.eauction.enums.Event;
import com.eauction.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateAuctionUserCommand {
	
	@TargetAggregateIdentifier
    private String uid;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private Long pin;
	private Long phone;
	private String email;
	private Event status;
	private UserType userType;
}
