package com.revature.caliber.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.location.domain.Location;
import com.revature.caliber.location.service.LocationService;

@RestController
@CrossOrigin(origins="*")
public class LocationController {

	private static Logger log = Logger.getLogger(LocationController.class);
	
	@Autowired
	LocationService ls;
	
	/**
	 * Waits for http request and calls the LocationService method createLocation() to save the location
	 * 		to the database 
	 * 
	 * @param l
	 * 
	 * @return http response: CREATED
	 */
	@PostMapping(value="/vp/location/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public ResponseEntity<Location> createLocation(@Valid @RequestBody Location l) {
		 log.debug("Saving new location:" + l );
		 ls.createLocation(l);
		 return new ResponseEntity<>(l, HttpStatus.CREATED);
	}

	/**
	 * Returns all Locations for the database
	 * 
	 * @return lList - a List object with all the Location entities from the database
	 */
	@GetMapping(value="/all/location/all", produces=MediaType.APPLICATION_JSON_VALUE)
	// @Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public ResponseEntity<List<Location>> getAllLocations() {
		log.debug("Getting all locations from the database");
		// Location l1 = new Location(1,"Revature", "Tampa", "11111", "1223 Sesame St", "Florida", true);
		// List<Location> list = Collections.unmodifiableList(Arrays.asList(l1));
		 List<Location> lList = ls.getAllLocations();
		return new ResponseEntity<List<Location>>(lList, HttpStatus.OK);
	}
	
	/**
	 * Updates a Location entry in the database
	 * 
	 * @param - l - the Location entry to be updated
	 * 
	 * @return - returns an http status code: NO_CONTENT
	 */
	@PutMapping(value="/vp/location/update", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public ResponseEntity<Void> updateLocation(@Valid @RequestBody Location l) {
		log.debug("Updating a location: " + l);
		ls.updateLocation(l);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Deletes a Location entry from the database, soft delete, sets Location active field to false
	 * 
	 * @param - l - the Location entry to be deleted
	 * 
	 * @return returns an http status code: NO_CONTENT
	 */
	@DeleteMapping(value="vp/location/delete", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public ResponseEntity<Void> deleteLocation(@Valid @RequestBody Location l) {
		log.debug("Deactivating a location:  " + l);
		ls.updateLocation(l);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Sets Location active field to true
	 * 
	 * @param - l - the Location entry to be reactivated
	 * 
	 * @return returns an http status code: NO_CONTENT
	 */
	@PutMapping(value="vp/location/reactivate", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public ResponseEntity<Void> reactivateLocation(@Valid @RequestBody Location l) {
		log.debug("Reactivating a location:  " + l);
		ls.updateLocation(l);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
