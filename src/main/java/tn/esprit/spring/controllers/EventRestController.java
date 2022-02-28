package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.service.*;
import io.swagger.annotations.Api;




@Api(tags = "Event Management")

@RestController
@RequestMapping("/Event")
public class EventRestController {


	@Autowired
	EventService eventService;

	@PostMapping("/addEvent")
	public ResponseEntity<Event> addEvent(@RequestBody Event event) {
		eventService.addEvent(event);
		
		return ResponseEntity.ok().body(event);
	}

	@GetMapping("/getAllEvent")
	public ResponseEntity<Page<Event>> getAllEvent(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Event> evn = eventService.getAllEvent(pageable);
		return ResponseEntity.ok().body(evn);
	}

	@GetMapping("/getEventById/{id}")
	public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
	}

	@DeleteMapping("/deleteEvent/{id}")
	public ResponseEntity<Void> deleteEvent(@PathVariable("id") Long id) {
		eventService.deleteEvent(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/sort")
	public ResponseEntity<Page<Event>> findEventByCriteria(
			@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
			@RequestParam( required = true) String param,
			@RequestParam( required = true) Sort.Direction direction) {
		Pageable pageable = !StringUtils.hasLength(param) ? PageRequest.of(page, size)
				: PageRequest.of(page, size, direction, param);
		Page<Event> evn = eventService.getAllEvent(pageable);
		return ResponseEntity.ok().body(evn);
	}

	@GetMapping("/search/{title}")
	public ResponseEntity<Page<Event>> findByTitle(@PathVariable("title") String title, @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
			@RequestParam(value = "size", defaultValue = "10", required = false) Integer size){
		Pageable pageable = PageRequest.of(page, size);
		Page<Event> evn = eventService.findByTitle(title,pageable);	
		return ResponseEntity.ok().body(evn);
	}
}
