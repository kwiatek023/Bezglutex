package layout.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Window;
import layout.App;

import java.io.IOException;
import java.util.Optional;

public class AddProductsController {
    private int supplyId;
    private boolean supplyIsEmpty = true;

    public boolean supplyIsEmpty() {
        return supplyIsEmpty;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public void addBreadstuff(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding breadstuff");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("addBreadstuffView.fxml"));

        AddBreadstuffController addBreadstuffController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            addBreadstuffController = fxmlLoader.getController();
            addBreadstuffController.setSupplyId(supplyId);
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            if(addBreadstuffController.isAnythingIsAdded()) {
                supplyIsEmpty = false;
            }
        }
    }

    public void addPasta(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding pasta");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("addPastaView.fxml"));

        AddPastaController addPastaController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            addPastaController = fxmlLoader.getController();
            addPastaController.setSupplyId(supplyId);
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            if(addPastaController.isAnythingIsAdded()) {
                supplyIsEmpty = false;
            }
        }
    }

    public void addDesserts(ActionEvent actionEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setWidth(950);
        dialog.setHeight(800);
        dialog.setTitle("Adding dessert");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        Window window = dialog.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> window.hide());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("addDessertView.fxml"));

        AddDessertController addDessertController = null;
        try {
            Parent dialogContent = fxmlLoader.load();
            addDessertController = fxmlLoader.getController();
            addDessertController.setSupplyId(supplyId);
            dialog.getDialogPane().setContent(dialogContent);
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.CLOSE) {
            if(addDessertController.isAnythingIsAdded()) {
                supplyIsEmpty = false;
            }
        }
    }
}
