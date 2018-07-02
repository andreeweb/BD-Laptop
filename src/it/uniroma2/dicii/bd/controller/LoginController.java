package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.UserDao;
import it.uniroma2.dicii.bd.model.User;
import it.uniroma2.dicii.bd.utils.Sha;

/**
 * Controller class for Login Use Case
 *
 * @author Andrea Cerra
 */

public class LoginController {

    /**
     * Passing a UserBean, this method takes care of
     * validating the username and password received from the boundary.
     *
     * @param userBean user bean which contains username and password received from the boundary
     * @throws DaoException error in user search
     */
    public UserBean validateLogin(UserBean userBean) throws DaoException {

        UserDao dao = DaoFactory.getSingletonInstance().getUserDAO();
        User user = dao.getUserByUsernameAndPassword(userBean.getUsername(), Sha.sha256(userBean.getPassword()));

        userBean.setUserID(user.getUserID());
        userBean.setUserRole(user.getUserRole());
        userBean.setName(user.getName());
        userBean.setSurname(user.getSurname());
        userBean.setUsername(user.getUsername());
        userBean.setPassword("");
        userBean.setEmail(user.getEmail());

        return userBean;
    }
}
