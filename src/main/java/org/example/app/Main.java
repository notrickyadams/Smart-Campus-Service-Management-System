package org.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        stage.setTitle("University Service System");
        navigateTo("Login.fxml", 800, 850);
        stage.show();
    }

    public static void navigateTo(String fxmlFile, double width, double height) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/org/example/app/fxml/" + fxmlFile)
            );
            Parent root = loader.load();
            mainStage.setScene(new Scene(root, width, height));
        } catch (Exception e) {
            System.err.println("Could not load: " + fxmlFile);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}