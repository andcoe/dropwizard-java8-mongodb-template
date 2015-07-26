package com.andcoe.config;

import org.hibernate.validator.constraints.NotEmpty;

public class MongoConfig {

    @NotEmpty
    public String dbname;

    @NotEmpty
    public String host;

    public int port;
}
