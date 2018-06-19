package cs545.airline.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cs545.airline.model.Airline;
import cs545.airline.service.AirlineService;

@Named("airlineController")
@SessionScoped
public class AirlineController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String airlineName;
	private Airline airline;

	private List<AirlineWrapper> airlineList;

	@Inject
	private AirlineService airlineService;

	public AirlineController() {
		this.airlineList = new ArrayList<AirlineWrapper>();
	}

	/*
	 * This populated the all the air lines and initialize wrapper object
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

	public void addAirline() {

		this.airline = new Airline();
		airline.setName(this.airlineName);
		this.airlineService.create(airline);
	}

	public String editAction(AirlineWrapper airlineWrapper) {

		airlineWrapper.setEditable(true);
		return null;
	}

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
}
