/**
 * 
 */
package edu.mum.cs545.ws;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cs545.airline.model.Airline;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * @author romiezaw
 *
 */

@Path("airlines")
@Api(value = "airlines", description = "REST API to interact with Airline service")
@Produces({"application/xml","application/json"})
@RequestScoped
public class AirlineRestServices {
	
	@Inject
	private AirlineService airlineService;
	
	@GET
	@ApiOperation(value="Get All Airlines", notes = "Get all airlines", 
				response=Airline.class, responseContainer="List")
	public List<Airline> getAllAirlines() {
		int a;
		return airlineService.findAll();
		
	}
	
	@GET
	@Path("/{name}")
	@ApiOperation(value="Get airline by name", notes="Get airline by name", 
				response=Airline.class)
	public Airline getAirlineByName(@PathParam("name") String name) {
		return airlineService.findByName(name);
	}
//	
//	@GET
//	@Path("/")
//	@ApiOperation(value="Get airline by flight", notes="Get all airlines by flight",
//				response=Airline.class, responseContainer="List")
//	public List<Airline> getAirlinesByFlight(Flight flight){
//		return airlineService.findByFlight(flight);
//	}
//	
	@POST
	@ApiOperation(value="Add airline" , notes="Add a new airline")
	public Response addAirline(@Parameter(description="Airline object to add to the list", required = true)  Airline airline) {
		airlineService.create(airline);
		return Response.ok().entity(airline).build();
	}
	
}
