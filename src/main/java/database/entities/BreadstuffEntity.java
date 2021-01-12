package database.entities;

import database.BreadstuffType;
import javax.persistence.*;

@Entity
@Table(name = "breadstuff", schema = "bezglutex")
public class BreadstuffEntity extends ProductsEntity {
  private int productId;
  private BreadstuffType type;
  private int nettoWeight;
  private int piecesPerPackage;
  private int energyValue;

  @Id
  @Column(name = "product_id", nullable = false)
  public int getProductId() {
    return productId;
  }

  public void setProductId(int productId) {
    this.productId = productId;
  }

  @Basic
  @Column(name = "type", insertable = false, updatable = false, nullable = false)
  @Enumerated(EnumType.STRING)
  public BreadstuffType getType() {
    return type;
  }

  public void setType(BreadstuffType type) {
    this.type = type;
  }

  @Basic
  @Column(name = "netto_weight", nullable = false)
  public int getNettoWeight() {
    return nettoWeight;
  }

  public void setNettoWeight(int nettoWeight) {
    this.nettoWeight = nettoWeight;
  }

  @Basic
  @Column(name = "pieces_per_package", nullable = false)
  public int getPiecesPerPackage() {
    return piecesPerPackage;
  }

  public void setPiecesPerPackage(int piecesPerPackage) {
    this.piecesPerPackage = piecesPerPackage;
  }

  @Basic
  @Column(name = "energy_value", nullable = false)
  public int getEnergyValue() {
    return energyValue;
  }

  public void setEnergyValue(int energyValue) {
    this.energyValue = energyValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BreadstuffEntity that = (BreadstuffEntity) o;

    if (productId != that.productId) return false;
    if (nettoWeight != that.nettoWeight) return false;
    if (piecesPerPackage != that.piecesPerPackage) return false;
    if (energyValue != that.energyValue) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + nettoWeight;
    result = 31 * result + piecesPerPackage;
    result = 31 * result + energyValue;
    return result;
  }
}
