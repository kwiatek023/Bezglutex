package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SalesmanViewController extends AbstractBrowserController {
  private final ObservableList<OrdersEntity> ordersEntities = FXCollections.observableArrayList();

  @FXML
  public TableColumn realized;

  @FXML
  @Override
  public void initialize() {
    this.sessionManager = SessionManager.getInstance();
  }

  @Override
  public void createTableView() {
    id.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    date.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    payment.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    realized.prefWidthProperty().bind(tableView.widthProperty().divide(4));

    Session session = sessionManager.getCurrentSession();
    Query query = session.createQuery("FROM OrdersEntity ");
    ordersEntities.addAll(query.getResultList());
    tableView.setItems(ordersEntities);

    //tableView.setEditable(true);
    id.setCellValueFactory(new PropertyValueFactory<OrdersEntity, Integer>("orderId"));
    date.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("date"));
    payment.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("payment"));
    realized.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("realized"));
  }
}
