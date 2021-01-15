package layout.controllers;

import database.PaymentType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import layout.communication.ControllerCommunicator;

public class ChoosePaymentController {
    @FXML
    public ComboBox<String> paymentBox;

    @FXML
    public void initialize() {
        paymentBox.setItems(FXCollections.observableArrayList(PaymentType.valuesToUILabel()));
    }

    public boolean isPaymentChosen() {
        String paymentType = paymentBox.getSelectionModel().getSelectedItem();

        if (paymentType != null) {
            ControllerCommunicator.getInstance().setMsg(paymentType);
            return true;
        }

        displayErrorAlert("You should choose type of payment!");
        return false;
    }

    private void displayErrorAlert(String contextText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
}
