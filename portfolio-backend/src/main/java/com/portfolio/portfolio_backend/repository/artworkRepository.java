package com.portfolio.portfolio_backend.repository;

import com.portfolio.portfolio_backend.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface artworkRepository extends JpaRepository<Artwork, Long> {
}
