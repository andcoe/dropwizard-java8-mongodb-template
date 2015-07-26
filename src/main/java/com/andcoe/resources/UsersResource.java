package com.andcoe.resources;

import com.andcoe.logic.Logic;
import com.andcoe.model.User;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    private Logic logic;

    public UsersResource(Logic logic) {
        this.logic = logic;
    }

    @GET
    @Timed
    public List<User> users() {
        return logic.users();
    }

    @GET
    @Path("{id}")
    @Timed
    public Optional<User> user(@PathParam("id") String id) {
        return logic.user(id);
    }

    @POST
    @Timed
    public User user(User user) {
        return logic.createUser(user);
    }
}
