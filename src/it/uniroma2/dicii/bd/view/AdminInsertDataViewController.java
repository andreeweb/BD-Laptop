package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.SatelliteBean;
import it.uniroma2.dicii.bd.bean.ToolBean;
import it.uniroma2.dicii.bd.controller.InsertDataController;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class AdminInsertDataViewController {

    @FXML
    private Button backButton;

    // List satellites

    @FXML
    private TableView<SatelliteBean> satelliteBeanTableView;

    @FXML
    private TableColumn<SatelliteBean, String> nameColumn;

    @FXML
    private TableColumn<SatelliteBean, String> firstObColumn;

    @FXML
    private TableColumn<SatelliteBean, String> lastObColumn;

    @FXML
    private TableColumn<SatelliteBean, String> agencyColumn;

    @FXML
    private Button addSatelliteButton;

    private ObservableList<SatelliteBean> satelliteBeans = FXCollections.observableArrayList();

    // List tools
    @FXML
    private TableView<ToolBean> toolsBeanTableView;

    @FXML
    private TableColumn<ToolBean, String> toolsNameColumn;

    @FXML
    private TableColumn<ToolBean, String> bandColumn;

    @FXML
    private TableColumn<ToolBean, String> satelliteColumn;

    @FXML
    private Button addToolButton;

    private ObservableList<ToolBean> toolBeans = FXCollections.observableArrayList();

    // other

    @FXML
    private TabPane tabPane;

    @FXML
    private void initialize() {

        satelliteBeanTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> rowActionSatellite(newValue));
        satelliteBeanTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        toolsBeanTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> rowActionTools(newValue));
        toolsBeanTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backButton.setOnAction(this::backButtonAction);
        addSatelliteButton.setOnAction(this::addSatelliteAction);
        addToolButton.setOnAction(this::addToolsAction);
    }

    /**
     *
     */
    public void onCreateView(Integer tabSelected) {

        tabPane.getSelectionModel().select(tabSelected);

        InsertDataController controller = new InsertDataController();

        try {

            List<SatelliteBean> listS = controller.getSatelliteBeanList();
            satelliteBeans.addAll(listS);
            satelliteBeanTableView.setItems(satelliteBeans);

            List<ToolBean> listT = controller.getToolBeanList();
            toolBeans.addAll(listT);
            toolsBeanTableView.setItems(toolBeans);

        } catch (DaoException e) {

            e.printStackTrace();
            System.exit(1);
        }

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        firstObColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstObservationString()));
        lastObColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastObservationString()));
        agencyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAgency()));

        toolsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        bandColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBand()));
        satelliteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSatellite().getName()));
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
     * Add satellite action
     *
     * @param actionEvent JavaFX event
     */
    private void addSatelliteAction(ActionEvent actionEvent) {

        SceneManager.getSingletonInstance().showAdminInsertSatelliteView();
    }

    /**
     * Add tools action
     *
     * @param actionEvent JavaFX event
     */
    private void addToolsAction(ActionEvent actionEvent) {

        SceneManager.getSingletonInstance().showAdminInsertToolView();
    }

    /**
     * Detail row action
     *
     * @param satelliteBean
     */
    public void rowActionSatellite(SatelliteBean satelliteBean){

        //System.out.println(userBean.toString());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete the satellite: " + satelliteBean.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            InsertDataController controller = new InsertDataController();

            try {
                controller.deleteSatellite(satelliteBean);
            } catch (DaoException e) {

                ExceptionDialog exceptionDialog = new ExceptionDialog(e);
                exceptionDialog.show();

                e.printStackTrace();
            }
        }

        SceneManager.getSingletonInstance().showAdminInsertDataView(0);
    }

    /**
     * Detail row action
     *
     * @param toolBean
     */
    public void rowActionTools(ToolBean toolBean){

        //System.out.println(userBean.toString());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete the tool: " + toolBean.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            InsertDataController controller = new InsertDataController();

            try {
                controller.deleteTool(toolBean);
            } catch (DaoException e) {

                ExceptionDialog exceptionDialog = new ExceptionDialog(e);
                exceptionDialog.show();

                e.printStackTrace();
            }
        }

        SceneManager.getSingletonInstance().showAdminInsertDataView(1);
    }
}
