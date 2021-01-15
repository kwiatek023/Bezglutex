package database.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrdersProductsEntityPK implements Serializable {
  private static final long serialVersionUID = 1L;
  private int orderId;
  private int productId;

  public OrdersProductsEntityPK() {
  }

  public OrdersProductsEntityPK(int orderId, int productId) {
    super();
    this.orderId = orderId;
    this.productId = productId;
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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
    OrdersProductsEntityPK that = (OrdersProductsEntityPK) o;
    return orderId == that.orderId &&
        productId == that.productId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderId, productId);
  }
}
