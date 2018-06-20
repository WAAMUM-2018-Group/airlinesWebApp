/**
 * 
 */
package edu.mum.cs545.ws;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.FlightService;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * @author romiezaw
 *
 */
@Path("flights")
@Api(value="flights" , description= "REST API to interact with Flight services")
@Produces({"application/xml","application/json"})
@RequestScoped
public class FlightRestServices {

	@Inject
	private FlightService flightService;
	
	@GET
	@ApiOperation(value="Get All Flights" , notes= "Get all flights ",
				response=Flight.class, responseContainer="List")
	public List<Flight> getAllFlights(){
		return flightService.findAll();
	}
	
	@GET
	@Path("/flight/{flightNo}")
	@ApiOperation(value="Get flight", notes = "Get airport by airport code",
				response = Flight.class, responseContainer="List")
	public List<Flight> getFlight(@PathParam("flightNo") String flightNo) {
		return flightService.findByNumber(flightNo);
	}
	
	
	@PUT
	@ApiOperation(value="Update flight", notes="Update an flight", response = Flight.class)
	public Response updateFlight(@Parameter(description="Flight object to update to the list", required= true) Flight flight) {
		flightService.update(flight);
		return Response.ok().entity(flight).build();
	}
	
	
}
