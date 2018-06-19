package cs545.airline.beans;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cs545.airline.service.AirlineService;
import cs545.airline.service.AirplaneService;
import cs545.airline.service.AirportService;
import cs545.airline.service.FlightService;

@Named
@SessionScoped
public class FlightBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private FlightService flishtService;
	@Inject
	private AirlineService airlineService;
	@Inject
	private AirplaneService airplaneService;
	@Inject
	private AirportService airportService;
	
	private String flightnr;
	private String airportcode;
	private String serialnr;
	
	
	
}
