package com.eauction.command.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.eauction.command.dto.AuctionUserRequest;
import com.eauction.command.dto.BidRequest;
import com.eauction.command.dto.ProductRequest;
import com.eauction.entity.AuctionUser;
import com.eauction.entity.Bid;
import com.eauction.entity.Product;
import com.eauction.enums.Category;
import com.eauction.enums.UserType;
import com.eauction.repository.AuctionUserRepository;
import com.eauction.repository.BidRepository;
import com.eauction.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AuctionCommandControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	private AuctionUserRequest seller;
	private AuctionUserRequest buyer;
	private ProductRequest product;

	
	@BeforeEach
	void init() {
		seller = new AuctionUserRequest();
		seller.setFirstName("sname1");
		seller.setLastName("slname1");
		seller.setAddress("saddress1");
		seller.setCity("scity1");
		seller.setState("sstate1");
		seller.setPin("111111");
		seller.setPhone("1111111111");
		seller.setEmail("seller@email.com");
		seller.setUserType(UserType.SELLER);
		
		buyer = new AuctionUserRequest();
		buyer.setFirstName("bname1");
		buyer.setLastName("blname1");
		buyer.setAddress("baddress1");
		buyer.setCity("bcity1");
		buyer.setState("bstate1");
		buyer.setPin("111111");
		buyer.setPhone("1111111111");
		buyer.setEmail("buyer@email.com");
		buyer.setUserType(UserType.BUYER);
		
		product = new ProductRequest();
		product.setName("Product1");
		product.setShortDesc("shortDesc1");
		product.setDetailedDesc("detailedDesc1");
		product.setCategory(Category.PAINTING);
		product.setStartingPrice("200");
		product.setBidEndDate(LocalDate.now().plusDays(7));
		product.setUid("1");
		
		AuctionUser auctionUserSeller =  new AuctionUser();
		BeanUtils.copyProperties(seller, auctionUserSeller);
		auctionUserRepository.save(auctionUserSeller);
		AuctionUser auctionUserBuyer =  new AuctionUser();
		BeanUtils.copyProperties(buyer, auctionUserBuyer);
		auctionUserRepository.save(auctionUserBuyer);
		Product initProduct = new Product();
		BeanUtils.copyProperties(product, initProduct);
		productRepository.save(initProduct);
		Product initProduct2 = new Product();
		BeanUtils.copyProperties(product, initProduct2);
		initProduct2.setName("product2");
		productRepository.save(initProduct2);
	}
	
	@Test
	void addUserBadRequestTest() throws Exception {
		AuctionUserRequest auctionUserRequest = new AuctionUserRequest();
		mockMvc.perform(post("/e-auction/api/v1/cmd/add-user")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(auctionUserRequest)))
	            .andExpect(status().isBadRequest());
	}
	
	@Test
	void addUserSuccessTest() throws Exception {
		AuctionUserRequest auctionUserSeller = new AuctionUserRequest();
		BeanUtils.copyProperties(seller, auctionUserSeller);
		auctionUserSeller.setEmail(UUID.randomUUID().toString() + "@email.com");
		mockMvc.perform(post("/e-auction/api/v1/cmd/add-user")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(auctionUserSeller)))
	            .andExpect(status().isCreated());
	}
	
	@Test
	void productNotFoundTest() throws Exception {
		ProductRequest newProduct = new ProductRequest();
		BeanUtils.copyProperties(product, newProduct);
		newProduct.setUid("111");
		mockMvc.perform(post("/e-auction/api/v1/cmd/seller/add-product")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(newProduct)))
	            .andExpect(status().isNotFound());
	}
	
	@Test
	void addProductTest() throws Exception {	
		ProductRequest newProduct = new ProductRequest();
		BeanUtils.copyProperties(product, newProduct);
		newProduct.setName("New product 1");
		newProduct.setUid("1");
		mockMvc.perform(post("/e-auction/api/v1/cmd/seller/add-product")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(product)))
	            .andExpect(status().isCreated());
	}
	
	@Test
	void deleteProductTest() throws Exception {		
		mockMvc.perform(delete("/e-auction/api/v1/cmd/seller/delete/2")
	            .contentType("application/json"))
	            .andExpect(status().isOk());
	}
	
	@Test
	void placeBidTest() throws Exception {	
		BidRequest bidRequest = new BidRequest();
		bidRequest.setBidAmount(500.0);
		bidRequest.setProductId(1L);
		bidRequest.setUid("2");
		
		mockMvc.perform(post("/e-auction/api/v1/cmd/buyer/place-bid")
	            .contentType("application/json")
	            .content(objectMapper.writeValueAsString(bidRequest)))
	            .andExpect(status().isCreated());
	}
	
	@Test
	void updateBidTest() throws Exception{
		AuctionUser auctionUserBuyer = new AuctionUser();
		BeanUtils.copyProperties(buyer, auctionUserBuyer);
		auctionUserBuyer.setEmail("buyerupdate@email.com");
		AuctionUser savedBuyer = auctionUserRepository.save(auctionUserBuyer);
		Bid newBid = new Bid();
		newBid.setAuctionUser(savedBuyer);
		Product oldProduct = new Product();
		oldProduct.setProductId(1L);
		newBid.setProduct(oldProduct);
		newBid.setBidAmount(888.0);
		bidRepository.save(newBid);
		mockMvc.perform(put("/e-auction/api/v1/cmd/buyer/update-bid/1/buyerupdate@email.com/234")
	            .contentType("application/json"))
	            .andExpect(status().isOk());
	}
}
