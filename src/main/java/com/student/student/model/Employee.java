package com.student.student.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees") 
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name") 
  private String name;

  @Column(name = "job_title") 
  private String jobTitle;

  @Column(name = "salary") 
  private double salary;

  @Column(name = "department") 
  private String department;

  @Column(name = "email") 
  private String email;

  public Employee() {
  }

  public Employee(String name, String jobTitle, double salary, String department, String email) {
    this.name = name;
    this.jobTitle = jobTitle;
    this.salary = salary;
    this.department = department;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Employee [id=" + id + ", name=" + name + ", jobTitle=" + jobTitle + ", salary=" + salary + 
           ", department=" + department + ", email=" + email + "]";
  }
}
