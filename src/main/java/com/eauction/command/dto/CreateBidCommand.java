package com.eauction.command.dto;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.eauction.entity.AuctionUser;
import com.eauction.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateBidCommand {
	@TargetAggregateIdentifier
	private final String uid;
	private final Double bidAmount;
    private final Product product;
    private final AuctionUser auctionUser;
}
