package com.eauction.command.aggregator;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.eauction.command.dto.CreateAuctionUserCommand;
import com.eauction.enums.UserType;
import com.eauction.events.AuctionUserCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class AuctionUserAggregate {
	@AggregateIdentifier
	String uid;
	String firstName;
	String lastName;
	String address;
	String city;
	String state;
	Long pin;
	Long phone;
	String email;
	UserType userType;

	@CommandHandler
	public AuctionUserAggregate(CreateAuctionUserCommand createAuctionUserCommand) {
		AuctionUserCreatedEvent auctionUserCreatedEvent = new AuctionUserCreatedEvent();
		BeanUtils.copyProperties(createAuctionUserCommand, auctionUserCreatedEvent);
		AggregateLifecycle.apply(auctionUserCreatedEvent);
	}

	@EventSourcingHandler
	public void on(AuctionUserCreatedEvent auctionUserCreatedEvent) {
		this.uid = auctionUserCreatedEvent.getUid();
		this.firstName = auctionUserCreatedEvent.getFirstName();
		this.lastName = auctionUserCreatedEvent.getLastName();
		this.address = auctionUserCreatedEvent.getAddress();
		this.city = auctionUserCreatedEvent.getCity();
		this.state = auctionUserCreatedEvent.getState();
		this.pin = auctionUserCreatedEvent.getPin();
		this.phone = auctionUserCreatedEvent.getPhone();
		this.email = auctionUserCreatedEvent.getEmail();
		this.userType = auctionUserCreatedEvent.getUserType();
		log.info("{} : {} : {}", AuctionUserCreatedEvent.class.getSimpleName(), 
				auctionUserCreatedEvent.getUserType(), auctionUserCreatedEvent.getLastName());
	}
}
