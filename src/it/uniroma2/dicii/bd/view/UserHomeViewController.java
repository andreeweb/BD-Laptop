package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class UserHomeViewController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {

        logoutButton.setOnAction(this::logoutAction);
        searchButton.setOnAction(this::searchButtonAction);
    }

    public void onCreateView(Pane view){


    }

    /**
     * Logout
     *
     * @param actionEvent
     */
    private void logoutAction(ActionEvent actionEvent) {
        SceneManager.getSingletonInstance().showLoginView();
    }

    /**
     * Search button action
     *
     * @param event JavaFX event
     */
    private void searchButtonAction(ActionEvent event) {
        SceneManager.getSingletonInstance().showSearchView();
    }
}
