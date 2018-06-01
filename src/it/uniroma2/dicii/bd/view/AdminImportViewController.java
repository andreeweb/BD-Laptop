package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.controller.ImportController;
import it.uniroma2.dicii.bd.controller.InsertDataController;
import it.uniroma2.dicii.bd.exception.DaoException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AdminImportViewController {

    private Stage stage;

    @FXML
    private TextField importFilamentTextField;

    @FXML
    private Button selectFileButton;

    @FXML
    private ProgressBar importProgress;

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> satelliteComboBox;

    private ObservableList<String> satelliteBeanNames = FXCollections.observableArrayList();

    @FXML
    private HBox satelliteHBox;

    @FXML
    private TextArea exampleTextArea;

    @FXML
    private Button importButton;

    @FXML
    private Label statusLabel;

    private String CSVType = "noType";
    private String filePath = "";

    @FXML
    private void initialize() {

        selectFileButton.setOnAction(this::selectFileButtonAction);
        backButton.setOnAction(this::backButtonAction);
        importButton.setOnAction(this::importButtonAction);

        statusLabel.setVisible(false);

        // show only when import star file
        satelliteComboBox.setVisible(false);
        satelliteComboBox.setManaged(false);
    }

    public void onCreateView(Pane view){

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Select CSV Type",
                        "CSV Filament (eg. filamenti_Spitzer.csv)",
                        "CSV Boundary (eg. contorni_filamenti_Spitzer.csv)",
                        "CSV Skeleton (eg. scheletro_filamenti_Spitzer.csv)",
                        "CSV Star (eg. stelle_Herschel.csv)"
                );

        typeComboBox.setValue("Select CSV Type");
        typeComboBox.setItems(options);

        typeComboBox.valueProperty().addListener(new ChangeListener<String>() {
                                                   @Override public void changed(ObservableValue ov, String t, String t1) {

                                                       satelliteComboBox.setVisible(false);
                                                       satelliteComboBox.setManaged(false);

                                                       switch (t1){

                                                           case "Select CSV Type":{
                                                               CSVType = "noType";
                                                               exampleTextArea.setText("");
                                                               break;
                                                           }

                                                           case "CSV Filament (eg. filamenti_Spitzer.csv)":{
                                                               CSVType = "filament";
                                                               exampleTextArea.setText("IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT\n" +
                                                                       "379,SDC10.001-0.361,67.8000,1.3431856e+23,15.53,1.89333,1.89796,Spitzer,IRAC");
                                                               break;
                                                           }

                                                           case "CSV Boundary (eg. contorni_filamenti_Spitzer.csv)":{
                                                               CSVType = "boundary";
                                                               exampleTextArea.setText("IDFIL,GLON_CONT,GLAT_CONT\n" +
                                                                       "45,13.91816,-1.26974");
                                                               break;
                                                           }

                                                           case "CSV Skeleton (eg. scheletro_filamenti_Spitzer.csv)":{
                                                               CSVType = "skeleton";
                                                               exampleTextArea.setText("IDFIL,IDBRANCH,TYPE,GLON_BR,GLAT_BR,N,FLUX\n" +
                                                                       "45,26,S,13.9134,-1.25217,1,3.4094014e+21");
                                                               break;
                                                           }

                                                           case "CSV Star (eg. stelle_Herschel.csv)":{
                                                               CSVType = "star";
                                                               satelliteComboBox.setVisible(true);
                                                               satelliteComboBox.setManaged(true);
                                                               exampleTextArea.setText("IDSTAR,NAMESTAR,GLON_ST,GLAT_ST,FLUX_ST,TYPE_ST\n" +
                                                                       "23813,HIGALPS005.0014+0.0856,5.0004370,0.084881000,36.8936,PROTOSTELLAR");
                                                               break;
                                                           }
                                                       }

                                                   }
        });


        InsertDataController controller = new InsertDataController();

        try {

            List<SatelliteBean> listS = controller.getSatelliteBeanList();

            satelliteBeanNames.add("Select Satellite");

            for (SatelliteBean satelliteBean : listS){
                satelliteBeanNames.add(satelliteBean.getName());
            }

            satelliteComboBox.setItems(satelliteBeanNames);
            satelliteComboBox.setValue("Select Satellite");

        } catch (DaoException e) {

            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *
     * Set stage from SceneManager for open file explorer
     *
     * @param stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Login button action
     *
     * @param event JavaFX event
     */
    private void selectFileButtonAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );

        File file = fileChooser.showOpenDialog(stage);

        filePath = file.getPath();
        importFilamentTextField.setText(filePath);
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
     * Import button action
     *
     * @param event JavaFX event
     */
    private void importButtonAction(ActionEvent event){

        if (filePath.length() == 0){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Please select a file for import!");
            alert.showAndWait();

            return;
        }

        switch (CSVType){

            case "noType":{

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Please select CSV type!");
                alert.showAndWait();

                break;
            }

            case "filament":{

                this.updateGuiBeforeImport();

                Task<Integer> task = new Task<Integer>() {
                    @Override protected Integer call() throws Exception {

                        ImportController importController = new ImportController();
                        double time = importController.importFilament(filePath);

                        // Update the GUI when done
                        Platform.runLater(() -> {
                            statusLabel.setText("Import done in: " + time + " seconds.");
                        });

                        return null;
                    }
                };

                new Thread(task).start();

                task.setOnFailed(evt -> {
                    this.updateGuiOnTaskFail();
                    task.getException().printStackTrace(System.err);
                });

                task.setOnSucceeded(evt -> {
                    this.updateGuiAfterImport();
                });

                break;

            }

            case "boundary":{

                this.updateGuiBeforeImport();

                Task<Integer> task = new Task<Integer>() {
                    @Override protected Integer call() throws Exception {

                        ImportController importController = new ImportController();
                        double time = importController.importBoundary(filePath);

                        // Update the GUI when done
                        Platform.runLater(() -> {
                            statusLabel.setText("Import done in: " + time + " seconds.");
                        });

                        return null;
                    }
                };

                new Thread(task).start();

                task.setOnFailed(evt -> {
                    this.updateGuiOnTaskFail();
                    task.getException().printStackTrace(System.err);
                });

                task.setOnSucceeded(evt -> {
                    this.updateGuiAfterImport();
                });

                break;
            }

            case "skeleton":{

                this.updateGuiBeforeImport();

                Task<Integer> task = new Task<Integer>() {
                    @Override protected Integer call() throws Exception {

                        ImportController importController = new ImportController();
                        double time = importController.importSkeleton(filePath);

                        // Update the GUI when done
                        Platform.runLater(() -> {
                            statusLabel.setText("Import done in: " + time + " seconds.");
                        });

                        return null;
                    }
                };

                new Thread(task).start();

                task.setOnFailed(evt -> {
                    this.updateGuiOnTaskFail();
                    task.getException().printStackTrace(System.err);
                });

                task.setOnSucceeded(evt -> {
                    this.updateGuiAfterImport();
                });

                break;
            }

            case "star":{

                String satelliteName = satelliteComboBox.getSelectionModel().getSelectedItem();
                SatelliteBean satelliteBean = new SatelliteBean(satelliteName);

                if (satelliteName.equals("Select Satellite")){

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Please select a satellite!");
                    alert.showAndWait();

                    return;
                }

                this.updateGuiBeforeImport();

                Task<Integer> task = new Task<Integer>() {
                    @Override protected Integer call() throws Exception {

                        ImportController importController = new ImportController();
                        double time = importController.importStar(filePath, satelliteBean);

                        // Update the GUI when done
                        Platform.runLater(() -> {
                            statusLabel.setText("Import done in: " + time + " seconds.");
                        });

                        return null;
                    }
                };

                new Thread(task).start();

                task.setOnFailed(evt -> {
                    this.updateGuiOnTaskFail();
                    task.getException().printStackTrace(System.err);
                });

                task.setOnSucceeded(evt -> {
                    this.updateGuiAfterImport();
                });

                break;
            }
        }
    }

    /**
     *
     * Enable/disable GUI action BEFORE update
     *
     */
    private void updateGuiBeforeImport() {

        importProgress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        statusLabel.setVisible(true);
        statusLabel.setText("Import in progress...");
        selectFileButton.setDisable(true);
        typeComboBox.setDisable(true);
        importButton.setDisable(true);
        importFilamentTextField.setDisable(true);
        backButton.setDisable(true);
        satelliteComboBox.setDisable(true);
    }

    /**
     *
     * Enable/disable GUI action AFTER update
     *
     */
    private void updateGuiAfterImport() {

        importProgress.setProgress(100.0);
        selectFileButton.setDisable(false);
        typeComboBox.setDisable(false);
        importButton.setDisable(false);
        importFilamentTextField.setDisable(false);
        backButton.setDisable(false);
        satelliteComboBox.setDisable(false);
    }

    /**
     *
     * Update GUI when import fail
     *
     */
    private void updateGuiOnTaskFail () {

        importProgress.setProgress(0.0);
        selectFileButton.setDisable(false);
        typeComboBox.setDisable(false);
        importButton.setDisable(false);
        importFilamentTextField.setDisable(false);
        backButton.setDisable(false);
        satelliteComboBox.setDisable(false);
        statusLabel.setText("Import error, please select correct type and csv!");

    }

}
