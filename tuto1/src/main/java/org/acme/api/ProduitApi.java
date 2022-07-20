package org.acme.api;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.acme.models.Produit;

@Path("produits")
public class ProduitApi {

	private Set<Produit> produits = Collections.synchronizedSet(new LinkedHashSet<>());
	
		
	public ProduitApi() {
		super();
		produits.add(new Produit(1L, "desg", 100.0, 10));
		produits.add(new Produit(2L, "desg2", 248.0, 47));
	}


	@GET
	public Response all()
	{
		return Response.ok(produits).build();
	}
	
	@GET
	@Path("/{id}")
	public Response find( long id)
	{
		Produit p = produits.stream().filter(x->x.getId()==id).findFirst().get();
		return Response.ok(p).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Produit p)
	{
		produits.add(p);
		return Response.created(
				UriBuilder.fromResource(Produit.class)
				.path(Long.toString(p.getId()))
				.build()
				).build();
	}
}

