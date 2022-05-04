package com.eauction.command.aggregator;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.eauction.command.dto.CreateBidCommand;
import com.eauction.command.dto.UpdateBidCommand;
import com.eauction.entity.AuctionUser;
import com.eauction.entity.Product;
import com.eauction.events.BidCreatedEvent;
import com.eauction.events.BidUpdatedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class BidAggregate {
	@AggregateIdentifier
	String aggId;
	String uid;
	Double bidAmount;
	Product product;
    AuctionUser auctionUser;
	
	@CommandHandler
	public BidAggregate(CreateBidCommand createBidCommand) {
		BidCreatedEvent bidCreatedEvent = new BidCreatedEvent();
		BeanUtils.copyProperties(createBidCommand, bidCreatedEvent);
		AggregateLifecycle.apply(bidCreatedEvent);
	}
	
	@EventSourcingHandler
	public void on(BidCreatedEvent bidCreatedEvent) {
		this.aggId = bidCreatedEvent.getUid();
		this.uid = bidCreatedEvent.getUid();
		this.bidAmount = bidCreatedEvent.getBidAmount();
		this.product = bidCreatedEvent.getProduct();
		this.auctionUser = bidCreatedEvent.getAuctionUser();
		log.info("{} : {}", BidCreatedEvent.class.getSimpleName(), bidCreatedEvent.getProduct().getName());
	}
	
	@CommandHandler
	public BidAggregate(UpdateBidCommand updateBidCommand) {
		BidUpdatedEvent bidUpdatedEvent = new BidUpdatedEvent();
		BeanUtils.copyProperties(updateBidCommand, bidUpdatedEvent);
		AggregateLifecycle.apply(bidUpdatedEvent);
	}
	
	@EventSourcingHandler
	public void on(BidUpdatedEvent bidUpdatedEvent) {
		this.aggId = bidUpdatedEvent.getAggId();
		this.uid = bidUpdatedEvent.getUid();
		this.bidAmount = bidUpdatedEvent.getBidAmount();
		this.product = bidUpdatedEvent.getProduct();
		this.auctionUser = bidUpdatedEvent.getAuctionUser();
		log.info("{} : {}", BidUpdatedEvent.class.getSimpleName(), bidUpdatedEvent.getProduct().getName());
	}
}
