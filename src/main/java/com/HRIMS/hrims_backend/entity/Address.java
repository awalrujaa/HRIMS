package com.HRIMS.hrims_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    public Address(){

    }

    public Address(long id, String street, String city, String state, String zipcode, String country) {
        this.addressId = id;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
    }


    public long getId() {
        return addressId;
    }

    public void setId(long id) {
        this.addressId = id;
    }

//    public Long getEmployee() {
//        return employeeId;
//    }
//
//    public void setEmployee(Long employee) {
//        this.employeeId = employee;
//    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
