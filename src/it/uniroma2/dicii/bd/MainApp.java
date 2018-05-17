package it.uniroma2.dicii.bd;

import it.uniroma2.dicii.bd.dao.ConnectionManager;
import it.uniroma2.dicii.bd.view.SceneManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.dbcp2.BasicDataSource;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Astra");
        //primaryStage.getIcons().add(new Image("file:resources/images/"));

        // onCreateView with root layout

        try {

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("view/RootView.fxml"));
            BorderPane rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            // call SceneManager
            SceneManager sm = SceneManager.getSingletonInstance();
            sm.setRootLayout(rootLayout);
            sm.setRootController(loader.getController());
            sm.init();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {

        launch(args);
    }
}
