package entities;

import javax.persistence.*;

@Entity
@Table(name = "customers")
public class CustomerEntity {
  private int customer_id;
  private String firstname;
  private String lastname;
  private String NIP;
  private String country;
  private String city;
  private String postalCode;
  private String email;

  @Id
  @Column(name = "customer_id")
  @GeneratedValue
  public int getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(int customer_id) {
    this.customer_id = customer_id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getNIP() {
    return NIP;
  }

  public void setNIP(String NIP) {
    this.NIP = NIP;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
