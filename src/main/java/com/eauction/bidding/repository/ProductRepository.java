package com.eauction.bidding.repository;

import com.eauction.bidding.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

    Optional<Product> findByProductIdAndCategoryIsNotNull(String productId);
    List<Product> findByProductName(String productName);
    void deleteByProductId(String productId);
}
