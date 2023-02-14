package com.example.springboot.repository;

import com.example.springboot.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offers , Long> {

}
