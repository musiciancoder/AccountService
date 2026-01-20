package org.giant.resource;

//import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.giant.dto.CreditRequest;
import org.giant.dto.ReleaseRequest;
import org.giant.dto.ReserveRequest;
import org.giant.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BalanceResource {

    private final AccountRepository repo;

    private final Logger logger = LoggerFactory.getLogger(BalanceResource.class);
    public BalanceResource(AccountRepository repo) { this.repo = repo; }

    /**
     * POST /accounts/reserve
     * Debits funds from source account (synchronous).
     */
    @POST
    @Path("/reserve")
  //  @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @Transactional
    public Response reserve(ReserveRequest req, @HeaderParam("X-Correlation-Id") String correlationId) {
        logger.info("Reserving {} from account {}", req.amount(), req.number());
        var acc = repo.findByNumber(req.number());
        if (acc == null || !"ACTIVE".equals(acc.status)) throw new WebApplicationException("ACCOUNT_INVALID", 409);
        if (acc.balance.compareTo(req.amount()) < 0) throw new WebApplicationException("INSUFFICIENT_FUNDS", 422);
        acc.balance = acc.balance.subtract(req.amount());
        return Response.ok().build();
    }

    /**
     * POST /accounts/credit
     * Credits funds to target account (synchronous).
     */
    @POST
    @Path("/credit")
  //  @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @Transactional
    public Response credit(CreditRequest req, @HeaderParam("X-Correlation-Id") String correlationId) {
        logger.info("Crediting {} to account {}", req.amount(), req.number());
        var acc = repo.findByNumber(req.number());
        if (acc == null || !"ACTIVE".equals(acc.status)) throw new WebApplicationException("ACCOUNT_INVALID", 409);
        acc.balance = acc.balance.add(req.amount());
        return Response.ok().build();
    }

    /**
     * POST /accounts/release
     * Compensation: returns funds to source account (undo reserve).
     */
    @POST
    @Path("/release")
 //   @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @Transactional
    public Response release(ReleaseRequest req, @HeaderParam("X-Correlation-Id") String correlationId) {
        logger.info("Releasing {} to account {}", req.amount(), req.number());
        var acc = repo.findByNumber(req.number());
        if (acc == null || !"ACTIVE".equals(acc.status)) throw new WebApplicationException("ACCOUNT_INVALID", 409);
        acc.balance = acc.balance.add(req.amount());
        return Response.ok().build();
    }
}
