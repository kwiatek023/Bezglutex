package layout.controllers;

import database.PaymentType;
import database.connection.SessionManager;
import database.entities.SuppliesEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.io.IOException;
import java.util.Optional;

public class StoreKeeperViewController {
    private SessionManager sessionManager;
    private final ObservableList<SuppliesEntity> suppliesEntities = FXCollections.observableArrayList();

    @FXML
    public BorderPane borderPane;

    @FXML
    public TableView<SuppliesEntity> tableView;

    @FXML
    public TableColumn<SuppliesEntity, Integer> id;

    @FXML
    public TableColumn<SuppliesEntity, String> date;

    @FXML
    public TableColumn<SuppliesEntity, String> payment;

    @FXML
    public TableColumn<SuppliesEntity, String> supplierName;

    @FXML
    public TextField supplyToDelete;

    @FXML
    public Button deleteSupplyButton;

    @FXML
    public void initialize() {
        this.sessionManager = SessionManager.getInstance();
        createTableView();
    }


    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    String msg = "" + newValue.getSupplyId();
                    ControllerCommunicator.getInstance().setMsg(msg);
                    App.setRoot("supplyDetailsView");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Session session = sessionManager.openSession();
        Query<SuppliesEntity> query = session.createQuery("FROM SuppliesEntity ", SuppliesEntity.class);
        suppliesEntities.addAll(query.getResultList());
        sessionManager.closeSession();

        tableView.setItems(suppliesEntities);

        id.setCellValueFactory(new PropertyValueFactory<>("supplyId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        supplierName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSuppliersEntity().getName()));

        boolean isStoreManager = isStoreManager();
        supplyToDelete.setVisible(isStoreManager);
        deleteSupplyButton.setVisible(isStoreManager);
    }

    private boolean isStoreManager() {
        return (sessionManager.getSessionFactoryOwner().equals("store_manager"));
    }

    public void seeStorageClicked(ActionEvent actionEvent) {
        try {
            App.setRoot("storage");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewSupplyClicked(ActionEvent actionEvent) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionManager.openSession();
            tx = session.beginTransaction();

            int supplierId = Integer.parseInt(showChooseSupplierDialog());
            String paymentType = PaymentType.convertUILabelToDBLabel(showChoosePaymentDialog());
            SuppliesEntity suppliesEntity = addSupply(supplierId, paymentType);
            showAddProductsDialog(suppliesEntity.getSupplyId());
            suppliesEntities.add(suppliesEntity);

            tx.commit();

        } catch (Exception ex) {
//            ex.printStackTrace();
            tx.rollback();
        }
        finally {
            sessionManager.closeSession();
        }
    }

    private String showChooseSupplierDialog() throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding new supply");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("chooseSupplierView.fxml"));

        ChooseSupplierController chooseSupplierController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            chooseSupplierController = fxmlLoader.getController();
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.NEXT) {
            if(chooseSupplierController.supplierIsChosen()) {
                return ControllerCommunicator.getInstance().getMsg();
            } else {
                return showChooseSupplierDialog();
            }
        }

        return null;
    }

    private String showChoosePaymentDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding new supply");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("choosePaymentView.fxml"));

        ChoosePaymentController choosePaymentController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            choosePaymentController = fxmlLoader.getController();
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.NEXT) {
            if(choosePaymentController.isPaymentChosen()) {
                return ControllerCommunicator.getInstance().getMsg();
            }

            return showChoosePaymentDialog();
        }

        return null;
    }

    private SuppliesEntity addSupply(int supplierId, String paymentType) {
        StoredProcedureQuery procedureQuery = sessionManager.getCurrentSession().createStoredProcedureQuery("add_supply").
                registerStoredProcedureParameter("_supplier_id", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("_payment", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.OUT)
                .setParameter("_supplier_id", supplierId).setParameter("_payment", paymentType);
        procedureQuery.execute();
        Integer addedSupplyId = (Integer) procedureQuery.getOutputParameterValue("id");

        Query<SuppliesEntity> supplyQuery  = sessionManager.getCurrentSession().
                createQuery("FROM SuppliesEntity WHERE supplyId = (:supplyId)", SuppliesEntity.class)
                .setParameter("supplyId", addedSupplyId);
       return supplyQuery.getSingleResult();
    }

    private void showAddProductsDialog(int supplyId) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding new supply");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("addProductsView.fxml"));

        AddProductsController addProductsController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            addProductsController = fxmlLoader.getController();
            addProductsController.setSupplyId(supplyId);
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.FINISH) {
            if(addProductsController.supplyIsEmpty()) {
                displayErrorAlert("Supply is empty now. Add products (breadstuff, pasta or desserts).");
                showAddProductsDialog(supplyId);
            }

            return;
        }

        throw new Exception();
    }

    public void deleteSupplyClicked(ActionEvent actionEvent) {
        if (!supplyToDelete.getText().isBlank()) {
            String supplyId = supplyToDelete.getText();
            String message = "Do you confirm you want to remove supply with id \"" + supplyId + "\"?";

            if (confirm(message)) {
                try {
                    deleteSupply(supplyId);
                    displayInfoAlert("Supply with id \"" + supplyId + "\" successfully removed.");
                } catch (Exception ex) {
                    displayErrorAlert("Supply with id \"" + supplyId + "\" doesn't exists.");
                }
            }
        } else {
            displayErrorAlert("You should enter supply id in order to remove supply.");
        }

        supplyToDelete.setText("");
    }

    private void deleteSupply(String supplyId) throws Exception {
        SuppliesEntity supplyToRemove = null;
        int id = Integer.parseInt(supplyId);

        for (SuppliesEntity supply: suppliesEntities) {
            if (supply.getSupplyId() == id) {
                supplyToRemove = supply;
                break;
            }
        }

        if(supplyToRemove == null) {
            throw new Exception();
        }

        Session session = sessionManager.openSession();
        session.beginTransaction();
        session.remove(supplyToRemove);
        session.getTransaction().commit();
        sessionManager.closeSession();

        suppliesEntities.remove(supplyToRemove);
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

    private boolean confirm(String contextText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(contextText);

        alert.showAndWait();

        return alert.getResult() == ButtonType.OK;
    }
}
