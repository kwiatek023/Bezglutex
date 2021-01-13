package layout.controllers;

import database.connection.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;

public abstract class AbstractBrowserController {
  protected SessionManager sessionManager;

  @FXML
  public BorderPane BrowserBorderPane;

  @FXML
  public TableColumn id;

  @FXML
  public TableColumn date;

  @FXML
  public TableColumn payment;

  @FXML
  public abstract void initialize();

  public abstract void createTableView();
}
