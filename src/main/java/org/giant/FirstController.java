package org.giant;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.giant.resource.BalanceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class FirstController {

    final Logger logger = LoggerFactory.getLogger(FirstController.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
  logger.info("Hi!");
        return "Hello from Quarkus RESTttr";
    }
}
