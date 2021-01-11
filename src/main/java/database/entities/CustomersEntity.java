package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "customers", schema = "bezglutex")
public class CustomersEntity {
  private int customerId;
  private String firstname;
  private String lastname;
  private String nip;
  private String country;
  private String city;
  private String street;
  private String postalCode;
  private String email;

  @Id
  @Column(name = "customer_id", nullable = false)
  public int getCustomerId() {
    return customerId;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  @Basic
  @Column(name = "firstname", nullable = false, length = 45)
  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  @Basic
  @Column(name = "lastname", nullable = false, length = 45)
  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  @Basic
  @Column(name = "NIP", nullable = false, length = 16)
  public String getNip() {
    return nip;
  }

  public void setNip(String nip) {
    this.nip = nip;
  }

  @Basic
  @Column(name = "country", nullable = false, length = 45)
  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Basic
  @Column(name = "city", nullable = false, length = 45)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Basic
  @Column(name = "street", nullable = false, length = 45)
  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @Basic
  @Column(name = "postal_code", nullable = false, length = 16)
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @Basic
  @Column(name = "email", nullable = false, length = 120)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CustomersEntity that = (CustomersEntity) o;

    if (customerId != that.customerId) return false;
    if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
    if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
    if (nip != null ? !nip.equals(that.nip) : that.nip != null) return false;
    if (country != null ? !country.equals(that.country) : that.country != null) return false;
    if (city != null ? !city.equals(that.city) : that.city != null) return false;
    if (street != null ? !street.equals(that.street) : that.street != null) return false;
    if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = customerId;
    result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
    result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
    result = 31 * result + (nip != null ? nip.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }
}
