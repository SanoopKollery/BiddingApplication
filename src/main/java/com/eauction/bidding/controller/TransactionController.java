package com.eauction.bidding.controller;

import com.eauction.bidding.entity.Response;
import com.eauction.bidding.entity.Transaction;
import com.eauction.bidding.exception.ProductNotFound;
import com.eauction.bidding.exception.TransactionExistsException;
import com.eauction.bidding.exception.TransactionNotFoundException;
import com.eauction.bidding.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(path = "/e-auction/api/v1/buyer")
//@CrossOrigin(origins = "https://fsesqaud.azurewebsites.net")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/place-bid")
    @ResponseStatus(HttpStatus.ACCEPTED)

    public ResponseEntity<?> create(@RequestBody @Valid Transaction req) throws TransactionExistsException, ProductNotFound, URISyntaxException {
        if (req.getFirstName().length()<5 ||
            req.getFirstName().length()>30)
            return new ResponseEntity<>(new Response("First name length should be between 5 and 30 !"), HttpStatus.BAD_REQUEST);
        if (req.getLastName().length()<3 ||
                req.getLastName().length()>25)
            return new ResponseEntity<>(new Response("Last name length should be between 3 and 25 !"), HttpStatus.BAD_REQUEST);
        if (req.getPhone().length() != 10 )
            return new ResponseEntity<>(new Response("Phone length should be 10 !"), HttpStatus.BAD_REQUEST);

        Transaction transaction = transactionService.create(req);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @PutMapping("/update-bid/{productId}/{buyerEmailld}/{newBidAmount}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> newPhone(@PathVariable String productId,
                                   @PathVariable String buyerEmailld,
                                   @PathVariable double newBidAmount) throws TransactionNotFoundException {
        Transaction transaction =  transactionService.update(productId,buyerEmailld,newBidAmount);
        return new ResponseEntity<>(transaction,HttpStatus.OK);
    }

    @GetMapping("show-bids/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> findTransactions(@PathVariable String productID) throws TransactionNotFoundException {
        return transactionService.findByProductID(productID);
    }

    @GetMapping("/tutorials/{transactionId}")
    public Transaction getTransaction(@PathVariable String transactionId)
    {
        return  transactionService.getTransaction(transactionId);
    }

    @GetMapping("/show-bid/{productID}")
    public ResponseEntity<?> getBidDetails(@PathVariable String productID)
    {
        return  transactionService.getBidDetails(productID);
    }


}
