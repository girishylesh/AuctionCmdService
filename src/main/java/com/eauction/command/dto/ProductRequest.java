package com.eauction.command.dto;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import com.eauction.enums.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
	@NotBlank(message = "Product name is required")
	private String name;
	private String shortDesc;
	private String detailedDesc;
	@Digits(message="Must be number", fraction = 2, integer = 10)
	private String startingPrice;
	private Category category;
	@Future(message = "Bid end date must be future date")
	private LocalDate bidEndDate;
	private String uid;
}
