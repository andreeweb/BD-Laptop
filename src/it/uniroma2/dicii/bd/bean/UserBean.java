package it.uniroma2.dicii.bd.bean;

import it.uniroma2.dicii.bd.enumeration.UserRole;

/**
 * Bean class for entity User
 *
 * @author Andrea Cerra
 */

public class UserBean {

    private String name;
    private String surname;
    private String username;
    private String password;
    private UserRole userRole;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return the name of the user bean
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the surname of the user bean
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param name the name of the user bean
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param surname the surname of the user bean
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return the username of the user bean
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return the password of the user bean
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param username the username of the user bean
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password the password of the user bean
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return enumeration
     * @see UserRole
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     *
     * @param userRole new userRole for user bean
     * @see UserRole
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
