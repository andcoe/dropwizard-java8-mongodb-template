package com.andcoe.health;

import com.codahale.metrics.health.HealthCheck;

public class RestApiHealthCheck extends HealthCheck {

    public RestApiHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}