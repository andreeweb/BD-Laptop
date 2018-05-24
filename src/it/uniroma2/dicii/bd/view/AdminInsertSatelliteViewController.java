package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.controller.InsertDataController;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminInsertSatelliteViewController {

    @FXML
    private TextField insertedName;

    @FXML
    private TextField insertedFirstObservation;

    @FXML
    private TextField insertedLastObservation;

    @FXML
    private TextField insertedAgency;

    @FXML
    private Button backButton;

    @FXML
    private Button saveSatellite;

    @FXML
    private void initialize() {

        backButton.setOnAction(this::backButtonAction);
        saveSatellite.setOnAction(this::saveSatellite);
    }

    /**
     *
     */
    public void onCreateView() {

    }

    /**
     * Save user on db
     *
     * @param event JavaFX event
     */
    public void saveSatellite(ActionEvent event) {

        SatelliteBean satelliteBean = new SatelliteBean(insertedName.getText());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date parsed = format.parse(insertedFirstObservation.getText());
            java.sql.Date sql = new java.sql.Date(parsed.getTime());
            satelliteBean.setFirstObservation(sql);

            parsed = format.parse(insertedLastObservation.getText());
            sql = new java.sql.Date(parsed.getTime());
            satelliteBean.setLastObservation(sql);

        } catch (ParseException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Date format must be yyyy-MM-dd");
            alert.showAndWait();

            e.printStackTrace();

            return;
        }

        satelliteBean.setAgency(insertedAgency.getText());

        InsertDataController controller = new InsertDataController();

        try {

            controller.saveSatellite(satelliteBean);
            SceneManager.getSingletonInstance().showAdminInsertDataView(0);

        } catch (DaoException ex) {

            ExceptionDialog exceptionDialog = new ExceptionDialog(ex);
            exceptionDialog.show();
        }
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminInsertDataView(0);
    }
}
