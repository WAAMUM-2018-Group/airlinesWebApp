package cs545.airline.beans;

import cs545.airline.model.Airline;
import cs545.airline.model.Airplane;
import cs545.airline.model.Airport;
import cs545.airline.model.Flight;
import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;
import javafx.util.Pair;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("flightBean")
@SessionScoped
public class FlightBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private FlightService flightService;
	@Inject
	private AirlineService airlineService;
	@Inject
	private AirplaneService airplaneService;
	@Inject
	private AirportService airportService;
	
	private String flightnr;
	private String airlineName;
	private String airportcode;
	private Date departureDate;
	private Date arrivalDate;
	private String typeOfSearch = "all";
	private static List<Pair<String, String>> typeOfSearchs;
	
	private List<Flight> flights;
	@PostConstruct
    public void init() {
		System.out.println("==========================================************=================1111");
		filter();
    }

	public List<Pair<String, String>> getTypeOfSearchs() {
        typeOfSearchs = new ArrayList<>();
        typeOfSearchs.add(new Pair<>("All", "all"));
        typeOfSearchs.add(new Pair<>("Departure Time", "departureDateTime"));
        typeOfSearchs.add(new Pair<>("Airline", "airline"));
        typeOfSearchs.add(new Pair<>("Origin", "origin"));
        typeOfSearchs.add(new Pair<>("Destination", "destination"));
        System.out.println("==========================================************=================222");
        return typeOfSearchs;
    }
	
	public String getTypeOfSearch() {
		return typeOfSearch;
	}


	public String getFlightnr() {
		return flightnr;
	}

	public void setFlightnr(String flightnr) {
		this.flightnr = flightnr;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirportcode() {
		return airportcode;
	}

	public void setAirportcode(String airportcode) {
		this.airportcode = airportcode;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	public void setTypeOfSearch(String typeOfSearch) {
		this.typeOfSearch = typeOfSearch;
	}
	
	public List<Flight> getListOfAllFlights(){
		 flights=flightService.findAll();
		 return flights;
		
	}
	
	public List<Flight> getListOfAllFlightsByDeparture(Date departureDate){
		flights=flightService.findByDeparture(departureDate);
		return flights;
	}
	
	public List<Flight> getListOfAllFlightsByArrivalDate(Date arrivalDate){
		return flights=flightService.findByDeparture(departureDate);
	}

	public List<Flight> getListOfAllFlightsByAirLine(String airlineName){
		return flights=flightService.findByAirline(airlineService.findByName(airlineName));
	}
	
	public List<Flight> getListOfAllFlightsByOrigin(String airportcode){
		 return flights=flightService.findByOrigin(airportService.findByCode(airportcode));
	}
	
	
	public List<Flight> getListOfAllFlightsByDestination(String airportcode){
		return flights=flightService.findByDestination(airportService.findByCode(airportcode));
	}

    public List<Airline> getAirlines() {
        return airlineService.findAll();
    }

    public List<Airport> getAirports() {
        return airportService.findAll();
    }

    public List<Airplane> getAirplanes() {
        return airplaneService.findAll();
    }
	
    public void filter() {
        switch (typeOfSearch) {
            case "departureDateTime":
            	getListOfAllFlightsByDeparture(departureDate);
                break;
            case "arrivalDateTime":
            	getListOfAllFlightsByArrivalDate(arrivalDate);
                break;
            case "airline":
            	getListOfAllFlightsByAirLine(airlineName);
                break;
            case "origin":
            	getListOfAllFlightsByOrigin(airportcode);
                break;
            case "destination":
            	getListOfAllFlightsByDestination(airportcode);
                break;
            default:
            	getListOfAllFlights();
                break;
        }
    }
	
}
