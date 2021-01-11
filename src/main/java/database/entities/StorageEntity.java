package database.entities;

import javax.persistence.*;

@Entity
@Table(name = "storage", schema = "bezglutex")
public class StorageEntity {
  private int productId;
  private int quantity;

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

    StorageEntity that = (StorageEntity) o;

    if (productId != that.productId) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + quantity;
    return result;
  }
}
