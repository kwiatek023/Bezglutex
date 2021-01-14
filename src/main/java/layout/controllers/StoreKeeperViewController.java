package layout.controllers;

import database.connection.SessionManager;
import database.entities.SuppliesEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import layout.App;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

            showChooseSupplierDialog();
            tx.commit();

        }catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        finally {session.close();}
    }

    private String showChooseSupplierDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding new supply");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("chooseSupplierView.fxml"));

        try {
            Parent dialogContent = fxmlLoader.load();
            ChooseSupplierController chooseSupplierController = fxmlLoader.getController();
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.NEXT) {
            return ControllerCommunicator.getInstance().getMsg();
        }

        return null;
    }
}
