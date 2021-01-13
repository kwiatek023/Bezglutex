package database.entities;

import database.ProductType;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", schema = "bezglutex")
public class ProductsEntity {
  private int productId;
  private ProductType type;
  private BigDecimal price;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false)
  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  @Basic
  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  public ProductType getProductType() {
    return type;
  }

  public void setProductType(ProductType type) {
    this.type = type;
  }

  @Basic
  @Column(name = "price", nullable = false, precision = 2)
  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ProductsEntity that = (ProductsEntity) o;

    if (productId != that.productId) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;
    if (price != null ? !price.equals(that.price) : that.price != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
