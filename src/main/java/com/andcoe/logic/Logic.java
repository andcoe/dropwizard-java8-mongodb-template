package com.andcoe.logic;

import com.andcoe.model.User;
import com.andcoe.persistence.Dao;

import java.util.List;
import java.util.Optional;

public class Logic {

    private Dao dao;

    public Logic(Dao dao) {
        this.dao = dao;
    }

    public List<User> users() {
        return dao.users();
    }

    public Optional<User> user(String id) {
        return dao.user(id);
    }

    public User createUser(User user) {
        return dao.createUser(user);
    }
}
