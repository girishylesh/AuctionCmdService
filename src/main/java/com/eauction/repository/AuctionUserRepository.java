package com.eauction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eauction.entity.AuctionUser;

@Repository
public interface AuctionUserRepository extends JpaRepository<AuctionUser, Long> {
	public Optional<AuctionUser> findByEmail(String email);
}
