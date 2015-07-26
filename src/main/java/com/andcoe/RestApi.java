package com.andcoe;

import com.andcoe.config.MongoManaged;
import com.andcoe.config.RestApiConfiguration;
import com.andcoe.exception.RestApiExceptionMapper;
import com.andcoe.health.MongoHealthCheck;
import com.andcoe.health.RestApiHealthCheck;
import com.andcoe.logic.Logic;
import com.andcoe.persistence.Dao;
import com.andcoe.resources.UsersResource;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

public class RestApi extends Application<RestApiConfiguration> {

    public static void main(String[] args) throws Exception {
        new RestApi().run(args);
    }

    @Override
    public void initialize(Bootstrap<RestApiConfiguration> bootstrap) {
        bootstrap.addBundle(new Java8Bundle());
    }

    @Override
    public void run(RestApiConfiguration configuration, Environment environment) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient( configuration.mongoConfig.host , configuration.mongoConfig.port );
        DB db = mongoClient.getDB(configuration.mongoConfig.dbname);
        MongoManaged mongoManaged = new MongoManaged(mongoClient);

        DBCollection users = db.getCollection("users");

        final Dao dao = new Dao(users);
        final Logic logic = new Logic(dao);

        final UsersResource usersResource = new UsersResource(logic);

        final RestApiHealthCheck healthCheck = new RestApiHealthCheck();

        environment.lifecycle().manage(mongoManaged);
        environment.healthChecks().register("healthCheck", healthCheck);
        environment.healthChecks().register("mongoHealthCheck", new MongoHealthCheck(mongoClient));
        environment.jersey().register(usersResource);
        environment.jersey().register(new RestApiExceptionMapper());
    }


}
