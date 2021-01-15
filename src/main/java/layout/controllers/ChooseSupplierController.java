package layout.controllers;

import database.connection.SessionManager;
import database.entities.SuppliersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import layout.communication.ControllerCommunicator;
import layout.exceptions.FailedAddingSupplierException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

public class ChooseSupplierController {
    private Integer chosenSupplierId = null;
    private Session session;
    private final ObservableList<SuppliersEntity> suppliersEntities = FXCollections.observableArrayList();

    @FXML
    public TableView<SuppliersEntity> tableView;

    @FXML
    public TableColumn<SuppliersEntity, String> name;

    @FXML
    public TableColumn<SuppliersEntity, String> nip;

    @FXML
    public TableColumn<SuppliersEntity, String> country;

    @FXML
    public TableColumn<SuppliersEntity, String> city;

    @FXML
    public TableColumn<SuppliersEntity, String> street;

    @FXML
    public TableColumn<SuppliersEntity, String> postalCode;

    @FXML
    public TableColumn<SuppliersEntity, String> email;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField nipTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField countryTextField;

    @FXML
    public TextField cityTextField;

    @FXML
    public TextField streetTextField;

    @FXML
    public TextField postalCodeTextField;

    @FXML
    public void initialize() {
        this.session = SessionManager.getInstance().getCurrentSession();
        createTableView();
    }

    public void createTableView() {
        tableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                chosenSupplierId = newValue.getSupplierId();
            }
        });

        Query<SuppliersEntity> query = session.createQuery("FROM SuppliersEntity ", SuppliersEntity.class);
        suppliersEntities.addAll(query.getResultList());

        tableView.setItems(suppliersEntities);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        nip.setCellValueFactory(new PropertyValueFactory<>("nip"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public boolean supplierAddedSuccessfully() {
        boolean allDataFilled = !nameTextField.getText().isBlank() &&
                !nipTextField.getText().isBlank() && !emailTextField.getText().isBlank() &&
                !countryTextField.getText().isBlank() && !cityTextField.getText().isBlank() &&
                !streetTextField.getText().isBlank() && !postalCodeTextField.getText().isBlank();

        if (allDataFilled) {
            try {
                addSupplier(nameTextField.getText(), nipTextField.getText(), emailTextField.getText(),
                        countryTextField.getText(), cityTextField.getText(), streetTextField.getText(), postalCodeTextField.getText());
                return true;
            } catch(FailedAddingSupplierException failedAddingSupplierException) {
                displayErrorAlert("This supplier exists.");
            } catch (Exception ex) {
                displayErrorAlert("Operation failed");
            }
        } else {
            displayErrorAlert("Fill all data to add supplier.");
        }

        nameTextField.setText("");
        nipTextField.setText("");
        emailTextField.setText("");
        countryTextField.setText("");
        cityTextField.setText("");
        streetTextField.setText("");
        postalCodeTextField.setText("");

        return false;
    }

    private void addSupplier(String name, String nip, String email, String country, String city, String street, String postalCode) throws FailedAddingSupplierException {
        for(SuppliersEntity supplier: suppliersEntities) {
            if(supplier.getName().equals(name)) {
                throw new FailedAddingSupplierException();
            }
        }

        StoredProcedureQuery procedureQuery = session.createStoredProcedureQuery("add_supplier").
                registerStoredProcedureParameter("name", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("nip", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("country", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("city", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("street", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("postalCode", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("email", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("id", Integer.class, ParameterMode.OUT)
                .setParameter("name", name).setParameter("nip", nip).setParameter("country", country)
                .setParameter("city", city).setParameter("street", street)
                .setParameter("postalCode", postalCode).setParameter("email", email);

        procedureQuery.execute();
        chosenSupplierId = (Integer) procedureQuery.getOutputParameterValue("id");
    }

    private void displayErrorAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public boolean supplierIsChosen() {
        boolean someDataFilled = !nameTextField.getText().isBlank() ||
                !nipTextField.getText().isBlank() || !emailTextField.getText().isBlank() ||
                !countryTextField.getText().isBlank() || !cityTextField.getText().isBlank() ||
                !streetTextField.getText().isBlank() || !postalCodeTextField.getText().isBlank();

        if(someDataFilled) {
            if(supplierAddedSuccessfully()) {
                ControllerCommunicator.getInstance().setMsg(chosenSupplierId.toString());
                return true;
            }
        } else {
            if(chosenSupplierId != null) {
                ControllerCommunicator.getInstance().setMsg(chosenSupplierId.toString());
                return true;
            }
            displayErrorAlert("You have to choose a supplier!");
        }

        return false;
    }
}
