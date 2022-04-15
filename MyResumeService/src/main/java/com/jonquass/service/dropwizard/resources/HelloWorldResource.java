package com.jonquass.service.dropwizard.resources;

import com.jonquass.core.dropwizard.response.GreetingResponse;
import com.jonquass.data.creator.GreetingCreator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final GreetingCreator greetingCreator;
    private final AtomicLong counter;

    @Inject
    public HelloWorldResource(GreetingCreator greetingCreator) {
        this.greetingCreator = greetingCreator;
        this.counter = new AtomicLong();
    }

    @GET
    public GreetingResponse sayHello(@QueryParam("name") Optional<String> name) {
        return name.map(s -> greetingCreator.createGreeting(counter.incrementAndGet(), s))
                .orElseGet(() -> greetingCreator.createGreeting(counter.incrementAndGet()));
    }
}
