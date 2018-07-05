package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.StarBean;
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

public class R12ViewController {

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private Button nextPageButton;

    @FXML
    private Button prevPageButton;

    @FXML
    private TableView<StarBean> starBeanTableView;

    @FXML
    private TableColumn<StarBean, String> distanceColumn;

    @FXML
    private TableColumn<StarBean, String> nameColumn;

    @FXML
    private TableColumn<StarBean, String> fluxColumn;

    private ObservableList<StarBean> starBeans = FXCollections.observableArrayList();

    // pagination
    private List<StarBean> starBeansList;
    private Integer to = 20;
    private Integer from = 0;

    /**
     *
     */
    public void onCreateView() {

        starBeanTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backButton.setOnAction(this::backButtonAction);
        searchButton.setOnAction(this::searchButtonAction);
        prevPageButton.setOnAction(this::prevButtonAction);
        nextPageButton.setOnAction(this::nextButtonAction);

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        distanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDistanceFromFilamentSpine().toString()));
        fluxColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlux().toString()));

    }

    /**
     * Search button action
     *
     * @param event JavaFX event
     */
    private void searchButtonAction(ActionEvent event){

        to = 20;
        from = 0;

        // remove all data in table
        starBeanTableView.getItems().clear();

        SearchController controller = new SearchController();

        try {

            Integer filamentID = Integer.valueOf(searchTextField.getText());

            try {

                // total list
                starBeansList = controller.getStarsInsideFilament(filamentID);

                starBeans.addAll(starBeansList.subList(from, to));
                starBeanTableView.setItems(starBeans);

            } catch (DaoException e) {

                e.printStackTrace();

                ExceptionDialog dialog = new ExceptionDialog(e);
                dialog.show();
            }

        }catch (NumberFormatException nfe){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please insert correct value.");
            alert.showAndWait();

        }catch (Exception ex){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("No results!");
            alert.showAndWait();

        }
    }

    /**
     * Prev button action
     *
     * @param event JavaFX event
     */
    private void prevButtonAction(ActionEvent event){

        if (to == starBeansList.size()){

            from-=20;
            to-=starBeansList.size()%20;

        }else{

            if (from > 0)
                from-=20;

            if (to > 20)
                to-=20;
        }

        starBeanTableView.getItems().clear();

        starBeans.addAll(starBeansList.subList(from, to));
        starBeanTableView.setItems(starBeans);
    }

    /**
     * Next button action
     *
     * @param event JavaFX event
     */
    private void nextButtonAction(ActionEvent event){

        if (to == starBeansList.size())
            return;

        if (starBeans.size() == 20)
            from+=20;

        if (starBeans.size() == 20)
            to+=20;

        if (to > starBeansList.size())
            to = starBeansList.size();

        starBeanTableView.getItems().clear();

        starBeans.addAll(starBeansList.subList(from, to));
        starBeanTableView.setItems(starBeans);
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
