package layout.controllers;

import database.connection.SessionManager;
import database.entities.BreadstuffEntity;
import database.entities.DessertsEntity;
import database.entities.PastaEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import layout.communication.ControllerCommunicator;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ProductViewController {
  private SessionManager sessionManager;
  private int productId;
  String productType;

  @FXML
  public BorderPane productBorderPane;

  @FXML
  public Label productInfo;

  @FXML
  public void initialize() {
    sessionManager = SessionManager.getInstance();

    // msg format: "productId productType"
    String[] msg = ControllerCommunicator.getInstance().getMsg().split(" ");
    productId = Integer.parseInt(msg[0]);
    productType = msg[1];
    
    Session session = sessionManager.openSession();
    switch (productType) {
      case "breadstuff": {
        Query<BreadstuffEntity> query = session.createQuery("FROM BreadstuffEntity WHERE productId = (:productId)", BreadstuffEntity.class);
        query.setParameter("productId", productId);
        
        BreadstuffEntity product = query.getSingleResult();
        productInfo.setText("Energy value: " + product.getEnergyValue() + " kcal\n" +
                            "Netto weight: " + product.getNettoWeight() + "g\n" +
                            "Pieces per package: " + product.getPiecesPerPackage() + "\n" +
                            "Type: " + product.getType() + "\n"
        );
        break;
      }
      case "pasta": {
        Query<PastaEntity> query = session.createQuery("FROM PastaEntity WHERE productId = (:productId)", PastaEntity.class);
        query.setParameter("productId", productId);

        PastaEntity product = query.getSingleResult();
        productInfo.setText("Energy value: " + product.getEnergyValue() + " kcal\n" +
            "Netto weight: " + product.getNettoWeight() + "g\n" +
            "Boil time: " + product.getBoilTime() + "s\n" +
            "Type: " + product.getType() + "\n"
        );
        break;
      }
      case "dessert": {
        Query<DessertsEntity> query = session.createQuery("FROM DessertsEntity WHERE productId = (:productId)", DessertsEntity.class);
        query.setParameter("productId", productId);

        DessertsEntity product = query.getSingleResult();
        productInfo.setText("Name: " + product.getName() + "\n" +
            "Energy value: " + product.getEnergyValue() + " kcal\n" +
            "Netto weight: " + product.getNettoWeight() + "g\n" +
            "Dairy free: " + product.getDairyFree() + "\n"
        );
        break;
      }
    }
  }
}
