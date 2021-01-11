package database.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SuppliesProductsEntityPK implements Serializable {
  private int supplyId;
  private int productId;

  @Column(name = "supply_id", nullable = false)
  @Id
  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  @Column(name = "product_id", nullable = false)
  @Id
  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SuppliesProductsEntityPK that = (SuppliesProductsEntityPK) o;

    if (supplyId != that.supplyId) return false;
    if (productId != that.productId) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = supplyId;
    result = 31 * result + productId;
    return result;
  }
}
