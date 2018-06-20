package cs545.airline.beans;

import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;

@Named
@FlowScoped("airlineflight")
public class AirlineFlightFlowBean implements Serializable{

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
	private String departureDate;
	private String arrivalDate;
	private String departureTime;
	private String arrivalTime;
	private String airportOrigin;
	private String airportDestination;
	
	@Inject
	private AirlineService airlineService;
	@Inject
	private AirplaneService airplaneService;
	@Inject
	private AirportService airportService;
	
	public AirlineFlightFlowBean() {
		//this.departureDate = new Date();
		//this.arrivalDate = new Date();
		//this.departureTime = new Date();
		//this.arrivalTime = new Date();
	}
	
	public void loadExistingAirlineName() {
		String temp = (String)FacesContext.getCurrentInstance().getExternalContext().getFlash().get("airlineName");
		if(temp != null) {
			this.airlineName = temp;
		}
	}
	
	/* Create new flights to an airline
	 * */
	public String createFlight() {
		this.airline = getAirlineByName(this.airlineName);
		this.flight = new Flight(this.flightNumber, 
									this.departureDate, 
										this.departureTime, 
											this.arrivalDate, 
												this.arrivalTime);
		this.flight.setAirplane(this.airplaneService.findBySrlnr(airplaneSerialnr));
		this.flight.setOrigin(this.airportService.findByCode(airportOrigin));
		this.flight.setDestination(this.airportService.findByCode(airportDestination));
		this.airline.addFlight(this.flight);
		this.airlineService.update(this.airline);
		return "/airline_flight_details";
	}
	
	public Airline getAirlineByName(String name) {
		return this.airlineService.findByName(name);
	}
	
	public String getReturnValue() {
		return "";
	}
	
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getAirplaneSerialnr() {
		return airplaneSerialnr;
	}
	public void setAirplaneSerialnr(String airplaneSerialnr) {
		this.airplaneSerialnr = airplaneSerialnr;
	}
	public Airplane getAirplane() {
		return airplane;
	}
	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
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

}
