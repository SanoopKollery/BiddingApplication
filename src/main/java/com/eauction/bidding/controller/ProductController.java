package com.eauction.bidding.controller;

import com.eauction.bidding.entity.Product;
import com.eauction.bidding.entity.Response;
import com.eauction.bidding.entity.Transaction;
import com.eauction.bidding.entity.TransactionResponse;
import com.eauction.bidding.exception.*;
import com.eauction.bidding.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/e-auction/api/v1/seller")
@CrossOrigin(origins = "https://fsesqaud.azurewebsites.net/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add-product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product) throws TransactionExistsException, FutureDateException, ProductCategoryException {
        Product newProduct = productService.create(product);
        return new ResponseEntity<>(newProduct,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteProduct(@PathVariable  String productId) throws TransactionExistsException, FutureDateException, ProductCategoryException, ProductNotFound {
        Response response = productService.delete(productId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get-product/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> findProduct(@PathVariable String productID) throws TransactionExistsException {
        Product product = productService.findByProductID(productID);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @GetMapping("/get-product/name/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductByName(@PathVariable String productName) throws TransactionExistsException {
        try{
            return productService.findByProductName(productName);
        }catch (Exception ex) {

            throw new TransactionExistsException("");
        }
    }

    @GetMapping("/get-product")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProduct() throws TransactionExistsException {
        try{
            return productService.getProducts();
        }catch (Exception ex) {

            throw new TransactionExistsException("");
        }
    }

    @GetMapping("show-bids/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public TransactionResponse findTransaction(@PathVariable String productID) throws TransactionNotFoundException {
        return productService.getBidDetails(productID);
    }



}
