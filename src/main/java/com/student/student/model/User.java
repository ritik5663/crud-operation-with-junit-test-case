package com.student.student.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name", nullable = false) 
  private String name;

  @Column(name = "mobile_number", nullable = false, unique = true)
  private String mobileNumber;

  @Column(name = "address") 
  private String address;

  @Column(name = "email", nullable = false, unique = true) 
  private String email;

  public User() {
  }

  public User(String name, String mobileNumber, String address, String email) {
    this.name = name;
    this.mobileNumber = mobileNumber;
    this.address = address;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + ", mobileNumber=" + mobileNumber + 
           ", address=" + address + ", email=" + email + "]";
  }
}
