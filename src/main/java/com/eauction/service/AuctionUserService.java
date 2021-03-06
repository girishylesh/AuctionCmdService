package com.eauction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.entity.AuctionUser;
import com.eauction.repository.AuctionUserRepository;

@Service
public class AuctionUserService {
	
	@Autowired
	private AuctionUserRepository auctionUserRepository;
	
	public Optional<AuctionUser> getAuctionUserByUid(String userId) {
		return auctionUserRepository.findByUid(userId);
	}
	
	public Optional<AuctionUser> getAuctionUserByEmail(String email) {
		return auctionUserRepository.findByEmail(email);
	}
}
