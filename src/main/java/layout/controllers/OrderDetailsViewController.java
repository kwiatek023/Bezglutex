package layout.controllers;

import database.connection.SessionManager;
import database.entities.ProductsEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import layout.App;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.io.IOException;
import java.math.BigDecimal;

public class OrderDetailsViewController {
  private static final ObservableList<ProductsEntity> productsEntities = FXCollections.observableArrayList();
  private static SessionFactory factory;

  @FXML
  public static BorderPane DetailsBorderPane = new BorderPane();

  @FXML
  public static TableView<ProductsEntity> tableView = new TableView<>();

  @FXML
  public static TableColumn productId = new TableColumn<ProductsEntity, Integer>();

  @FXML
  public static TableColumn typeOfProduct = new TableColumn<ProductsEntity, String>();

  @FXML
  public static TableColumn price = new TableColumn<ProductsEntity, BigDecimal>();

  @FXML
  public static void initialize(int orderId) {
    tableView.prefWidthProperty().bind(DetailsBorderPane.widthProperty().divide(1.5));
    productId.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    typeOfProduct.prefWidthProperty().bind(tableView.widthProperty().divide(4));
    price.prefWidthProperty().bind(tableView.widthProperty().divide(4));

//    SessionManager sessionManager = SessionManager.getInstance();
//    Session session1 = sessionManager.getCurrentSession();
//    session1.close();
//
//
//    try {
//      factory = new Configuration().configure().buildSessionFactory();
//    } catch (Throwable ex) {
//      System.err.println("Failed to create sessionFactory object." + ex);
//      throw new ExceptionInInitializerError(ex);
//    }
//
//    Session session = factory.openSession();
//    Transaction tx = null;

//    try {
//      tx = session.beginTransaction();
//      Query query = session.createQuery("FROM ProductsEntity ");
//      productsEntities.addAll(query.getResultList());
//      tableView.setItems(productsEntities);
//      tx.commit();
//    } catch (HibernateException e) {
//      if (tx!=null) tx.rollback();
//      e.printStackTrace();
//    } finally {
//      session.close();
//    }




    //tableView.setEditable(true);
    productId.setCellValueFactory(new PropertyValueFactory<ProductsEntity, Integer>("productId"));
    typeOfProduct.setCellValueFactory(new PropertyValueFactory<ProductsEntity, String>("type"));
    price.setCellValueFactory(new PropertyValueFactory<ProductsEntity, BigDecimal>("price"));

    try {
      final Parent parent = FXMLLoader.load(App.class.getResource("orderDetailsView.fxml"));
      Stage stage = App.getStage();
      stage.setScene(new Scene(parent));
    }
    catch (IOException ex) {
      System.out.println(":(");
    }

  }

}
