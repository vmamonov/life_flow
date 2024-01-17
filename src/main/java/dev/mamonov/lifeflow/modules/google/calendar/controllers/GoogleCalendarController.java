package dev.mamonov.lifeflow.modules.google.calendar.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import dev.mamonov.lifeflow.modules.google.calendar.exceptions.GoogleCalendarOAuthRedirectException;
import dev.mamonov.lifeflow.modules.google.calendar.services.GoogleCalendarService;

@Controller
@RequestMapping("/api/calendar")
public class GoogleCalendarController {
	@Autowired
	GoogleCalendarService googleCalendarService;

	@GetMapping("/events")
	@ResponseBody
	public String getEventsToday() throws IOException, GeneralSecurityException, GoogleCalendarOAuthRedirectException {
		googleCalendarService.getTodaysEvents();
		return "today's events";
	}
	
	@GetMapping("/events/{begin}/{end}")
	@ResponseBody
	public String getEvents(@PathVariable("begin") int begin, @PathVariable("end") int end) throws IOException, GeneralSecurityException, GoogleCalendarOAuthRedirectException {
		googleCalendarService.getTodaysEvents();
		return "hello world";
	}
	
	
	@GetMapping("/events/today")
	@ResponseBody
	public String getTodaysEvents() throws IOException, GeneralSecurityException, GoogleCalendarOAuthRedirectException {
		List<Event> items = googleCalendarService.getTodaysEvents();
	    if (items.isEmpty()) {
	      System.out.println("No upcoming events found.");
	    } else {
	      System.out.println("Upcoming events");
	      for (Event event : items) {
	        DateTime start = event.getStart().getDateTime();
	        if (start == null) {
	          start = event.getStart().getDate();
	        }
	        System.out.printf("%s (%s)\n", event.getSummary(), start);
	      }
	    }
		return "todays events";
	}
	
	
	@PatchMapping("/events/create")
	@ResponseBody
	public String createEvent() {
		return "new event was created";
	}
	
	
	@ExceptionHandler(GoogleCalendarOAuthRedirectException.class)
	public String doAuth(GoogleCalendarOAuthRedirectException ex) {
		System.out.println("Hello from authRedirect --> " + ex.getRedirectUrl());
		return "redirect:" + ex.getRedirectUrl(); 
	}
	
	
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public String authRedirectdd(IOException ex) throws JsonMappingException, JsonProcessingException {
		System.out.println("-------IOException--------");
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		System.out.println("---------------");
		return "IOException"; 
	}

	
	@ExceptionHandler(GeneralSecurityException.class)
	@ResponseBody
	public String authRedirectdd(GeneralSecurityException ex) {
		System.out.println("------GeneralSecurityException---------");
		System.out.println(ex.getMessage());
		System.out.println("---------------");
		return "GeneralSecurityException"; 
	}
}
