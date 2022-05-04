package com.eauction.command.aggregator;

import java.time.LocalDate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.eauction.command.dto.CreateProductCommand;
import com.eauction.command.dto.DeleteProductCommand;
import com.eauction.entity.AuctionUser;
import com.eauction.enums.Category;
import com.eauction.events.ProductCreatedEvent;
import com.eauction.events.ProductDeletedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aggregate
public class ProductAggregate {

	@AggregateIdentifier
	String uid;
	String name;
	String shortDesc;
	String detailedDesc;
	Double startingPrice;
	Category category;
	LocalDate bidEndDate;
	AuctionUser auctionUser;

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();
		BeanUtils.copyProperties(createProductCommand, productCreatedEvent);
		AggregateLifecycle.apply(productCreatedEvent);
	}

	@EventSourcingHandler
	public void on(ProductCreatedEvent productCreatedEvent) {
		this.uid = productCreatedEvent.getUid();
		this.name = productCreatedEvent.getName();
		this.shortDesc = productCreatedEvent.getShortDesc();
		this.detailedDesc = productCreatedEvent.getDetailedDesc();
		this.startingPrice = productCreatedEvent.getStartingPrice();
		this.category = productCreatedEvent.getCategory();
		this.bidEndDate = productCreatedEvent.getBidEndDate();
		this.auctionUser = productCreatedEvent.getAuctionUser();
		log.info("{} : {}", ProductCreatedEvent.class.getSimpleName(), productCreatedEvent.getName());
	}
	
	@CommandHandler
	public ProductAggregate(DeleteProductCommand deleteProductCommand) {
		ProductDeletedEvent productDeletedEvent = new ProductDeletedEvent();
		BeanUtils.copyProperties(deleteProductCommand, productDeletedEvent);
		AggregateLifecycle.apply(productDeletedEvent);
	}
	
	@EventSourcingHandler
	public void on(ProductDeletedEvent productDeletedEvent) {
		this.uid = productDeletedEvent.getAggId();
		log.info("{} : {}", ProductDeletedEvent.class.getSimpleName(), productDeletedEvent.getProductId());
	}
}
