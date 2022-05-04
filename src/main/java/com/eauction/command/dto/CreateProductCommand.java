package com.eauction.command.dto;

import java.time.LocalDate;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.eauction.entity.AuctionUser;
import com.eauction.enums.Category;
import com.eauction.enums.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateProductCommand {
	
	@TargetAggregateIdentifier
    private final String uid;
	private final String name;
	private final String shortDesc;
	private final String detailedDesc;
	private final Double startingPrice;
	private final Category category;
	private final LocalDate bidEndDate;
	private final Event status;
	private final AuctionUser auctionUser;
}
