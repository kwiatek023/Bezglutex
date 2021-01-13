package layout.controllers;

import database.connection.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public abstract class AbstractBrowserController {
  protected SessionManager sessionManager;

  @FXML
  public TableView tableView;

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
