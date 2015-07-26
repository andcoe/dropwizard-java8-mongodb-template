package com.andcoe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestApiExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger log = LoggerFactory.getLogger(RestApiExceptionMapper.class);

    @Override
    public Response toResponse(RuntimeException runtime) {

        Response defaultResponse = Response
            .serverError()
            .entity(runtime.getMessage())
            .build();

        if (runtime instanceof WebApplicationException) {
            return handleWebApplicationException(runtime, defaultResponse);
        }

        return defaultResponse;

    }

    private Response handleWebApplicationException(RuntimeException e, Response defaultResponse) {
        WebApplicationException webAppException = (WebApplicationException) e;

        if (webAppException.getResponse().getStatus() == 401) {
            return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(webAppException.getMessage())
                .build();
        }
        if (webAppException.getResponse().getStatus() == 404) {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(webAppException.getMessage())
                .build();
        }

        log.error(e.getMessage(), e);
        return defaultResponse;
    }

}
