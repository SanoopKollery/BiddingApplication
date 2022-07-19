package com.eauction.bidding.repository;

import com.eauction.bidding.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
    Transaction findByProductIdAndEmail(String productID, String emailID);
    Optional<Transaction> findByTransactionId(String transactionID);
    List<Transaction> findByProductIdAndFirstNameIsNotNull(String productID);
    void deleteByProductIdAndEmail(String productID, String emailID);

}
