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

public class R7ViewController {

    @FXML
    private Button backButton;

    @FXML
    private TextField minNumberText;

    @FXML
    private TextField maxNumberText;

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

        Integer minNumberSegment;
        Integer maxNumberSegment;

        try {

            minNumberSegment = Integer.valueOf(minNumberText.getText());
            maxNumberSegment = Integer.valueOf(maxNumberText.getText());

            Integer difference = maxNumberSegment - minNumberSegment;

            System.out.println(difference);

            if (difference <= 2)
                throw new NumberFormatException("Interval difference must be > 2");

            if (maxNumberSegment < minNumberSegment)
                throw new NumberFormatException("Min must be minus than Max");

            try {

                List<FilamentBean> listS = controller.getFilamentsByNumberOfSegments(minNumberSegment, maxNumberSegment, limit, offset);
                filamentBeans.addAll(listS);
                filamentBeanTableView.setItems(filamentBeans);

            } catch (DaoException e) {

                ExceptionDialog dialog = new ExceptionDialog(e);
                dialog.show();
            }

        }catch (NumberFormatException nfe){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(nfe.getMessage());
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

        SceneManager.getSingletonInstance().showSearchView();
    }
}
