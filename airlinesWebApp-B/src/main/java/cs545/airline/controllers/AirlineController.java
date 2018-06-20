package cs545.airline.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;

@Named("airlineController")
@SessionScoped
public class AirlineController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String airlineName;
	private Airline airline;
	private Flight flight;

	private List<AirlineWrapper> airlineList;

	@Inject
	private AirlineService airlineService;
	@Inject
	private AirplaneService airplaneService;
	@Inject
	private AirportService airportService;

	public AirlineController() {
		this.airlineList = new ArrayList<AirlineWrapper>();
		// this.flight = new Flight();
		// this.airplane = new Airplane();
	}

	/*
	 * Add a flash attribute to pass to web flow
	 */
	public String navigateToAirlineflightFlow(String name) {
		if (name != null) {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("airlineName", name);
		}
		return "airlineflight";
	}

	public String navigateToAirlineFlight(String name) {
		this.airline = this.airlineService.findByName(name);

		return "airline_flight_details";
	}

	/*
	 * This populated the all the air lines and initialize wrapper objects
	 */
	public List<AirlineWrapper> getAirlineList() {

		AirlineWrapper airlineWrapper;
		List<Airline> airlines = this.getAllAirlines();
		List<AirlineWrapper> tempAirlineList = new ArrayList<AirlineWrapper>();

		for (Airline al : airlines) {
			airlineWrapper = new AirlineWrapper();
			airlineWrapper.setAirline(al);
			tempAirlineList.add(airlineWrapper);
		}
		for (AirlineWrapper alWrapper : tempAirlineList) {
			if (this.airlineList.contains(alWrapper)) {
				int index = this.airlineList.indexOf(alWrapper);
				airlineWrapper = this.airlineList.get(index);
				alWrapper.setEditable(airlineWrapper.isEditable());
			}
		}
		this.airlineList = tempAirlineList;
		return airlineList;
	}

	public List<Airport> getAirportList() {
		return this.airportService.findAll();
	}

	public List<Airplane> getAirplanesList() {
		return this.airplaneService.findAll();
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public void setAirlineList(List<AirlineWrapper> airlineaList) {
		this.airlineList = airlineaList;
	}

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

	/*
	 * Create a new airline
	 */
	public void addAirline() {

		this.airline = new Airline();
		airline.setName(this.airlineName);
		this.airlineService.create(airline);
	}

	public String editAction(AirlineWrapper airlineWrapper) {

		airlineWrapper.setEditable(true);
		return null;
	}

	/*
	 * This method save changes to all airlines names.
	 */
	public void saveAction() {

		for (AirlineWrapper airLine : this.airlineList) {
			if (airLine.isEditable()) {
				this.airlineService.update(airLine.getAirline());
				airLine.setEditable(false);
			}
		}
	}

	public void deleteAction(AirlineWrapper airlineWrapper) {
		this.airlineService.delete(airlineWrapper.getAirline());
		airlineWrapper.setDeleted(true);
	}

	public List<Airline> getAllAirlines() {

		return airlineService.findAll();
	}

	public Airline getAirlineByName(String name) {
		return this.airlineService.findByName(name);
	}
}
