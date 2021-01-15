package database.entities;

import database.PaymentType;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "supplies", schema = "bezglutex")
public class SuppliesEntity {
  private int supplyId;
  private Date date;
  private PaymentType payment;
  private SuppliersEntity suppliersEntity;
  private List<SuppliesProductsEntity> suppliesProductsEntities = new ArrayList<>();

  @Id
  @GeneratedValue
  @Column(name = "supply_id", nullable = false)
  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  @Basic
  @Column(name = "date", nullable = false)
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Basic
  @Column(name = "payment", nullable = false)
  @Enumerated(EnumType.STRING)
  public PaymentType getPayment() {
    return payment;
  }

  public void setPayment(PaymentType payment) {
    this.payment = payment;
  }

  @OneToOne
  @JoinColumn(name = "supplier_id")
  public SuppliersEntity getSuppliersEntity() {
    return suppliersEntity;
  }

  public void setSuppliersEntity(SuppliersEntity suppliersEntity) {
    this.suppliersEntity = suppliersEntity;
  }

  @OneToMany(mappedBy = "suppliesEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public List<SuppliesProductsEntity> getSuppliesProductsEntities() {
    return suppliesProductsEntities;
  }

  public void setSuppliesProductsEntities(List<SuppliesProductsEntity> suppliesProductsEntities) {
    this.suppliesProductsEntities = suppliesProductsEntities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SuppliesEntity that = (SuppliesEntity) o;

    if (supplyId != that.supplyId) return false;
    if (date != null ? !date.equals(that.date) : that.date != null) return false;
    if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = supplyId;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (payment != null ? payment.hashCode() : 0);
    return result;
  }
}
