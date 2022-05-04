package com.eauction.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eauction.entity.Bid;
import com.eauction.repository.BidRepository;

@Service
public class BidService {

	@Autowired
	private BidRepository bidRepository;
	
	public Optional<Bid> getBidByProductAndAuctionUser(Long productId, Long userId) {
		return bidRepository.findByProductProductIdAndAuctionUserUserId(productId, userId);
	}
	public Long getBidCountByProductId(Long productId) {
		return bidRepository.countByProductProductId(productId);
	}
}
