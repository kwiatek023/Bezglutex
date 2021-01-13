package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "supplies_products", schema = "bezglutex")
public class SuppliesProductsEntity implements Serializable {
  private int supplyId;
  private int productId;
  private int quantity;

  @Id
  @Column(name = "supply_id", nullable = false)
  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  @Id
  @Column(name = "product_id", nullable = false)
  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  @Basic
  @Column(name = "quantity", nullable = false)
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SuppliesProductsEntity that = (SuppliesProductsEntity) o;

    if (supplyId != that.supplyId) return false;
    if (productId != that.productId) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = supplyId;
    result = 31 * result + productId;
    result = 31 * result + quantity;
    return result;
  }
}
