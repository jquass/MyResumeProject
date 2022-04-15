package com.jonquass.service.dropwizard.resources;

import com.jonquass.core.dropwizard.response.CatResponse;
import com.jonquass.data.creator.CatCreator;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/the-cat-api")
@Produces(MediaType.APPLICATION_JSON)
public class TheCatApiResource {

    private final CatCreator catCreator;
    private final AtomicLong counter;

    @Inject
    public TheCatApiResource(CatCreator catCreator) {
        this.catCreator = catCreator;
        this.counter = new AtomicLong();
    }

    @GET
    public CatResponse getCat() {
        return catCreator.createCat(counter.incrementAndGet());
    }
}
