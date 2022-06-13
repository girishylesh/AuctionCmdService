package com.eauction.command.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eauction.command.dto.AuctionUserRequest;
import com.eauction.command.dto.BidRequest;
import com.eauction.command.dto.CreateAuctionUserCommand;
import com.eauction.command.dto.CreateBidCommand;
import com.eauction.command.dto.CreateProductCommand;
import com.eauction.command.dto.DeleteProductCommand;
import com.eauction.command.dto.ProductRequest;
import com.eauction.command.dto.UpdateBidCommand;
import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.enums.Event;
import com.eauction.enums.UserType;
import com.eauction.exception.BidException;
import com.eauction.exception.ProductExistsException;
import com.eauction.exception.ProductNotFoundException;
import com.eauction.exception.UserExistsException;
import com.eauction.exception.UserNotFoundException;
import com.eauction.service.AuctionUserService;
import com.eauction.service.BidService;
import com.eauction.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/e-auction/api/v1/cmd")
public class AuctionCommandController {

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private AuctionUserService auctionUserService;
	
	@Autowired
	private BidService bidService;

	@PostMapping("/add-user")
	public ResponseEntity<String> createProduct(@Valid @RequestBody AuctionUserRequest auctionUserRequest) {
		Optional<AuctionUser> auctionUser = auctionUserService.getAuctionUserByEmail(auctionUserRequest.getEmail());
        if(auctionUser.isPresent()) {
        	throw new UserExistsException("User already exists");
        }
        
		try {
			CreateAuctionUserCommand seller = CreateAuctionUserCommand.builder()
					.uid(UUID.randomUUID().toString())
					.firstName(auctionUserRequest.getFirstName())
					.lastName(auctionUserRequest.getLastName())
					.address(auctionUserRequest.getAddress())
					.city(auctionUserRequest.getCity())
					.state(auctionUserRequest.getState())
					.pin(Long.parseLong(auctionUserRequest.getPin()))
					.phone(Long.parseLong(auctionUserRequest.getPhone()))
					.email(auctionUserRequest.getEmail())
					.userType(auctionUserRequest.getUserType())
					.status(Event.CREATE)
					.build();
			return new ResponseEntity<>(commandGateway.sendAndWait(seller).toString(), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error creating user: {}", ex);
			return new ResponseEntity<>("Error creating user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/seller/add-product")
	public ResponseEntity<String> createProduct(@Valid @RequestBody ProductRequest productRequest) {
		
		Optional<AuctionUser> auctionUser = auctionUserService.getAuctionUserByUid(productRequest.getUid());
		if(!auctionUser.isPresent() || !UserType.SELLER.equals(auctionUser.get().getUserType())) {
			throw new UserNotFoundException("Invalid user.");
		} 
		else if(productService.getProductByNameAndUser(productRequest.getName(), productRequest.getUid()).isPresent()) {
			throw new ProductExistsException("Product exists");
		} 
		
		try {
			CreateProductCommand product = CreateProductCommand.builder()
					.uid(UUID.randomUUID().toString())
					.name(productRequest.getName())
					.shortDesc(productRequest.getShortDesc())
					.detailedDesc(productRequest.getDetailedDesc())
					.category(productRequest.getCategory())
					.startingPrice(Double.parseDouble(productRequest.getStartingPrice()))
					.bidEndDate(productRequest.getBidEndDate())
					.auctionUser(auctionUser.get())
					.status(Event.CREATE)
					.build();
			return new ResponseEntity<>(commandGateway.sendAndWait(product).toString(), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error creating product: {}", ex);
			return new ResponseEntity<>("Error creating product", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/seller/delete/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
	    	
		Optional<Product> product = productService.getProductByUid(productId);
		if(!product.isPresent()) {
			throw new ProductNotFoundException("Product not found.");
		} else if(LocalDate.now().isAfter(product.get().getBidEndDate())) {
				throw new BidException("Bid is closed. Cannot perorm this operation.");
		} else if(bidService.getBidCountByProductUid(product.get().getUid()) > 0) {
			throw new BidException("Bid exists for this product. Cannot perorm this operation.");
		}
		
		try {
			DeleteProductCommand deleteProduct = DeleteProductCommand.builder()
					.aggId(UUID.randomUUID().toString())
					.uid(product.get().getUid())
					.build();
			return new ResponseEntity<>(commandGateway.sendAndWait(deleteProduct).toString(), HttpStatus.OK);
			
		} catch (Exception ex) {
			log.error("Error deleting product: {}", ex);
			return new ResponseEntity<>("Error deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/buyer/place-bid")
	public ResponseEntity<String> placeBid(@Valid @RequestBody BidRequest bidRequest) {
		Optional<AuctionUser> auctionUser = auctionUserService.getAuctionUserByUid(bidRequest.getUserUid());
		if(!auctionUser.isPresent() || !UserType.BUYER.equals(auctionUser.get().getUserType())) {
			throw new UserNotFoundException("Invalid user");
		}
			
		Optional<Bid> bid = bidService.getBidByProductAndAuctionUser(bidRequest.getProductUid(), auctionUser.get().getUid());
		if(bid.isPresent()) {
			throw new BidException("Bid already placed. Please update existing bid if required");
		}
			
		Optional<Product> product = productService.getProductByUid(bidRequest.getProductUid());
		if(!product.isPresent()) {
			throw new ProductNotFoundException("Product not found");
		}  
		else if(LocalDate.now().isAfter(product.get().getBidEndDate())) {
			throw new BidException("Bid is closed. Cannot perorm this operation.");
		}
		
		try {	
			CreateBidCommand createBid = CreateBidCommand.builder()
					.uid(UUID.randomUUID().toString())
					.product(product.get())
					.auctionUser(auctionUser.get())
					.bidAmount(bidRequest.getBidAmount())
					.build();
			return new ResponseEntity<>(commandGateway.sendAndWait(createBid).toString(), HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error placing bid: {}", ex);
			return new ResponseEntity<>("Error placing bid", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/buyer/update-bid/{productUid}/{buyerEmailId}/{newBidAmount}")
	public ResponseEntity<String> updateBid(@PathVariable String productUid, @PathVariable String buyerEmailId, 
			@PathVariable Double newBidAmount) {
		
		Optional<AuctionUser> auctionUser = auctionUserService.getAuctionUserByEmail(buyerEmailId);
		if(!auctionUser.isPresent() || !UserType.BUYER.equals(auctionUser.get().getUserType())) {
			throw new UserNotFoundException("Buyer not found");
		}
		Optional<Product> product = productService.getProductByUid(productUid);
		if(!product.isPresent()) {
			throw new ProductNotFoundException("Product not found");
		} 
		else if(LocalDate.now().isAfter(product.get().getBidEndDate())) {
			throw new BidException("Bid closed");
		}
		Optional<Bid> bid = bidService.getBidByProductAndAuctionUser(productUid, auctionUser.get().getUid());
		if(!bid.isPresent()) {
			throw new BidException("Bid does not exist");
		}
			
		try {	
			UpdateBidCommand updateBidCommand = UpdateBidCommand.builder()
					.bidAmount(newBidAmount)
					.product(product.get())
					.auctionUser(auctionUser.get())
					.aggId(UUID.randomUUID().toString())
					.uid(bid.get().getUid())
					.build();
			return new ResponseEntity<>(commandGateway.sendAndWait(updateBidCommand).toString(), HttpStatus.OK);
		} catch (Exception ex) {
				log.error("Error updating bid: {}", ex);
				return new ResponseEntity<>("Error updating bid", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
