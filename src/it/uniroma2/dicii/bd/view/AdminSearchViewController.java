package it.uniroma2.dicii.bd.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminSearchViewController {

    @FXML
    private Button backButton;

    @FXML
    private Button reqFN5;

    @FXML
    private Button reqFN6;

    @FXML
    private Button reqFN7;

    @FXML
    private Button reqFN8;

    @FXML
    private Button reqFN9;

    @FXML
    private Button reqFN10;

    @FXML
    private Button reqFN11;

    @FXML
    private Button reqFN12;

    /**
     *
     */
    public void onCreateView() {

        backButton.setOnAction(this::backButtonAction);
        reqFN5.setOnAction(this::reqFN5ButtonAction);
        reqFN6.setOnAction(this::reqFN6ButtonAction);
        reqFN7.setOnAction(this::reqFN7ButtonAction);
        reqFN8.setOnAction(this::reqFN8ButtonAction);
        reqFN9.setOnAction(this::reqFN9ButtonAction);
        reqFN10.setOnAction(this::reqFN10ButtonAction);
        reqFN11.setOnAction(this::reqFN11ButtonAction);
        reqFN12.setOnAction(this::reqFN12ButtonAction);
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminHomeView();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN5ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR5ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN6ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR6ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN7ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR7ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN8ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR8ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN9ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR9ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN10ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR10ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN11ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR11ViewController();
    }

    /**
     *
     *
     * @param event JavaFX event
     */
    private void reqFN12ButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showR12ViewController();
    }
}
