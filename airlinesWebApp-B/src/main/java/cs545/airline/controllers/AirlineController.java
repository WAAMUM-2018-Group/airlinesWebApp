package cs545.airline.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
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
	private String flightNumber;
	private String airplaneSerialnr;
	private Airplane airplane;
	private Date departureDate;
	private Date arrivalDate;
	private Date departureTime;
	private Date arrivalTime;
	private String airportOrigin;
	private String airportDestination;

	private List<AirlineWrapper> airlineList;

	@Inject
	private AirlineService airlineService;
	@Inject
	private AirplaneService airplaneService;
	@Inject
	private AirportService airportService;
	
	public AirlineController() {
		this.airlineList = new ArrayList<AirlineWrapper>();
		//this.flight = new Flight();
		//this.airplane = new Airplane();
		this.departureDate = new Date();
		this.arrivalDate = new Date();
		this.departureTime = new Date();
		this.arrivalTime = new Date();
	}

	public String navigateToAirlineFlight(String name){	
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
		for(AirlineWrapper alWrapper: tempAirlineList) {
			if(this.airlineList.contains(alWrapper)) {
				int index = this.airlineList.indexOf(alWrapper);
				airlineWrapper = this.airlineList.get(index);
				alWrapper.setEditable(airlineWrapper.isEditable());
			}
		}
		this.airlineList = tempAirlineList;
		return airlineList;
	}
	
	public List<Airport> getAirportList(){
		return this.airportService.findAll();
	}
	public String getAirportOrigin() {
		return airportOrigin;
	}

	public void setAirportOrigin(String airportOrigin) {
		this.airportOrigin = airportOrigin;
	}

	public String getAirportDestination() {
		return airportDestination;
	}

	public void setAirportDestination(String airportDestination) {
		this.airportDestination = airportDestination;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getAirplaneSerialnr() {
		return airplaneSerialnr;
	}

	public void setAirplaneSerialnr(String airplaneSerialnr) {
		this.airplaneSerialnr = airplaneSerialnr;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public List<Airplane> getAirplanesList(){
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

	/* Create new flights to an airline
	 * */
	public void createFlight() {
		this.airline = getAirlineByName(this.airlineName);
		this.flight = new Flight(this.flightNumber, 
									this.departureDate.toString(), 
										this.departureTime.toString(), 
											this.arrivalDate.toString(), 
												this.arrivalTime.toString());
		this.flight.setAirplane(this.airplaneService.findBySrlnr(airplaneSerialnr));
		this.flight.setOrigin(this.airportService.findByCode(airportOrigin));
		this.flight.setDestination(this.airportService.findByCode(airportDestination));
		this.airline.addFlight(this.flight);
		this.airlineService.update(this.airline);
	}
	/* Create a new airline 
	 * */
	public void addAirline() {

		this.airline = new Airline();
		airline.setName(this.airlineName);
		this.airlineService.create(airline);
	}

	public String editAction(AirlineWrapper airlineWrapper) {

		airlineWrapper.setEditable(true);
		return null;
	}

	/*This method save changes to all airlines names.
	 * */
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
