package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import database.entities.OrdersProductsEntity;
import database.entities.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;

public class OrderDetailsViewController {
  private SessionManager sessionManager;
  private final ObservableList<ProductsEntity> productsEntities = FXCollections.observableArrayList();

  @FXML
  public BorderPane detailsBorderPane;

  @FXML
  public TableView<ProductsEntity> tableView;

  @FXML
  public TableColumn<ProductsEntity, Integer> productId;

  @FXML
  public TableColumn<ProductsEntity, String> type;

  @FXML
  public TableColumn<ProductsEntity, BigDecimal> price;

  @FXML
  public void initialize() {
    sessionManager = SessionManager.getInstance();

    tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if (newValue != null) {
        try {
          String msg = newValue.getProductId() + " " + newValue.getType();
          ControllerCommunicator.getInstance().setMsg(msg);
          App.setRoot("productView");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });

    String msg = ControllerCommunicator.getInstance().getMsg();

    int orderId = Integer.parseInt(msg);

    Session session = sessionManager.openSession();
    Query<ProductsEntity> query = session.createQuery("SELECT productsEntity" +
        " FROM OrdersProductsEntity en WHERE en.orderId = (:orderId)", ProductsEntity.class);
    query.setParameter("orderId", orderId);

    productsEntities.addAll(query.getResultList());
    sessionManager.closeSession();

    tableView.setItems(productsEntities);

    productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    type.setCellValueFactory(new PropertyValueFactory<>("type"));
    price.setCellValueFactory(new PropertyValueFactory<>("price"));
  }
}
