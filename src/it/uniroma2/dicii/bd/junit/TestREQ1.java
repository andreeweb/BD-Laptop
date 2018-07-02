package it.uniroma2.dicii.bd.junit;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.controller.LoginController;
import it.uniroma2.dicii.bd.exception.DaoException;
import org.junit.Assert;
import org.junit.Test;

public class TestREQ1 {

    @Test
    public void testLogin() throws DaoException {

        UserBean userBean = new UserBean();
        userBean.setUsername("andreeweb");
        userBean.setPassword("password");

        LoginController loginController = new LoginController();
        UserBean userBeanResult = loginController.validateLogin(userBean);

        Assert.assertEquals("andrea.cerra@me.com", userBeanResult.getEmail());
        Assert.assertEquals("Andrea", userBeanResult.getName());
        Assert.assertEquals("Cerra", userBeanResult.getSurname());
    }

    @Test (expected = DaoException.class)
    public void testLoginException() throws DaoException {

        UserBean userBean = new UserBean();
        userBean.setUsername("fakeusername");
        userBean.setPassword("fakepassword");

        LoginController loginController = new LoginController();
        loginController.validateLogin(userBean);
    }
}