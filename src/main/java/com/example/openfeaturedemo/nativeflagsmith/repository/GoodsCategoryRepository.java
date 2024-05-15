package com.example.openfeaturedemo.nativeflagsmith.repository;

import com.example.openfeaturedemo.nativeflagsmith.entity.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, String>{
    GoodsCategory findByGoodsCategoryId(String goodsCategoryId);

}
