package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class AdminHomeViewController {

    @FXML
    private Button importButton;

    @FXML
    private Button userManagementButton;

    @FXML
    private void initialize() {

        importButton.setOnAction(this::importButtonAction);
        userManagementButton.setOnAction(this::userButtonAction);
    }

    public void onCreateView(Pane view){


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

}
