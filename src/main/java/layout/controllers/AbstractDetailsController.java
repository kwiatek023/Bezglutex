package layout.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;

public abstract class AbstractDetailsController {

  @FXML
  public BorderPane DetailsBorderPane;

  @FXML
  public TableColumn productId;

  @FXML
  public TableColumn typeOfProduct;

  @FXML
  public TableColumn price;

  @FXML
  public abstract void initialize();
}
