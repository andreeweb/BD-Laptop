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

import java.util.Map;

public class R9ViewController {

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label numberOfStarLabel;

    @FXML
    private Label unbounStarLabel;

    @FXML
    private Label prestellarStarLabel;

    @FXML
    private Label protostellarStarLabel;


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

            Integer filamentID = Integer.parseInt(searchTextField.getText());

            Map<String, Float> map = controller.countStarsInsideFilamentByID(filamentID);

            numberOfStarLabel.setText(String.format("%.0f", map.get("totalStar")));
            unbounStarLabel.setText(String.format("%.2f", map.get("percentageOfUnbound"))+"%");
            prestellarStarLabel.setText(String.format("%.2f", map.get("percentageOfPrestellar"))+"%");
            protostellarStarLabel.setText(String.format("%.2f", map.get("percentageOfProtostellar"))+"%");

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
