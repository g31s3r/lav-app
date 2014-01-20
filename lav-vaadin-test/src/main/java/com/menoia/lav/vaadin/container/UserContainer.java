package com.menoia.lav.vaadin.container;

import com.menoia.lav.vaadin.entity.User;

import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class UserContainer extends DefaultHbnContainer<User> {

    private static final long serialVersionUID = 1L;

    public UserContainer() {

        super(User.class);
    }

    public User getByLoginAndPassword(String login, String password) {

        // there are plenty of usefull methods for querying the database using
        // HQL. here we use "simpleQuery" wich returns a single instance of User
        return (User) singleQuery("from User where login = ? and password = ?", new Object[] { login, password });
    }

}
