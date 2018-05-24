package it.uniroma2.dicii.bd.model;

import it.uniroma2.dicii.bd.enumeration.UserRole;

/**
 * Class for entity User
 *
 * @author Andrea Cerra
 */
public class User {

    private Integer userID;
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private UserRole userRole;

    /**
     *
     * @return userID
     */
    public Integer getUserID() {
        return userID;
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
     * @return email
     */
    public String getEmail() {
        return email;
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
     * @param username set
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     *
     * @param password set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param userID set
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     *
     * @return string formatted "name surname"
     */
    @Override
    public String toString() {
        return name + " " + surname;
    }


}
