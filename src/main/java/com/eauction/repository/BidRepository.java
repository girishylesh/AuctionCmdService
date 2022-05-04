package com.eauction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eauction.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
	public Optional<Bid> findByProductProductIdAndAuctionUserUserId(Long productId, Long userId);
	public Long countByProductProductId(Long productId);
}
