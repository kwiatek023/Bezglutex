package layout.controllers;

import database.connection.SessionManager;
import database.entities.OrdersEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;

public abstract class AbstractBrowserController {
  protected SessionManager sessionManager;

  @FXML
  public BorderPane BrowserBorderPane;

  @FXML
  public TableColumn<OrdersEntity, Integer> id;

  @FXML
  public TableColumn<OrdersEntity, String> date;

  @FXML
  public TableColumn<OrdersEntity, String> payment;

  @FXML
  public abstract void initialize();

  public abstract void createTableView();
}
