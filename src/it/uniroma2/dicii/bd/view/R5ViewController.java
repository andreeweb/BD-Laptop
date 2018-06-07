package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.model.GPoint;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class R5ViewController {

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label centroidLatitudeLabel;

    @FXML
    private Label centroidLongitudeLabel;

    @FXML
    private Label extensionLatitudeLabel;

    @FXML
    private Label extensionLongitueLabel;

    @FXML
    private Label numberOfbranchLabel;

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

        // try by ID
        try {

            Integer filamentID = Integer.parseInt(searchTextField.getText());

            GPointBean centroidByID = controller.getFilamentCentroidByID(filamentID);
            GPointBean extensionByID = controller.getFilamentExtensionByID(filamentID);
            Integer numberOfSegmentByID = controller.getNumberOfBranchByID(filamentID);

            centroidLatitudeLabel.setText(String.valueOf(centroidByID.getGlatitude()));
            centroidLongitudeLabel.setText(String .valueOf(centroidByID.getGlongitude()));
            extensionLatitudeLabel.setText(String.valueOf(extensionByID.getGlatitude()));
            extensionLongitueLabel.setText(String.valueOf(extensionByID.getGlongitude()));
            numberOfbranchLabel.setText(String.valueOf(numberOfSegmentByID));


        } catch(NumberFormatException nfe) {

            // try by name
            try {

                String filamentName = searchTextField.getText();

                GPointBean centroidByID = controller.getFilamentCentroidByName(filamentName);
                GPointBean extensionByID = controller.getFilamentExtensionByName(filamentName);
                Integer numberOfSegmentByID = controller.getNumberOfBranchByName(filamentName);

                centroidLatitudeLabel.setText(String.valueOf(centroidByID.getGlatitude()));
                centroidLongitudeLabel.setText(String .valueOf(centroidByID.getGlongitude()));
                extensionLatitudeLabel.setText(String.valueOf(extensionByID.getGlatitude()));
                extensionLongitueLabel.setText(String.valueOf(extensionByID.getGlongitude()));
                numberOfbranchLabel.setText(String.valueOf(numberOfSegmentByID));


            } catch (DaoException e) {

                e.printStackTrace();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Filament not found!");
                alert.showAndWait();

            }

        } catch (DaoException e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Filament not found!");
            alert.showAndWait();
        }


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
