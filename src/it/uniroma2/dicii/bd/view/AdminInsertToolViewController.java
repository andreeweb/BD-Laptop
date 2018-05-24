package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.bean.ToolBean;
import it.uniroma2.dicii.bd.controller.InsertDataController;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import it.uniroma2.dicii.bd.model.Satellite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.List;

public class AdminInsertToolViewController {

    @FXML
    private TextField insertedName;

    @FXML
    private TextField insertedBand;

    @FXML
    private Button backButton;

    @FXML
    private Button saveTool;

    @FXML
    private ComboBox<String> satelliteComboBox;

    private ObservableList<String> satelliteBeanNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        backButton.setOnAction(this::backButtonAction);
        saveTool.setOnAction(this::saveTool);
    }

    /**
     *
     */
    public void onCreateView() {

        InsertDataController controller = new InsertDataController();

        try {

            List<SatelliteBean> listS = controller.getSatelliteBeanList();

            for (SatelliteBean satelliteBean : listS){
                satelliteBeanNames.add(satelliteBean.getName());
            }

            satelliteComboBox.setItems(satelliteBeanNames);

        } catch (DaoException e) {

            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * Save user on db
     *
     * @param event JavaFX event
     */
    public void saveTool(ActionEvent event) {

        if (satelliteComboBox.getSelectionModel().getSelectedIndex() == -1){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Please select satellite!");
            alert.showAndWait();

            return;
        }

        ToolBean toolBean = new ToolBean();
        toolBean.setName(insertedName.getText());
        toolBean.setBand(insertedBand.getText());

        SatelliteBean satelliteBean = new SatelliteBean();
        satelliteBean.setName(satelliteBeanNames.get(satelliteComboBox.getSelectionModel().getSelectedIndex()));

        toolBean.setSatellite(satelliteBean);

        InsertDataController controller = new InsertDataController();

        try {

            controller.saveTool(toolBean);
            SceneManager.getSingletonInstance().showAdminInsertDataView(1);

        } catch (DaoException ex) {

            ExceptionDialog exceptionDialog = new ExceptionDialog(ex);
            exceptionDialog.show();

            ex.printStackTrace();
        }
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminInsertDataView(1);
    }
}
