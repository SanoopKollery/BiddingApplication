package com.eauction.bidding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("eAuction")
public class Transaction {

    @Id
    @GeneratedValue
    private String transactionId;
    @NotNull(message="First name should be mandatory!")
    @Size(min=5,max=30,message=" First name length should be between 5 and 30 !")
    private String firstName;
    @NotNull(message="Last name should be mandatory!")
    @Size(min=3,max=25,message="Last name length should be between 5 and 30 !")
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String pin;
    @NotNull(message="Phone number should be mandatory !")
    @Size(min=10,max=10,message="Phone length should be 10 !")
    private String phone;
    @NotNull (message="Email Id mandatory!")
    @Email(message="Email not valid !")
    private String email;
    @NotNull(message="Product ID mandatory !")
    private String productId;
    private double bidAmount;
}
