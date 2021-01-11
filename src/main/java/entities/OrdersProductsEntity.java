package entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "bezglutex")
@IdClass(OrdersProductsEntityPK.class)
public class OrdersProductsEntity {
  private int orderId;
  private int productId;
  private int quantity;

  @Id
  @Column(name = "order_id", nullable = false)
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
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

    OrdersProductsEntity that = (OrdersProductsEntity) o;

    if (orderId != that.orderId) return false;
    if (productId != that.productId) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + productId;
    result = 31 * result + quantity;
    return result;
  }
}
