package org.svaltqt;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    private static Stage stg;

    public Stage getMainStage() {
        return stg;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/VentanaPrincipal.fxml"));
        primaryStage.setTitle("Docentes");
        primaryStage.setScene((new Scene(root, 967, 708)));
        primaryStage.show();
    }

    public void changeScene(String fxml, Stage stage) throws IOException {

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setWidth(600);
        stg.setHeight(500);
        stg.getScene().setRoot(pane);

    }

    public static void main(String[] args) {
        launch();
    }

}