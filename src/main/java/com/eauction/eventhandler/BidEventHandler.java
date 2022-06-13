package com.eauction.eventhandler;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eauction.entity.Bid;
import com.eauction.events.BidCreatedEvent;
import com.eauction.events.BidUpdatedEvent;
import com.eauction.repository.BidRepository;

@Component
public class BidEventHandler {
	
	@Autowired
	private BidRepository bidRepository;
	
	@EventHandler
	public void bidCreatedEvent(BidCreatedEvent bidCreatedEvent) {
		Bid bid = new Bid();
		BeanUtils.copyProperties(bidCreatedEvent, bid);
		bidRepository.save(bid).getBidId();
	}
	
	@EventHandler
	public void bidUpdatedEvent(BidUpdatedEvent bidUpdatedEvent) {
		Optional<Bid> bid = bidRepository.findByProductUidAndAuctionUserUid(bidUpdatedEvent.getProduct().getUid(),
				bidUpdatedEvent.getAuctionUser().getUid());
		if(bid.isPresent()) {
			Bid updateBid = bid.get();
			updateBid.setBidAmount(bidUpdatedEvent.getBidAmount());
			bidRepository.save(updateBid);
		}	
	}
}
