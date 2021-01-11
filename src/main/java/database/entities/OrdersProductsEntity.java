package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "bezglutex")
public class OrdersProductsEntity {
  private int orderId;
  private int quantity;
  private ProductsEntity productsEntity;

  @Id
  @Column(name = "order_id", nullable = false)
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Basic
  @Column(name = "quantity", nullable = false)
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @ManyToOne
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

    OrdersProductsEntity that = (OrdersProductsEntity) o;

    if (orderId != that.orderId) return false;
    if (productsEntity != that.productsEntity) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + productsEntity.getProductId();
    result = 31 * result + quantity;
    return result;
  }
}
