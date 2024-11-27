package com.student.student.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students") 
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name") 
  private String name;

  @Column(name = "age") 
  private int age;

  @Column(name = "email") 
  private String email;

  public Student() {
  }

  public Student(String name, int age, String email) {
    this.name = name;
    this.age = age;
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Student [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + "]";
  }
}
