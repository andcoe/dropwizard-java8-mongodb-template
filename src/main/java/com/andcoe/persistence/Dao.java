package com.andcoe.persistence;

import com.andcoe.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

public class Dao {

    private DBCollection users;

    public Dao(DBCollection users) {
        this.users = users;
    }

    public List<User> users() {
        DBCursor cursor = users.find();
        List<User> users = newArrayList();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            Object username = object.get("username");
            User user = new User();
            user.setId(object.get("_id").toString());
            user.setUsername((String) username);
            users.add(user);
        }
        return users;
    }

    public Optional<User> user(String id) {
        ObjectId objectId = new ObjectId(id);
        BasicDBObject object = new BasicDBObject()
            .append("_id", objectId);
        DBCursor cursor = users.find(object);

        if (cursor.hasNext()) {
            DBObject result = cursor.one();
            User user = new User();
            user.setId(object.get("_id").toString());
            user.setUsername((String) result.get("username"));
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public User createUser(User user) {
        BasicDBObject object = new BasicDBObject()
            .append("username", user.getUsername());
        users.insert(object);
        user.setId(object.get("_id").toString());
        return user;
    }

}
