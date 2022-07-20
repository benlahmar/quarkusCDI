package org.acme.api;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.acme.models.Categorie;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;


@Path("categories")
public class CategorieApi {

	private static final Logger LOGGER = Logger.getLogger(CategorieApi.class);
	private AtomicLong counter = new AtomicLong(0);
	
	@Inject
	cBreakerService cbreakerService;
	
	private Set<Categorie> categories = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

	public CategorieApi() {
		
		categories.add(new Categorie(1L, "libelle1"));
		categories.add(new Categorie(2L, "libelle2"));
	}
	@GET
	@Path("/{id}")
	@Timeout(250)
	public Categorie find(long id)
	{
		return categories.stream()
				.filter(x->x.getId()==id)
		.findFirst().get();
	}
	@GET
	@Retry(maxRetries = 4)
	public Set<Categorie> all()
	{
		return categories;
	}
	
	@POST
	public Set<Categorie>  add(Categorie c)
	{
		categories.add(c);
		return categories;
	}
	
	@DELETE
	public Set<Categorie>  remove(Categorie c)
	{
		categories.remove(c);
		return categories;
	}
	
	
	@GET
	@Path("/{libelle}")
	public Uni<Categorie> getOne(String libelle) {
		
		Optional<Categorie> oc = categories.stream().filter(x-> 
				x.getLibelle().equals(libelle))
				.findFirst();
		return  Uni.createFrom().item(oc.get());
	    
	}

	@GET
	@Path("/asyn")
	public Multi<Categorie> getAll() {
		return Multi.createFrom().iterable(categories);
	   
	}
	
	@GET
	@Path("retry")
	@Retry(maxRetries = 4)
	public Set<Categorie> all2()
	{
		final Long invocationNumber = counter.getAndIncrement();

        maybeFail(String.format("Categories......() invocation #%d failed", invocationNumber));

        LOGGER.infof("Categories......() invocation #%d returning successfully", invocationNumber);
        return categories;
	}
	
	
	
	
	private void maybeFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }
	
	
	@GET
    @Path("/timeout")
    @Timeout(250)
    public List<String> getlibelles() {
        long started = System.currentTimeMillis();
        final long invocationNumber = counter.getAndIncrement();

        try {
            randomDelay();
            LOGGER.infof("Categories......() invocation #%d returning successfully", invocationNumber);
            return categories.stream().map(x->x.getLibelle()).collect(Collectors.toList());
        } catch (InterruptedException e) {
            LOGGER.errorf("Categories......() invocation #%d timed out after %d ms",
                    invocationNumber, System.currentTimeMillis() - started);
            return null;
        }
    }

    private void randomDelay() throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
    }
	
    
    
    @GET
    @Path("/fallback/{id}")
    @Fallback(fallbackMethod = "fallbackCategories")
    @Timeout(250)
    public Set<Categorie> categories(int id) {
    	
        return categories;
    }

    public Set<Categorie> fallbackCategories(int id) {
        LOGGER.info("Falling back to fallbackCategories#fallbackCategories()");
        // safe bet, return something that everybody likes
        return Collections.singleton(categories.stream().findFirst().get());
    }
    
    
   
    @Path("/{id}/availability")
    @GET
    public Response availability(int id) {
        final Long invocationNumber = counter.getAndIncrement();

        Categorie coffee = categories.stream().filter(x->x.getId()==id).findFirst().get();
        // check that coffee with given id exists, return 404 if not
        if (coffee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            Integer availability = cbreakerService.getAvailability(id);
            LOGGER.infof("Resource#availability() invocation #%d returning successfully", invocationNumber);
            return Response.ok(availability).build();
        } catch (RuntimeException e) {
            String message = e.getClass().getSimpleName() + ": " + e.getMessage();
            LOGGER.errorf("CoffeeResource#availability() invocation #%d failed: %s", invocationNumber, message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }
    
    
}
