package database.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class OrdersProductsEntityPK implements Serializable {
  private static final long serialVersionUID = 1L;
  private int orderId;
  private int productId;

  public OrdersProductsEntityPK() {
  }

  public OrdersProductsEntityPK(int orderId, int productId) {
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

    if (orderId != that.orderId) return false;
    if (productId != that.productId) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + productId;
    return result;
  }
}
