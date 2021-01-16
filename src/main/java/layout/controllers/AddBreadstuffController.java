package layout.controllers;

import database.BreadstuffType;
import database.connection.SessionManager;
import database.entities.BreadstuffEntity;
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

public class AddBreadstuffController {
    private int supplyId;
    private boolean anythingIsAdded = false;
    private SessionManager sessionManager;
    private final ObservableList<BreadstuffEntity> breadstuffEntities = FXCollections.observableArrayList();

    @FXML
    public TableColumn<BreadstuffEntity, Integer> productId;

    @FXML
    public TableColumn<BreadstuffEntity, String> type;

    @FXML
    public TableColumn<BreadstuffEntity, Integer> nettoWeight;

    @FXML
    public TableColumn<BreadstuffEntity, Integer> piecesPerPackage;

    @FXML
    public TableColumn<BreadstuffEntity, Integer> energyValue;

    @FXML
    public TableColumn<BreadstuffEntity, BigDecimal> price;

    @FXML
    public TableView<BreadstuffEntity> tableView;

    @FXML
    public ComboBox<BreadstuffType> typeBox;

    @FXML
    public TextField nettoWeightTextField;

    @FXML
    public TextField piecesPerPackageTextField;

    @FXML
    public TextField energyValueTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
        typeBox.setItems(FXCollections.observableArrayList(BreadstuffType.values()));
    }


    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Integer quantity = showSelectQuantityDialog();
                if (quantity != null) {
                    addBreadstuff(newValue, quantity);
                    displayInfoAlert("Products successfully added to new supply");
                }
            }
        });

        Session session = sessionManager.getCurrentSession();
        Query<BreadstuffEntity> query = session.createQuery("FROM BreadstuffEntity", BreadstuffEntity.class);
        breadstuffEntities.addAll(query.getResultList());

        tableView.setItems(breadstuffEntities);

        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        nettoWeight.setCellValueFactory(new PropertyValueFactory<>("nettoWeight"));
        piecesPerPackage.setCellValueFactory(new PropertyValueFactory<>("piecesPerPackage"));
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

    private void addBreadstuff(BreadstuffEntity breadstuff, Integer quantity) {
        StoredProcedureQuery procedureQuery = sessionManager.getCurrentSession().createStoredProcedureQuery("add_breadstuff").
                registerStoredProcedureParameter("_type", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_netto_weight", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_pieces_per_package", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_energy_value", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_price", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_supply_id", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_quantity", Integer.class, ParameterMode.IN)
                .setParameter("_type", breadstuff.getType().toString())
                .setParameter("_netto_weight", breadstuff.getNettoWeight())
                .setParameter("_pieces_per_package", breadstuff.getPiecesPerPackage())
                .setParameter("_energy_value", breadstuff.getEnergyValue())
                .setParameter("_price", breadstuff.getProductsEntity().getPrice())
                .setParameter("_supply_id", supplyId)
                .setParameter("_quantity", quantity);
        procedureQuery.execute();
        anythingIsAdded = true;
    }

    public void addNewBreadstuffClicked(ActionEvent actionEvent) {
        boolean allDataFilled = typeBox.getSelectionModel().getSelectedItem() != null &&
                !nettoWeightTextField.getText().isBlank() && !piecesPerPackageTextField.getText().isBlank() &&
                !energyValueTextField.getText().isBlank() && !priceTextField.getText().isBlank();

        if (allDataFilled) {
            try {
                Integer nettoWeight = Integer.parseInt(nettoWeightTextField.getText());
                Integer piecesPerPackage = Integer.parseInt(piecesPerPackageTextField.getText());
                Integer energyValue = Integer.parseInt(energyValueTextField.getText());
                BigDecimal price = new BigDecimal(priceTextField.getText());
                Integer quantity = showSelectQuantityDialog();

                boolean validPrice = (price.compareTo(BigDecimal.ZERO) > -1) && (price.compareTo(BigDecimal.valueOf(1000)) < 1);
                boolean allDataValid = nettoWeight > 0 && piecesPerPackage > 0 && energyValue > 0
                        && validPrice  && quantity != null;

                if (allDataValid) {
                    BreadstuffEntity breadstuff = new BreadstuffEntity();
                    breadstuff.setType(typeBox.getSelectionModel().getSelectedItem());
                    breadstuff.setNettoWeight(nettoWeight);
                    breadstuff.setPiecesPerPackage(piecesPerPackage);
                    breadstuff.setEnergyValue(energyValue);
                    breadstuff.setProductsEntity(new ProductsEntity());
                    breadstuff.getProductsEntity().setPrice(new BigDecimal(priceTextField.getText()));


                    addBreadstuff(breadstuff, quantity);
                    displayInfoAlert("Products successfully added to new supply");
                } else {
                    displayErrorAlert("Incorrect data. Try again.");
                }
            } catch (NumberFormatException numberFormatException) {
                displayErrorAlert("Incorrect data. Try again.");
            } catch (Exception ex) {
                displayErrorAlert("Operation failed");
            }
        } else {
            displayErrorAlert("Fill all data to add new product.");
        }

        typeBox.setValue(null);
        nettoWeightTextField.setText("");
        piecesPerPackageTextField.setText("");
        energyValueTextField.setText("");
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
