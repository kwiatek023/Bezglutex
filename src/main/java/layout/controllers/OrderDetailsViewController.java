package layout.controllers;

import database.connection.SessionManager;
import database.entities.CustomersEntity;
import database.entities.OrdersEntity;
import database.entities.OrdersProductsEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Optional;

public class OrderDetailsViewController {
  private SessionManager sessionManager;
  private int orderId;
  private final ObservableList<OrdersProductsEntity> ordersProductsEntities = FXCollections.observableArrayList();
  private OrdersEntity ordersEntity;

  @FXML
  public Label customerInfo;

  @FXML
  public Label total;

  @FXML
  public Button realizeButton;

  @FXML
  public TableView<OrdersProductsEntity> tableView;

  @FXML
  public TableColumn<OrdersProductsEntity, String> productId;

  @FXML
  public TableColumn<OrdersProductsEntity, String> type;

  @FXML
  public TableColumn<OrdersProductsEntity, String> price;

  @FXML
  public TableColumn<OrdersProductsEntity, String> quantity;

  @FXML
  public TableColumn<OrdersProductsEntity, String> totalPrice;

  @FXML
  public void initialize() {
    sessionManager = SessionManager.getInstance();

    orderId = Integer.parseInt(ControllerCommunicator.getInstance().getMsg());

    tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if(newValue != null) {
        showDetails(newValue);
      }
    });


    findOrdersEntity();
    ordersProductsEntities.addAll(ordersEntity.getOrdersProductsEntities());

    productId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProductsEntity().getProductId())));
    type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductsEntity().getType().toString()));
    price.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProductsEntity().getPrice())));
    quantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
    totalPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
        cellData.getValue().getProductsEntity().getPrice().multiply(new BigDecimal(cellData.getValue().getQuantity())))));

    tableView.setItems(ordersProductsEntities);

    calculateTotal();
    setCustomerData();

    realizeButton.setVisible(!ordersEntity.getRealized());
  }

  private void showDetails(OrdersProductsEntity ordersProducts) {
    Dialog<ButtonType> dialog = new Dialog<>();

    dialog.setWidth(300);
    dialog.setHeight(300);
    dialog.setTitle("Product details");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> window.hide());

    String msg = ordersProducts.getProductsEntity().getProductId() + " " + ordersProducts.getProductsEntity().getType();
    ControllerCommunicator.getInstance().setMsg(msg);

    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(App.class.getResource("productView.fxml"));

    try {
      Parent dialogContent = fxmlLoader.load();
      dialog.getDialogPane().setContent(dialogContent);
    } catch (IOException e) {
      System.out.println("Couldn't load the dialog");
      e.printStackTrace();
    }

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.CLOSE) {
      window.hide();
    }
  }

  private void findOrdersEntity() {
    Session session = sessionManager.openSession();
    Query<OrdersEntity> query = session.createQuery("FROM OrdersEntity WHERE orderId = (:orderId)", OrdersEntity.class);
    query.setParameter("orderId", orderId);
    ordersEntity = query.getSingleResult();
////    test
//    System.out.println("findOrdersEntity: " + ordersEntity.getOrdersProductsEntities().size());
//    System.out.println(ordersEntity.getOrderId());
//    System.out.println(ordersEntity.getDate());
//    System.out.println(ordersEntity.getPayment());
//    System.out.println(ordersEntity.getRealized());
//    for (OrdersProductsEntity ordersProductsEntity : ordersEntity.getOrdersProductsEntities()) {
//      System.out.println(ordersProductsEntity.getProductsEntity().getProductId());
//    }
    sessionManager.closeSession();
  }

  private void calculateTotal() {
    BigDecimal totalCost = BigDecimal.ZERO;
    for (OrdersProductsEntity ordersProductsEntity : ordersProductsEntities) {
      totalCost = totalCost.add(ordersProductsEntity.getProductsEntity().getPrice().multiply(new BigDecimal(ordersProductsEntity.getQuantity())));
    }

    total.setText("Total: " + totalCost + "           ");
  }
  
  private void setCustomerData() {
    CustomersEntity customer = ordersEntity.getCustomersEntity();

    customerInfo.setText("Customer data:\n" +
                          "Firstname: " + customer.getFirstname() + "\n" +
                          "Lastname: " + customer.getLastname() + "\n" +
                          "Email: " + customer.getEmail() + "\n" +
                          "NIP: " + customer.getNip() + "\n" +
                          "Address: \n" +
                          "Street: " + customer.getStreet() + "\n" +
                          "City: " + customer.getCity() + "\n" +
                          "Postal code: " + customer.getPostalCode() + "\n" +
                          "Country: " + customer.getCountry() + "\n"
    );
  }

  public void returnClicked(ActionEvent actionEvent) {
    try {
      App.setRoot("salesmanView");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void realizeClicked(ActionEvent actionEvent) {
    Session session = sessionManager.openSession();
    session.beginTransaction();

    boolean success = session.doReturningWork(connection -> {
      try (CallableStatement function = connection.prepareCall(
            "{ ? = call realize_order(?) }")) {
        function.registerOutParameter(1, Types.BOOLEAN);
        function.setInt(2, orderId);
        function.execute();
        return function.getBoolean(1);
      }
    });

    session.getTransaction().commit();

    sessionManager.closeSession();

    realizeButton.setVisible(!success);
  }
}
