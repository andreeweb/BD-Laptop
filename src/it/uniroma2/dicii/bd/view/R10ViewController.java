package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.GPointBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Map;

public class R10ViewController {

    @FXML
    private TextField latitudeCenterTextField;

    @FXML
    private TextField longitudeCenterTextField;

    @FXML
    private TextField size1TextField;

    @FXML
    private TextField size2TextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private Label totalStarLabel;

    @FXML
    private Label totalStarInsideFilamentLabel;

    @FXML
    private Label totalStarOutsideFilamentLabel;

    @FXML
    private Label protosInsideFilamentLabel;

    @FXML
    private Label prestInsideFilamentLabel;

    @FXML
    private Label unboundInsideFilamentLabel;

    @FXML
    private Label protosOutsideFilamentLabel;

    @FXML
    private Label prestOutsideFilamentLabel;

    @FXML
    private Label unboundOutsideFilamentLabel;

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

        Float sideMin;
        Float sideMax;

        Double centerLatitude;
        Double centerLongitude;

        try {

            centerLatitude = Double.valueOf(latitudeCenterTextField.getText());
            centerLongitude = Double.valueOf(longitudeCenterTextField.getText());

            sideMin = Float.valueOf(size1TextField.getText());
            sideMax = Float.valueOf(size2TextField.getText());

            if (sideMin > sideMax)
                throw new NumberFormatException("SideMin must be minus than SideMax");

            GPointBean gPointBean = new GPointBean(centerLongitude, centerLatitude);

            Map<String, Float> map = controller.getStarInsideRectRegion(gPointBean, sideMin, sideMax);

            totalStarLabel.setText(String.format("%.2f", map.get("totalStar")));

            totalStarInsideFilamentLabel.setText(String.format("%.2f", map.get("totalStarInsideFilament")));
            totalStarOutsideFilamentLabel.setText(String.format("%.2f", map.get("totalStarOutsideFilament")));

            protosInsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfProtostellarInsideFilament"))+"%");
            prestInsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfPrestellarInsideFilament"))+"%");
            unboundInsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfUnboundInsideFilament"))+"%");

            protosOutsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfProtostellarOutsideFilament"))+"%");
            prestOutsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfPrestellarOutsideFilament"))+"%");
            unboundOutsideFilamentLabel.setText(String.format("%.2f", map.get("percentageOfUnboundOutsideFilament"))+"%");

        }catch (NumberFormatException nfe){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please insert correct value.");
            alert.showAndWait();

        }catch (Exception ex){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(ex.getMessage());
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
