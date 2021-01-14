package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "supplies_products", schema = "bezglutex")
public class SuppliesProductsEntity implements Serializable {
  private int supplyId;
  private int quantity;
  private ProductsEntity productsEntity;

  @Id
  @Column(name = "supply_id", nullable = false)
  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  @Basic
  @Column(name = "quantity", nullable = false)
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @OneToOne
  @JoinColumn(name = "product_id")
  public ProductsEntity getProductsEntity() {
    return productsEntity;
  }

  public void setProductsEntity(ProductsEntity productsEntity) {
    this.productsEntity = productsEntity;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SuppliesProductsEntity that = (SuppliesProductsEntity) o;

    if (supplyId != that.supplyId) return false;
    if (productsEntity != that.productsEntity) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = supplyId;
    result = 31 * result + productsEntity.getProductId();
    result = 31 * result + quantity;
    return result;
  }
}
