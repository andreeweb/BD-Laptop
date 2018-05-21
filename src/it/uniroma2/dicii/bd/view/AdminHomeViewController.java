package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class AdminHomeViewController {

    @FXML
    private Button importButton;

    @FXML
    private void initialize() {

        importButton.setOnAction(this::importButtonAction);
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

}
