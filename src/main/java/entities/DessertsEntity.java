package entities;

import javax.persistence.*;

@Entity
@Table(name = "desserts", schema = "bezglutex", catalog = "")
public class DessertsEntity {
  private int productId;
  private String name;
  private byte dairyFree;
  private int nettoWeight;
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
  @Column(name = "name", nullable = false, length = 45)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "dairy_free", nullable = false)
  public byte getDairyFree() {
    return dairyFree;
  }

  public void setDairyFree(byte dairyFree) {
    this.dairyFree = dairyFree;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DessertsEntity that = (DessertsEntity) o;

    if (productId != that.productId) return false;
    if (dairyFree != that.dairyFree) return false;
    if (nettoWeight != that.nettoWeight) return false;
    if (energyValue != that.energyValue) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = productId;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (int) dairyFree;
    result = 31 * result + nettoWeight;
    result = 31 * result + energyValue;
    return result;
  }
}
