package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.exception.DaoException;

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

        /*UserDao dao = DaoFactory.getSingletonInstance().getUserDAO(Persistence.File);
        User user = dao.getUserByUsernameAndPassword(userBean.getUsername(), Sha.sha256(userBean.getPassword()));

        userBean.setUserRole(user.getUserRole());
        userBean.setName(user.getName());
        userBean.setSurname(user.getSurname());

        return userBean;*/

        return null;
    }
}
