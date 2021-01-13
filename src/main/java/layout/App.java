package layout;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
  private static Scene scene;

  private static Stage stage;

  @Override
  public void start(Stage primaryStage) throws IOException {
    scene = new Scene(loadFXML("login"), 1100, 825);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Bezglutex");
    primaryStage.show();
    primaryStage.setOnCloseRequest(e -> {
      Platform.exit();
      System.exit(0);
    });

    stage = primaryStage;
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(loadFXML(fxml));
  }

  private static Parent loadFXML(String fxml) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    return fxmlLoader.load();
  }

  public static Stage getStage() {
    return stage;
  }

  public static void main(String[] args) {
    launch();
  }
}