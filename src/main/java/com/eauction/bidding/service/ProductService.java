package com.eauction.bidding.service;

import com.eauction.bidding.entity.Product;
import com.eauction.bidding.entity.Response;
import com.eauction.bidding.entity.Transaction;
import com.eauction.bidding.entity.TransactionResponse;
import com.eauction.bidding.repository.ProductRepository;
import com.eauction.bidding.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Product create(Product product)
    {
        UUID productID = UUID.randomUUID();
        product.setProductId(String.valueOf(productID));
        return productRepository.save(product);
    }

    public Response delete(String productId) {

        Optional<Product> product = productRepository.findByProductIdAndCategoryIsNotNull(productId);
        if (product.isPresent()) {
            productRepository.deleteByProductId(productId);
            return Response.builder().message("Successfully Deleted !!").build();
        }
           return  Response.builder().message("Product not found !!").build();


    }

    public Product findByProductID(String productID) {
        Optional<Product> product =  productRepository.findById(productID);
        if (product.isPresent())
            return product.get();
        return new Product();
    }

    public List<Product> findByProductName(String productName) {
        List<Product> products =  productRepository.findByProductName(productName);
        return products;
    }

    public List<Product> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().filter(p->p.getProductName() != null).collect(Collectors.toList());
    }

    public List<TransactionResponse> getBidDetails(String productID) {
        TransactionResponse transactionResponse = new TransactionResponse();
        Optional<Product> product = productRepository.findByProductIdAndCategoryIsNotNull(productID);
        if (product.isPresent()) {
            transactionResponse.setProductId(product.get().getProductId());
            transactionResponse.setProductName(product.get().getProductName());
            transactionResponse.setDescription(product.get().getDescription());
            transactionResponse.setCategory(product.get().getCategory());
            transactionResponse.setStartingPrice(product.get().getStartingPrice());
            transactionResponse.setBidEndDate(product.get().getBidEndDate());
            transactionResponse.setShortDescription(product.get().getShortDescription());
            List<Transaction> transactions = transactionRepository.findByProductIdAndFirstNameIsNotNull(product.get().getProductId());
            if (transactions != null && !transactions.isEmpty()) {
                transactions = transactions.stream().sorted(Comparator.comparingDouble(Transaction::getBidAmount).reversed())
                        .collect(Collectors.toList());
                transactionResponse.setTransactions(transactions);
            }

        }
        return Arrays.asList(transactionResponse);
    }
}
