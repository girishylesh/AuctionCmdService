package com.eauction.eventhandler;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.AuctionUser;
import com.eauction.events.AuctionUserCreatedEvent;
import com.eauction.repository.AuctionUserRepository;

@Component
public class AuctionUserEventHandler {
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;
	
	@EventHandler
	public void auctionUserCreatedEvent(AuctionUserCreatedEvent auctionUserCreatedEvent) {
		AuctionUser auctionUser = new AuctionUser();
		BeanUtils.copyProperties(auctionUserCreatedEvent, auctionUser);
		auctionUserRepository.save(auctionUser);
	}
}
