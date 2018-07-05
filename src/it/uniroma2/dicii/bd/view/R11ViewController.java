package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;

public class R11ViewController {

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField branchTextField;

    @FXML
    private TextField filamentTextField;

    @FXML
    private Label minVertexLabel;

    @FXML
    private Label maxVertexLabel;

    @FXML
    private Label maxVertexDistanceLabel;

    @FXML
    private Label minVertexDistanceLabel;


    /**
     *
     */
    public void onCreateView() {

        backButton.setOnAction(this::backButtonAction);
        searchButton.setOnAction(this::searchButtonAction);
    }

    /**
     * Search button action
     *
     * @param event JavaFX event
     */
    private void searchButtonAction(ActionEvent event){

        SearchController controller = new SearchController();

        try {

            Integer branchID = Integer.valueOf(branchTextField.getText());
            Integer filamentID = Integer.valueOf(filamentTextField.getText());

            List<GPointBean> vertex = controller.getBranchVertex(branchID, filamentID);

            GPointBean minVertex = vertex.get(1);
            GPointBean maxVertex = vertex.get(0);

            String minVertexText = "Longitude " + String.valueOf(minVertex.getGlongitude()) +
                                    " - Latitude " + String.valueOf(minVertex.getGlatitude());

            minVertexLabel.setText(minVertexText);

            String maxVertexText = "Longitude " + String.valueOf(maxVertex.getGlongitude()) +
                                    " - Latitude " + String.valueOf(maxVertex.getGlatitude());

            maxVertexLabel.setText(maxVertexText);

            // distance
            minVertexDistanceLabel.setText(String.valueOf(controller.getVertexDistanceFromBoundary(minVertex, filamentID)));
            maxVertexDistanceLabel.setText(String.valueOf(controller.getVertexDistanceFromBoundary(maxVertex, filamentID)));

        } catch(NumberFormatException nfe) {

            nfe.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please insert a correct value");
            alert.showAndWait();

        } catch (DaoException e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Branch not found!");
            alert.showAndWait();
        }
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showSearchView();
    }
}
