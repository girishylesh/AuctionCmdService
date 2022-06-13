package com.eauction.command.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidRequest {
	@Min(message="Must be number greater than or equal to 1", value = 1)
	private Double bidAmount;
	@NotNull(message = "Product id is required")
	private String productUid;
	@NotNull(message = "Product id is required")
	private String userUid;
}
