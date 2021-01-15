package database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "supplies_products", schema = "bezglutex")
public class SuppliesProductsEntity implements Serializable {
  private SuppliesProductsEntityPK id = new SuppliesProductsEntityPK();
  private int quantity;
  private SuppliesEntity suppliesEntity;
  private ProductsEntity productsEntity;

  @EmbeddedId
  public SuppliesProductsEntityPK getId() {
    return id;
  }

  public void setId(SuppliesProductsEntityPK id) {
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
  @MapsId("supplyId")
  @JoinColumn(name = "supply_id")
  public SuppliesEntity getSuppliesEntity() {
    return suppliesEntity;
  }

  public void setSuppliesEntity(SuppliesEntity suppliesEntity) {
    this.suppliesEntity = suppliesEntity;
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

    SuppliesProductsEntity that = (SuppliesProductsEntity) o;

    if (suppliesEntity.getSupplyId() != that.getSuppliesEntity().getSupplyId()) return false;
    if (productsEntity != that.productsEntity) return false;
    if (quantity != that.quantity) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = suppliesEntity.getSupplyId();
    result = 31 * result + productsEntity.getProductId();
    result = 31 * result + quantity;
    return result;
  }
}
