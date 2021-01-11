package entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders", schema = "bezglutex")
public class OrdersEntity {
  private int orderId;
  private Date date;
  private Object payment;
  private byte realized;

  @Id
  @Column(name = "order_id", nullable = false)
  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId) {
    this.orderId = orderId;
  }

  @Basic
  @Column(name = "date", nullable = true)
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

  @Basic
  @Column(name = "realized", nullable = false)
  public byte getRealized() {
    return realized;
  }

  public void setRealized(byte realized) {
    this.realized = realized;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrdersEntity that = (OrdersEntity) o;

    if (orderId != that.orderId) return false;
    if (realized != that.realized) return false;
    if (date != null ? !date.equals(that.date) : that.date != null) return false;
    if (payment != null ? !payment.equals(that.payment) : that.payment != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = orderId;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (payment != null ? payment.hashCode() : 0);
    result = 31 * result + (int) realized;
    return result;
  }
}
