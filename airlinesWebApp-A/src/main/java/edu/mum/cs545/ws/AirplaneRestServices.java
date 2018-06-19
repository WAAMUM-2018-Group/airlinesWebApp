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
import cs545.airline.model.Flight;
import cs545.airline.service.AirplaneService;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * @author romiezaw
 *
 */

@Path("airplanes")
@Api(value="airplanes" , description= "REST API to interact with Airplane services")
@Produces({"application/xml","application/json"})
@RequestScoped
public class AirplaneRestServices {

	@Inject
	private AirplaneService airplaneService;
	
	@GET
	@ApiOperation(value="Get All Aiplanes" , notes= "Get all airplanes",
				response=Airplane.class, responseContainer="List")
	public List<Airplane> getAllAiplanes(){
		return airplaneService.findAll();
	}
	
	@GET
	@Path("/{serialno}")
	@ApiOperation(value="Get airplane by serial no", notes = "Get airplane by serial no",
				response = Airplane.class)
	public Airplane getAirplaneBySerialNo(@PathParam("serialno") String serialno) {
		return airplaneService.findBySrlnr(serialno);
	}
	
	@GET
	@Path("/flight/")
	@ApiOperation(value="Get airplane by flight", notes="Get all airplanes by flight",
				response=Airplane.class, responseContainer = "List")
	public List<Airplane> getAirplaneByFlight(Flight flight){
		return airplaneService.findByFlight(flight);
	}
	
	@POST
	@ApiOperation(value="Add airplane", notes="Add an airplane", response= Airplane.class)
	public Response addAirplane(@Parameter(description="Airplane object to add to the list", required = true) Airplane airplane) {
		airplaneService.create(airplane);
		return Response.ok().entity(airplane).build();
	}
	
	@PUT
	@ApiOperation(value="Update airplane", notes="Update an airplane", response = Airplane.class)
	public Response updateAirplane(@Parameter(description="Airplane object to update to the list", required= true) Airplane airplane) {
		airplaneService.update(airplane);
		return Response.ok().entity(airplane).build();
	}
	
	@DELETE
	@Path("/{id}")
	@ApiOperation(value="Delete airplane", notes="Delete an airplane")
	public void deleteAirplane(@PathParam("id") Long id) {
		System.out.println("Delete Airplane...");
		Airplane airplaneToDelete = airplaneService.findById(id);
		airplaneService.delete(airplaneToDelete);
	}
}
