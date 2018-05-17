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
    private String email;
    private UserRole userRole;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param name set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param surname set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @param email set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param username set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password set
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
     * @param userRole set
     * @see UserRole
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
