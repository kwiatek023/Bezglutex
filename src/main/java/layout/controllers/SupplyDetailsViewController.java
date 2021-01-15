package layout.controllers;

import database.connection.SessionManager;
import database.entities.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class SupplyDetailsViewController {
    private SessionManager sessionManager;
    private int supplyId;
    private final ObservableList<SuppliesProductsEntity> suppliesProductsEntities = FXCollections.observableArrayList();
    private SuppliesEntity suppliesEntity;

    @FXML
    public Label supplierInfo;

    @FXML
    public Label total;

    @FXML
    public TableColumn<SuppliesProductsEntity, String> productId;

    @FXML
    public TableColumn<SuppliesProductsEntity, String> type;

    @FXML
    public TableColumn<SuppliesProductsEntity, String> price;

    @FXML
    public TableColumn<SuppliesProductsEntity, String> quantity;

    @FXML
    public TableView<SuppliesProductsEntity> tableView;

    @FXML
    public TableColumn<SuppliesProductsEntity, String> totalPrice;

    @FXML
    public void initialize() {
        sessionManager = SessionManager.getInstance();

        supplyId = Integer.parseInt(ControllerCommunicator.getInstance().getMsg());

        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null) {
                showDetails(newValue);
            }
        });

        findSuppliesEntity();
        suppliesProductsEntities.addAll(suppliesEntity.getSuppliesProductsEntities());

        productId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProductsEntity().getProductId())));
        type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductsEntity().getType().toString()));
        price.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getProductsEntity().getPrice())));
        quantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        totalPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(
            cellData.getValue().getProductsEntity().getPrice().multiply(new BigDecimal(cellData.getValue().getQuantity())))));

        tableView.setItems(suppliesProductsEntities);

        calculateTotal();
        setSupplierData();
    }

    private void calculateTotal() {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (SuppliesProductsEntity suppliesProductsEntity : suppliesProductsEntities) {
            totalCost = totalCost.add(suppliesProductsEntity.getProductsEntity().getPrice().multiply(new BigDecimal(suppliesProductsEntity.getQuantity())));
        }

        total.setText("Total: " + totalCost + "           ");
    }

    private void setSupplierData() {
        SuppliersEntity supplier = suppliesEntity.getSuppliersEntity();

        supplierInfo.setText("Supplier data:\n" +
                            "Name: " + supplier.getName() + "\n" +
                            "Email: " + supplier.getEmail() + "\n" +
                            "NIP: " + supplier.getNip() + "\n" +
                            "Address: \n" +
                            "Street: " + supplier.getStreet() + "\n" +
                            "City: " + supplier.getCity() + "\n" +
                            "Postal code: " + supplier.getPostalCode() + "\n" +
                            "Country: " + supplier.getCountry() + "\n"
        );
    }

    private void showDetails(SuppliesProductsEntity newValue) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(300);
        dialog.setHeight(300);
        dialog.setTitle("Product details");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        String msg = newValue.getProductsEntity().getProductId() + " " + newValue.getProductsEntity().getType();
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

    private void findSuppliesEntity() {
        Session session = sessionManager.openSession();
        Query<SuppliesEntity> query = session.createQuery("FROM SuppliesEntity WHERE supplyId = (:supplyId)", SuppliesEntity.class);
        query.setParameter("supplyId", supplyId);
        suppliesEntity = query.getSingleResult();
        sessionManager.closeSession();
    }

    public void returnClicked(ActionEvent actionEvent) {
        try {
            App.setRoot("storeKeeperView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
