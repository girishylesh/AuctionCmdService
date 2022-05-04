package com.eauction.command.dto;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeleteProductCommand {
	@TargetAggregateIdentifier
	private final String aggId;
	private final String uid;
    private final Long productId;
}
