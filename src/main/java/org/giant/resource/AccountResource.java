package org.giant.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.giant.repository.AccountRepository;

import java.math.BigDecimal;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    private final AccountRepository repo;
    public AccountResource(AccountRepository repo) { this.repo = repo; }

    @GET
    @Path("/{number}")
    @PermitAll
    public Response get(@PathParam("number") String number) {
        var acc = repo.findByNumber(number);
        return acc == null ? Response.status(404).build() : Response.ok(acc).build();
    }

    @GET
    @Path("/by-customer/{id}")
    @PermitAll
    public Response listByCustomer(@PathParam("id") Long id) {
        return Response.ok(repo.listByCustomer(id)).build();
    }

    @GET
    @Path("/{number}/validate/{amount}")
    @PermitAll
    public Response validate(@PathParam("number") String number, @PathParam("amount") BigDecimal amount) {
        var acc = repo.findByNumber(number);
        boolean ok = acc != null && "ACTIVE".equals(acc.status) && acc.balance.compareTo(amount) >= 0;
        return Response.ok(ok).build();
    }


}
