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

import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirportService;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * @author romiezaw
 *
 */
@Path("airports")
@Api(value="airports" , description= "REST API to interact with Airport services")
@Produces({"application/xml","application/json"})
@RequestScoped
public class AirportRestServices {

	@Inject
	private AirportService airportService;
	
	@GET
	@ApiOperation(value="Get All Aiports" , notes= "Get all airports",
				response=Airport.class, responseContainer="List")
	public List<Airport> getAllAirports(){
		return airportService.findAll();
	}
	
	@GET
	@Path("/code/{code}")
	@ApiOperation(value="Get airport by airport code", notes = "Get airport by airport code",
				response = Airport.class)
	public Airport getAirportByCode(@PathParam("code") String code) {
		return airportService.findByCode(code);
	}
	
	@GET
	@Path("/airport/{name}")
	@ApiOperation(value="Get airport by airport name", notes = "Get airport by airport name",
				response = Airport.class)
	public Airport getAirportByName(@PathParam("name") String name) {
		return airportService.findByCode(name);
	}
	
	@GET
	@Path("/airport/{city}")
	@ApiOperation(value="Get airport by city", notes="Get all airport by city",
				response=Airport.class, responseContainer = "List")
	public List<Airport> getAirportByCity(@PathParam("city") String city){
		return airportService.findByCity(city);
	}
	
	@GET
	@Path("/airport/{country}")
	@ApiOperation(value="Get airport by country", notes="Get all airport by country",
				response=Airport.class, responseContainer = "List")
	public List<Airport> getAirportByCountry(@PathParam("country") String country){
		return airportService.findByCountry(country);
	}
	
	
	@POST
	@ApiOperation(value="Add airport", notes="Add an airport", response= Airport.class)
	public Response addAirport(@Parameter(description="Airport object to add to the list", required = true) Airport airport) {
		airportService.create(airport);
		return Response.ok().entity(airport).build();
	}
	
	@PUT
	@ApiOperation(value="Update airport", notes="Update an airport", response = Airport.class)
	public Response updateAirplane(@Parameter(description="Airport object to update to the list", required= true) Airport airport) {
		airportService.update(airport);
		return Response.ok().entity(airport).build();
	}
	
	@DELETE
	@Path("airport/{airportcode}")
	@ApiOperation(value="Delete airport", notes="Delete an airport")
	public void deleteAirport(@PathParam("airportcode") String airportcode) {
		System.out.println("Delete Airport...");
		Airport airportToDelete = airportService.findByCode(airportcode);
		airportService.delete(airportToDelete);
	}
	
}
