package layout.controllers;

import database.connection.SessionManager;
import database.entities.BreadstuffEntity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.Optional;

public class AddBreadstuffController {
    private int supplyId;
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
    public TextField typeTextField;

    @FXML
    public TextField nettoWeightTextField;

    @FXML
    public TextField piecesPerPackageField;

    @FXML
    public TextField energyValueTextField;

    @FXML
    public TextField priceTextField;

    @FXML
    public TextField quantityTextField;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }


    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                addBreadstuff(newValue);
            }
        });

        Session session = sessionManager.openSession();
        Query<BreadstuffEntity> query = session.createQuery("FROM BreadstuffEntity", BreadstuffEntity.class);
        breadstuffEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(breadstuffEntities);

        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        nettoWeight.setCellValueFactory(new PropertyValueFactory<>("nettoWeight"));
        piecesPerPackage.setCellValueFactory(new PropertyValueFactory<>("piecesPerPackage"));
        energyValue.setCellValueFactory(new PropertyValueFactory<>("energyValue"));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getProductsEntity().getPrice()));
    }

    private void addBreadstuff(BreadstuffEntity breadstuff) {
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
//            if(addProductsController.supplyIsEmpty()) {
//                return ControllerCommunicator.getInstance().getMsg();
//            }
        }

//        return null;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }
}
