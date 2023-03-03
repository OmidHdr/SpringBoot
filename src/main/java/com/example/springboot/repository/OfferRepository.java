package com.example.springboot.repository;

import com.example.springboot.entity.Expert;
import com.example.springboot.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offers , Long> {

    @Query("select o from Offers o where o.orders.id=?1")
    List<Offers> findByOrderId(Long id);

    @Query("select o from Offers o where o.orders.id=?1 and o.status = true ")
    Offers findByOrderAndStatus(Long id);

}
