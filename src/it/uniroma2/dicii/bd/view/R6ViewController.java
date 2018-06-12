package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.FilamentBean;
import it.uniroma2.dicii.bd.controller.SearchController;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class R6ViewController {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<Float> minComboEllipticity;

    @FXML
    private ComboBox<Float> maxComboEllipticity;

    @FXML
    private TextField luminanceTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private TableView<FilamentBean> filamentBeanTableView;

    @FXML
    private TableColumn<FilamentBean, String> idColumn;

    @FXML
    private TableColumn<FilamentBean, String> nameColumn;

    @FXML
    private TableColumn<FilamentBean, String> fluxColumn;

    @FXML
    private TableColumn<FilamentBean, String> densityColumn;

    @FXML
    private TableColumn<FilamentBean, String> temperatureColumn;

    @FXML
    private TableColumn<FilamentBean, String> ellipticityColumn;

    @FXML
    private TableColumn<FilamentBean, String> contrastColumn;

    private ObservableList<FilamentBean> filamentBeans = FXCollections.observableArrayList();

    // pagination
    private Integer limit = 20;
    private Integer offset = 0;

    /**
     *
     */
    public void onCreateView() {

        filamentBeanTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backButton.setOnAction(this::backButtonAction);
        searchButton.setOnAction(this::searchButtonAction);
        prevPageButton.setOnAction(this::prevButtonAction);
        nextPageButton.setOnAction(this::nextButtonAction);

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdfil().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        fluxColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalFlux().toString()));
        densityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeanDensity().toString()));
        temperatureColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMeanTemperature().toString()));
        ellipticityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEllipticity().toString()));
        contrastColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContrast().toString()));

        ObservableList<Float> options = FXCollections.observableArrayList(2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f);

        minComboEllipticity.setValue(2.0f);
        minComboEllipticity.setItems(options);

        maxComboEllipticity.setValue(9.0f);
        maxComboEllipticity.setItems(options);

    }


    /**
     * Search button action
     *
     * @param event JavaFX event
     */
    private void searchButtonAction(ActionEvent event){

        // remove all data in table
        filamentBeanTableView.getItems().clear();

        SearchController controller = new SearchController();

        Double luminance;
        Float ellipticityMin;
        Float ellipticityMax;

        try {

            luminance = Double.valueOf(luminanceTextField.getText());

            if (luminance < 0)
                throw new NumberFormatException("Insert number > 0");

            ellipticityMin = minComboEllipticity.getSelectionModel().getSelectedItem();
            ellipticityMax = maxComboEllipticity.getSelectionModel().getSelectedItem();

            if (ellipticityMax < ellipticityMin)
                throw new NumberFormatException("Min must be minus than Max");

            try {

                List<FilamentBean> listS = controller.getFilamentsByLuminanceAndEllipticityWithLimit(luminance, ellipticityMin, ellipticityMax, limit, offset);
                filamentBeans.addAll(listS);
                filamentBeanTableView.setItems(filamentBeans);

            } catch (DaoException e) {

                ExceptionDialog dialog = new ExceptionDialog(e);
                dialog.show();
            }

        }catch (NumberFormatException nfe){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please insert correct value \n Min value Max value and Luminance > 0");
            alert.showAndWait();
        }
    }

    /**
     * Prev button action
     *
     * @param event JavaFX event
     */
    private void prevButtonAction(ActionEvent event){

        if (offset > 0)
            offset-=20;

        this.searchButton.fire();
    }

    /**
     * Next button action
     *
     * @param event JavaFX event
     */
    private void nextButtonAction(ActionEvent event){

        if (filamentBeans.size() == 20)
            offset+=20;

        this.searchButton.fire();
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
