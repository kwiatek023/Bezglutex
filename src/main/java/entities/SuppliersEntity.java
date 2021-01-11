package entities;

import javax.persistence.*;

@Entity
@Table(name = "suppliers", schema = "bezglutex")
public class SuppliersEntity {
  private int supplierId;
  private String name;
  private String nip;
  private String country;
  private String city;
  private String street;
  private String postalCode;
  private String email;

  @Id
  @Column(name = "supplier_id", nullable = false)
  public int getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    this.supplierId = supplierId;
  }

  @Basic
  @Column(name = "name", nullable = false, length = 45)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

    SuppliersEntity that = (SuppliersEntity) o;

    if (supplierId != that.supplierId) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
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
    int result = supplierId;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (nip != null ? nip.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }
}
