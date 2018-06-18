package cs545.airline.controllers;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cs545.airline.model.Airline;
import cs545.airline.service.AirlineService;

@Named("airlineController")
@RequestScoped
public class AirlineController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String airlineName;
	private Airline airline;
	
	@Inject
	private AirlineService airlineService;
	
	public Airline getAirline() {
		return airline;
	}


	public void setAirline(Airline airline) {
		this.airline = airline;
	}


	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	public void addAirline() {
		
		this.airline = new Airline();
		airline.setName(this.airlineName);
		this.airlineService.create(airline);
	}
	
	public List<Airline> getAllAirlines(){
		
		return airlineService.findAll();
	}
}
