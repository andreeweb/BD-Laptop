package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class AdminHomeViewController {

    @FXML
    private Button importButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button userManagementButton;

    @FXML
    private Button insertDataButton;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {

        importButton.setOnAction(this::importButtonAction);
        userManagementButton.setOnAction(this::userButtonAction);
        logoutButton.setOnAction(this::logoutAction);
        insertDataButton.setOnAction(this::insertDataButtonAction);
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
     * Login button action
     *
     * @param event JavaFX event
     */
    private void importButtonAction(ActionEvent event) {
        SceneManager.getSingletonInstance().showAdminImportView();
    }

    /**
     * User button action
     *
     * @param event JavaFX event
     */
    private void userButtonAction(ActionEvent event) {
        SceneManager.getSingletonInstance().showAdminUserManagementView();
    }

    /**
     * Insert data button action
     *
     * @param event JavaFX event
     */
    private void insertDataButtonAction(ActionEvent event) {
        SceneManager.getSingletonInstance().showAdminInsertDataView(0);
    }

    /**
     * Search button action
     *
     * @param event JavaFX event
     */
    private void searchButtonAction(ActionEvent event) {
        SceneManager.getSingletonInstance().showAdminSearchView();
    }
}
