package entities;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "bezglutex", catalog = "")
public class UsersEntity {
  private int userId;
  private String login;
  private String password;
  private Object type;

  @Id
  @Column(name = "user_id", nullable = false)
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "login", nullable = false, length = 45)
  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  @Basic
  @Column(name = "password", nullable = false, length = 40)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "type", nullable = false)
  public Object getType() {
    return type;
  }

  public void setType(Object type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UsersEntity that = (UsersEntity) o;

    if (userId != that.userId) return false;
    if (login != null ? !login.equals(that.login) : that.login != null) return false;
    if (password != null ? !password.equals(that.password) : that.password != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId;
    result = 31 * result + (login != null ? login.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}
