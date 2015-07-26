package com.andcoe.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestApiConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    public String version;

    @Valid
    @NotNull
    public MongoConfig mongoConfig = new MongoConfig();
}
