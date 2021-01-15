package database.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SuppliesProductsEntityPK implements Serializable {
  private static final long serialVersionUID = 2L;
  private int supplyId;
  private int productId;

  public SuppliesProductsEntityPK() {
  }

  public SuppliesProductsEntityPK(int supplyId, int productId) {
    super();
    this.supplyId = supplyId;
    this.productId = productId;
  }

  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

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
    return supplyId == that.supplyId &&
        productId == that.productId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(supplyId, productId);
  }
}
