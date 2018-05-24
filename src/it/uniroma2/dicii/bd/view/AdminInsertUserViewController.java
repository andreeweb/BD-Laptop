package it.uniroma2.dicii.bd.view;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.controller.UserManagementController;
import it.uniroma2.dicii.bd.enumeration.UserRole;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.exception.ExceptionDialog;
import it.uniroma2.dicii.bd.utils.Sha;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminInsertUserViewController {

    @FXML
    private Button backButton;

    @FXML
    private TextField insertedName;

    @FXML
    private TextField insertedSurname;

    @FXML
    private TextField insertedUsername;

    @FXML
    private TextField insertedPassword;

    @FXML
    private TextField insertedEmail;

    @FXML
    private Button saveUser;

    @FXML
    private void initialize() {

        backButton.setOnAction(this::backButtonAction);
        saveUser.setOnAction(this::saveUser);
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
    public void saveUser(ActionEvent event) {

        UserBean newUserBean = new UserBean();
        newUserBean.setName(insertedName.getText());
        newUserBean.setSurname(insertedSurname.getText());
        newUserBean.setUsername(insertedUsername.getText());
        newUserBean.setPassword(Sha.sha256(insertedPassword.getText()));
        newUserBean.setEmail(insertedEmail.getText());
        newUserBean.setUserRole(UserRole.USER);

        UserManagementController controller = new UserManagementController();

        try {

            controller.saveUser(newUserBean);

        } catch (DaoException ex) {

            ExceptionDialog exceptionDialog = new ExceptionDialog(ex);
            exceptionDialog.show();

            ex.printStackTrace();
        }

        SceneManager.getSingletonInstance().showAdminUserManagementView();
    }

    /**
     * Back button action
     *
     * @param event JavaFX event
     */
    private void backButtonAction(ActionEvent event){

        SceneManager.getSingletonInstance().showAdminUserManagementView();
    }
}
