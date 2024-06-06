package com.example.openfeaturedemo.repository;

import com.example.openfeaturedemo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {
    Optional<Goods> findByProductCode(String productCode);
    Goods getGoodsByProductCode(String productCode);
    boolean existsByProductCode(String productCode);
}
