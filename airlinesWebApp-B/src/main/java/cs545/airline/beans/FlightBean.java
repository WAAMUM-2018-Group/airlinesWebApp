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
import java.text.SimpleDateFormat;
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
	private String typeOfSearch ="all";

	List<String> typeOfSearchs = new ArrayList<String>();

	private List<Flight> flights;
	
	@PostConstruct
    public void init() {
		typeOfSearchs.add("All");
		typeOfSearchs.add("Departure Time");
		typeOfSearchs.add("Airline");
		typeOfSearchs.add("Origin");
		typeOfSearchs.add("Destination");
		filterOnRequest();
    }
	
	public List<String> getTypeOfSearchs() {
		
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
	
	public boolean checkType(String value) {
		return typeOfSearch.equals(value);
	}
	
	public List<Flight> getListOfAllFlights(){
		 flights=flightService.findAll();
		 return flights;
		
	}
	
	public List<Flight> getListOfAllFlightsByDeparture(Date departureDate){
		flights=flightService.findByDeparture(departureDate);
		return flights;
	}
	

	public List<Flight> getListOfAllFlightsByAirLine(String airlineName){
		 flights=flightService.findByAirline(airlineService.findByName(airlineName));
		 return flights;
	}
	
	public List<Flight> getListOfAllFlightsByOrigin(String airportcode){
		Airport a1=new Airport();
		a1=airportService.findByCode(airportcode);
		 flights=flightService.findByOrigin(a1);
		 return flights;
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
	
    public void filterOnRequest() {
        switch (typeOfSearch) {
            case "Departure Time":
            	getListOfAllFlightsByDeparture(departureDate);
                break;
            case "Airline":
            	getListOfAllFlightsByAirLine(airlineName);
                break;
            case "Origin":
            	getListOfAllFlightsByOrigin(airportcode);
                break;
            case "Destination":
            	getListOfAllFlightsByDestination(airportcode);
                break;
            default:
            	getListOfAllFlights();
                break;
        }
    }
	
}
