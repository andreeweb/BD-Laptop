package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * JavaFX (controller) class for RootView.fxml
 *
 * @author Andrea Cerra
 */

public class RootViewController {

    @FXML
    private Label nameLabel;

    @FXML
    private Button exitButton;

    @FXML
    private void initialize() {

        // set action
        exitButton.setOnAction(this::exitButtonAction);
    }

    /**
     * Exit/logout button action
     *
     * @param event JavaFX event
     */
    private void exitButtonAction(ActionEvent event) {

        SceneManager sm = SceneManager.getSingletonInstance();
        sm.showLoginView();
    }

    public void setVisibleExitButton(boolean visible){
        exitButton.setVisible(visible);
    }

    public void setVisibleNameLabel(boolean visible){
        nameLabel.setVisible(visible);
    }
}
