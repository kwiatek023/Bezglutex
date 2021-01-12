package database.entities;

import database.PastaType;
import javax.persistence.*;

@Entity
@Table(name = "pasta", schema = "bezglutex")
public class PastaEntity extends ProductsEntity {
  private int productId;
  private PastaType type;
  private int nettoWeight;
  private int energyValue;
  private int boilTime;

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
  public PastaType getType() {
    return type;
  }

  public void setType(PastaType type) {
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
  @Column(name = "energy_value", nullable = false)
  public int getEnergyValue() {
    return energyValue;
  }

  public void setEnergyValue(int energyValue) {
    this.energyValue = energyValue;
  }

  @Basic
  @Column(name = "boil_time", nullable = false)
  public int getBoilTime() {
    return boilTime;
  }

  public void setBoilTime(int boilTime) {
    this.boilTime = boilTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PastaEntity that = (PastaEntity) o;

    if (productId != that.productId) return false;
    if (nettoWeight != that.nettoWeight) return false;
    if (energyValue != that.energyValue) return false;
    if (boilTime != that.boilTime) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + nettoWeight;
    result = 31 * result + energyValue;
    result = 31 * result + boilTime;
    return result;
  }
}
