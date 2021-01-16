package layout.controllers;

import database.connection.SessionManager;
import database.entities.DessertsEntity;

import database.entities.ProductsEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.Optional;

public class AddDessertController {
  private int supplyId;
  private boolean anythingIsAdded = false;
  private SessionManager sessionManager;
  private final ObservableList<DessertsEntity> dessertsEntities = FXCollections.observableArrayList();

  @FXML
  public TableColumn<DessertsEntity, Integer> productId;

  @FXML
  public TableColumn<DessertsEntity, String> name;

  @FXML
  public TableColumn<DessertsEntity, Boolean> dairyFree;

  @FXML
  public TableColumn<DessertsEntity, Integer> nettoWeight;

  @FXML
  public TableColumn<DessertsEntity, Integer> energyValue;

  @FXML
  public TableColumn<DessertsEntity, BigDecimal> price;

  @FXML
  public TableView<DessertsEntity> tableView;

  @FXML
  public TextField nameTextField;

  @FXML
  public ComboBox<Boolean> dairyFreeBox;

  @FXML
  public TextField nettoWeightTextField;

  @FXML
  public TextField energyValueTextField;

  @FXML
  public TextField priceTextField;

  @FXML
  public void initialize() {
    this.sessionManager = SessionManager.getInstance();
    createTableView();
    dairyFreeBox.setItems(FXCollections.observableArrayList(true, false));
  }


  public void createTableView() {
    tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
      if (newValue != null) {
        Integer quantity = showSelectQuantityDialog();
        if(quantity != null) {
          addDessert(newValue, quantity);
          displayInfoAlert("Products successfully added to new supply");
        }
      }
    });

    Session session = sessionManager.getCurrentSession();
    Query<DessertsEntity> query = session.createQuery("FROM DessertsEntity", DessertsEntity.class);
    dessertsEntities.addAll(query.getResultList());

    tableView.setItems(dessertsEntities);

    productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    name.setCellValueFactory(new PropertyValueFactory<>("name"));
    dairyFree.setCellValueFactory(new PropertyValueFactory<>("dairyFree"));
    nettoWeight.setCellValueFactory(new PropertyValueFactory<>("nettoWeight"));
    energyValue.setCellValueFactory(new PropertyValueFactory<>("energyValue"));
    price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductsEntity().getPrice()));
  }

  private Integer showSelectQuantityDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();

    dialog.setTitle("Quantity");
    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

    Window window = dialog.getDialogPane().getScene().getWindow();
    window.setOnCloseRequest(event -> window.hide());

    Label label = new Label("Select quantity of this product in supply: ");
    TextField textField = new TextField();

    HBox hbox = new HBox(10);
    hbox.setAlignment(Pos.CENTER);
    Insets hBoxInsets = new Insets(10, 10, 10, 10);
    hbox.setPadding(hBoxInsets);

    hbox.getChildren().addAll(label, textField);
    dialog.getDialogPane().setContent(hbox);

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      try {
        int quantity = Integer.parseInt(textField.getText());

        if (quantity >= 0) {
          return quantity;
        }
        displayErrorAlert("Quantity must be positive!");
      } catch (NumberFormatException ex) {
        displayErrorAlert("Incorrect number.");
      }
      return showSelectQuantityDialog();
    }

    return null;
  }

  private void addDessert(DessertsEntity dessert, Integer quantity) {
    StoredProcedureQuery procedureQuery = sessionManager.getCurrentSession().createStoredProcedureQuery("add_desserts").
        registerStoredProcedureParameter("_name", String.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_dairy_free", Boolean.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_netto_weight", Integer.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_energy_value", Integer.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_price", BigDecimal.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_supply_id", Integer.class, ParameterMode.IN)
        .registerStoredProcedureParameter("_quantity", Integer.class, ParameterMode.IN)
        .setParameter("_name", dessert.getName())
        .setParameter("_dairy_free", dessert.getDairyFree())
        .setParameter("_netto_weight", dessert.getNettoWeight())
        .setParameter("_energy_value", dessert.getEnergyValue())
        .setParameter("_price", dessert.getProductsEntity().getPrice())
        .setParameter("_supply_id", supplyId)
        .setParameter("_quantity", quantity);
    procedureQuery.execute();
    anythingIsAdded = true;
  }

  public void addNewDessertClicked(ActionEvent actionEvent) {
    boolean allDataFilled = dairyFreeBox.getSelectionModel().getSelectedItem() != null &&
        !nettoWeightTextField.getText().isBlank() && !energyValueTextField.getText().isBlank() &&
        !nameTextField.getText().isBlank() && !priceTextField.getText().isBlank();

    if (allDataFilled) {
      try {
        DessertsEntity dessert = new DessertsEntity();
        dessert.setName(nameTextField.getText());
        dessert.setDairyFree(dairyFreeBox.getSelectionModel().getSelectedItem());
        dessert.setNettoWeight(Integer.parseInt(nettoWeightTextField.getText()));
        dessert.setEnergyValue(Integer.parseInt(energyValueTextField.getText()));
        dessert.setProductsEntity(new ProductsEntity());
        dessert.getProductsEntity().setPrice(new BigDecimal(priceTextField.getText()));

        Integer quantity = showSelectQuantityDialog();
        if(quantity != null) {
          addDessert(dessert, quantity);
          displayInfoAlert("Products successfully added to new supply");
        }

      } catch(NumberFormatException numberFormatException) {
        displayErrorAlert("Incorrect data. Try again.");
      } catch (Exception ex) {
        displayErrorAlert("Operation failed");
      }
    } else {
      displayErrorAlert("Fill all data to add new product.");
    }

    nameTextField.setText("");
    nettoWeightTextField.setText("");
    energyValueTextField.setText("");
    dairyFreeBox.setValue(null);
    priceTextField.setText("");
  }

  public void setSupplyId(int supplyId) {
    this.supplyId = supplyId;
  }

  public boolean isAnythingIsAdded() {
    return anythingIsAdded;
  }

  private void displayInfoAlert(String contextText) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(null);
    alert.setHeaderText(null);
    alert.setContentText(contextText);
    alert.showAndWait();
  }

  private void displayErrorAlert(String contextText) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(null);
    alert.setHeaderText(null);
    alert.setContentText(contextText);
    alert.showAndWait();
  }
}
