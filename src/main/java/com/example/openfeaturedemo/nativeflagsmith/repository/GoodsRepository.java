package com.example.openfeaturedemo.nativeflagsmith.repository;

import com.example.openfeaturedemo.nativeflagsmith.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {
    Optional<Goods> findByProductCode(String productCode);

}
