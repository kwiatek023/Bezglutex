package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "supplies", schema = "bezglutex", catalog = "")
public class SuppliesEntity {
  private int supplyId;
  private Date date;
  private Object payment;

  @Id
  @Column(name = "supply_id", nullable = false)
  public int getSupplyId() {
    return supplyId;
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  @Basic
  @Column(name = "date", nullable = false)
  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Basic
  @Column(name = "payment", nullable = false)
  public Object getPayment() {
    return payment;
  }

  public void setPayment(Object payment) {
    this.payment = payment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    SuppliesEntity that = (SuppliesEntity) o;

    if (supplyId != that.supplyId) return false;
    if (date != null ? !date.equals(that.date) : that.date != null) return false;
    if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = supplyId;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (payment != null ? payment.hashCode() : 0);
    return result;
  }
}
