package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_products", schema = "bezglutex")
public class OrdersProductsEntity {
  private OrdersProductsEntityPK id = new OrdersProductsEntityPK();
  private int quantity;
  private OrdersEntity ordersEntity;
  private ProductsEntity productsEntity;

  @ManyToOne
  @MapsId("orderId")
  @JoinColumn(name = "order_id")
  public OrdersEntity getOrdersEntity() {
    return ordersEntity;
  }

  public void setOrdersEntity(OrdersEntity ordersEntity) {
    this.ordersEntity = ordersEntity;
  }

  @EmbeddedId
  public OrdersProductsEntityPK getId() {
    return id;
  }

  public void setId(OrdersProductsEntityPK id) {
    this.id = id;
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
  @MapsId("productId")
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

    if (id.getProductId() != id.getProductId()) return false;
    if (productsEntity != that.productsEntity) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id.getOrderId();
    result = 31 * result + productsEntity.getProductId();
    result = 31 * result + quantity;
    return result;
  }
}
