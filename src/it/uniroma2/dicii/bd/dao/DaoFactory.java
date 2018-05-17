package it.uniroma2.dicii.bd.dao;

import it.uniroma2.dicii.bd.exception.DaoException;
import it.uniroma2.dicii.bd.interfaces.FilamentDao;
import it.uniroma2.dicii.bd.interfaces.UserDao;
import it.uniroma2.dicii.bd.utils.Config;

public class DaoFactory {

    private static DaoFactory instance = null;

    protected DaoFactory(){

    }

    /**
     * Return an istance of concrete user dao using Reflection
     *
     * @return UserDao object
     * @throws DaoException error with database connection or wrong type in config
     */
    public FilamentDao getFilamentDAO() throws DaoException {

        String filamentDaoClass = Config.getSingletonInstance().getProperty("filamentDaoClass");

        try {

            Class<?> c = Class.forName(filamentDaoClass);
            return (FilamentDao) c.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

            e.printStackTrace();
            throw new DaoException("Invalid type : " + filamentDaoClass, e.getCause());
        }

    }

    /**
     * Return an istance of concrete user dao using Reflection
     *
     * @return UserDao object
     * @throws DaoException error with database connection or wrong type in config
     */
    public UserDao getUserDAO() throws DaoException{

        String userDaoClass = Config.getSingletonInstance().getProperty("userDaoClass");

        try {

            Class<?> c = Class.forName(userDaoClass);
            return (UserDao) c.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

            e.printStackTrace();
            throw new DaoException("Invalid type : " + userDaoClass, e.getCause());
        }

    }

    /**
     * Return or inizialize the factory singleton istance
     *
     * @return reference to singleton istance
     */
    public synchronized static final DaoFactory getSingletonInstance() {

        if (DaoFactory.instance == null)
            DaoFactory.instance = new DaoFactory();
        return instance;
    }

}
