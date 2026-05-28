package org.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class JavaFXMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/app/resources/AdminDashboard.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}