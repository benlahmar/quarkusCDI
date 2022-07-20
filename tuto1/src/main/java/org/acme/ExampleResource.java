package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.models.IUser;

import io.micrometer.core.annotation.Counted;

@Path("/hello")
public class ExampleResource {

	@Inject
	GreetingService service;
	
	@Inject
    IUser ius;
	
	
	
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 @Path("/greeting/{name}")
	 public String hello(String name)
	 {
	   	
	   	return service.greeting(name)+"********"+ius.check();
	 }
	 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    public String hello() {
        return "Hello Habib from RESTEasy Reactive";
    }
    
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Counted
    @Path("/hi/{name}")
    public String hellouser() {
    	
        return "Hello Habib from RESTEasy Reactive";
    }
   
}