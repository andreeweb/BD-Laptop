package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class R5ViewController {

    @FXML
    private Button backButton;

    /**
     *
     */
    public void onCreateView() {

        backButton.setOnAction(this::backButtonAction);
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminSearchView();
    }
}
