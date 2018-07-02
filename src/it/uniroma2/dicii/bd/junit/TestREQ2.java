package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.controller.LoginController;
import it.uniroma2.dicii.bd.controller.UserManagementController;
import it.uniroma2.dicii.bd.enumeration.UserRole;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.utils.Sha;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestREQ2 {

    @Test
    public void testInsert() throws DaoException {

        UserBean newUserBean = new UserBean();
        newUserBean.setName("UserTestName");
        newUserBean.setSurname("UserTestSurname");
        newUserBean.setUsername("UserTestUsername");
        newUserBean.setPassword(Sha.sha256("UserTestPassword"));
        newUserBean.setEmail("UserTestEmail@email.it");
        newUserBean.setUserRole(UserRole.USER);

        UserManagementController controller = new UserManagementController();
        controller.saveUser(newUserBean);

        List<UserBean> list = controller.getUserBeanList();

        Boolean userFounded = false;
        for (UserBean userBean : list){
            if (userBean.getName().equals("UserTestName") && userBean.getSurname().equals("UserTestSurname"))
                userFounded = true;
        }

        Assert.assertTrue(userFounded);
    }

    @Test
    public void testRemove() throws DaoException {

        UserManagementController controller = new UserManagementController();

        UserBean userBean = new UserBean();
        userBean.setUsername("UserTestUsername");
        userBean.setPassword("UserTestPassword");

        LoginController loginController = new LoginController();
        UserBean userBeanResult = loginController.validateLogin(userBean);

        controller.deleteUser(userBeanResult);

        List<UserBean> list = controller.getUserBeanList();

        Boolean userFounded = false;
        for (UserBean userB : list){
            if (userB.getName().equals("UserTestName") && userB.getSurname().equals("UserTestSurname"))
                userFounded = true;
        }

        Assert.assertFalse(userFounded);
    }
}
