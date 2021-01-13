package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class SalesmanViewController extends AbstractBrowserController {
  private final ObservableList<OrdersEntity> ordersEntities = FXCollections.observableArrayList();

  @FXML
  public TableView<OrdersEntity> tableView;

  @FXML
  public TableColumn realized;

  @FXML
  @Override
  public void initialize() {
    this.sessionManager = SessionManager.getInstance();
    createTableView();
  }

  @Override
  public void createTableView() {
    tableView.prefWidthProperty().bind(BrowserBorderPane.widthProperty().divide(1.5));
    id.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    date.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    payment.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    realized.prefWidthProperty().bind(tableView.widthProperty().divide(4));

    tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if (newValue != null) {
        OrderDetailsViewController.initialize(newValue.getOrderId());
//        showDetails(newValue.getOrderId());
      }
    });

    Session session = sessionManager.openSession();
    Query query = session.createQuery("FROM OrdersEntity ");
    ordersEntities.addAll(query.getResultList());
    tableView.setItems(ordersEntities);

    //tableView.setEditable(true);
    id.setCellValueFactory(new PropertyValueFactory<OrdersEntity, Integer>("orderId"));
    date.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("date"));
    payment.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("payment"));
    realized.setCellValueFactory(new PropertyValueFactory<OrdersEntity, String>("realized"));

  }

//  private void showDetails(int orderId) {
//    try {
//      FXMLLoader loader = new FXMLLoader(App.class.getResource("orderDetailsView.fxml"));
//      final Parent parent = FXMLLoader.load(App.class.getResource("orderDetailsView.fxml"));
//      Stage stage = App.getStage();
//      stage.setScene(new Scene(parent));
//      OrderDetailsViewController controller = loader.getController();
//      controller.setOrderId(orderId);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }


}
