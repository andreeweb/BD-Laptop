package it.uniroma2.dicii.bd.controller;

import it.uniroma2.dicii.bd.bean.UserBean;
import it.uniroma2.dicii.bd.dao.DaoFactory;
import it.uniroma2.dicii.bd.enumeration.UserRole;
import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.UserDao;
import it.uniroma2.dicii.bd.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for User Management Use Case
 *
 * @author Andrea Cerra
 */
public class UserManagementController {

    /**
     * Get all user from database
     *
     * @return issue list
     * @throws DaoException error in dao
     */
    public List<UserBean> getUserBeanList() throws DaoException {

        UserDao dao = DaoFactory.getSingletonInstance().getUserDAO();
        List<User> list = dao.getUsers();
        List<UserBean> beanList = new ArrayList<UserBean>();

        for (User user:list) {

            UserBean userBean = new UserBean();

            userBean.setUserID(user.getUserID());
            userBean.setName(user.getName());
            userBean.setSurname(user.getSurname());
            userBean.setUsername(user.getUsername());
            userBean.setUserRole(user.getUserRole());
            userBean.setEmail(user.getEmail());

            beanList.add(userBean);

        }

        return beanList;

    }

    /**
     * Save user on db
     *
     * @param userBean
     * @throws DaoException
     */
    public void saveUser(UserBean userBean) throws DaoException {

        User user = new User();

        user.setName(userBean.getName());
        user.setSurname(userBean.getSurname());
        user.setUsername(userBean.getUsername());
        user.setUserRole(userBean.getUserRole());
        user.setEmail(userBean.getEmail());
        user.setPassword(userBean.getPassword());

        UserDao dao = DaoFactory.getSingletonInstance().getUserDAO();
        dao.insertUser(user);

    }

    /**
     * Delete user from DB
     *
     * @param userBean
     * @throws DaoException
     */
    public void deleteUser(UserBean userBean) throws DaoException {

        User user = new User();

        user.setUserID(userBean.getUserID());
        user.setName(userBean.getName());
        user.setSurname(userBean.getSurname());
        user.setUsername(userBean.getUsername());
        user.setUserRole(userBean.getUserRole());
        user.setEmail(userBean.getEmail());
        user.setPassword(userBean.getPassword());

        UserDao dao = DaoFactory.getSingletonInstance().getUserDAO();
        dao.deleteUser(user);
    }
}
