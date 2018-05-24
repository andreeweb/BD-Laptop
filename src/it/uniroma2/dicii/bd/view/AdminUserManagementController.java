package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.controller.UserManagementController;
import it.uniroma2.dicii.bd.exception.DaoException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class AdminUserManagementController {

    @FXML
    private Button addUser;

    @FXML
    private Button backButton;

    @FXML
    private TableView<UserBean> userBeanTableViewTableView;

    @FXML
    private TableColumn<UserBean, String> nameColumn;

    @FXML
    private TableColumn<UserBean, String> surnameColumn;

    @FXML
    private TableColumn<UserBean, String> usernameColumn;

    @FXML
    private TableColumn<UserBean, String> emailColumn;

    @FXML
    private TableColumn<UserBean, String> roleColumn;

    private ObservableList<UserBean> userBeans = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        // Listen for selection changes and show the person details when changed.
        userBeanTableViewTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> rowAction(newValue));
        userBeanTableViewTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        backButton.setOnAction(this::backButtonAction);
        addUser.setOnAction(this::addUserAction);
    }

    /**
     *
     */
    public void onCreateView() {

        UserManagementController controller = new UserManagementController();

        try {

            List<UserBean> list = controller.getUserBeanList();
            userBeans.addAll(list);
            userBeanTableViewTableView.setItems(userBeans);

        } catch (DaoException e) {

            e.printStackTrace();
            System.exit(1);
        }

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserRole().toString()));

    }

    /**
     * Add user action
     *
     * @param actionEvent JavaFX event
     */
    private void addUserAction(ActionEvent actionEvent) {

        SceneManager.getSingletonInstance().showAdminInsertUserView();
    }

    /**
     * Detail row action
     *
     * @param userBean
     */
    public void rowAction(UserBean userBean){

        //System.out.println(userBean.toString());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Do you want to delete the user: " + userBean.getUsername());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            UserManagementController controller = new UserManagementController();

            try {
                controller.deleteUser(userBean);
            } catch (DaoException e) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error while deleting user!");
                alert.showAndWait();

                e.printStackTrace();
            }
        }

        SceneManager.getSingletonInstance().showAdminUserManagementView();
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminHomeView();
    }

}
