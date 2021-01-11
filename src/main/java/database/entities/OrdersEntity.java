package database.entities;
import database.PaymentType;
import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders", schema = "bezglutex")
public class OrdersEntity {
  private int orderId;
  private Date date;
  private PaymentType payment;
  private boolean realized;
  private CustomersEntity customersEntity;
  private Set<OrdersProductsEntity> ordersProductsEntities = new HashSet<>();

  @Id
  @GeneratedValue
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
  @Enumerated(EnumType.STRING)
  public PaymentType getPayment() {
    return payment;
  }

  public void setPayment(PaymentType payment) {
    this.payment = payment;
  }

  @Basic
  @Column(name = "realized", nullable = false)
  public boolean getRealized() {
    return realized;
  }

  public void setRealized(boolean realized) {
    this.realized = realized;
  }

  @ManyToOne
  @JoinColumn(name = "customer_id")
  public CustomersEntity getCustomersEntity() {
    return customersEntity;
  }

  public void setCustomersEntity(CustomersEntity customersEntity) {
    this.customersEntity = customersEntity;
  }

  @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
  public Set<OrdersProductsEntity> getOrdersProductsEntities() {
    return ordersProductsEntities;
  }

  public void setOrdersProductsEntities(Set<OrdersProductsEntity> ordersProductsEntities) {
    this.ordersProductsEntities = ordersProductsEntities;
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
    if (realized) {
      result = 31 * result + 1;
    } else {
      result = 31 * result;
    }
    return result;
  }
}