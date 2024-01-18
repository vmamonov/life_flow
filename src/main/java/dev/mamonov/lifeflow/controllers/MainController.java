package dev.mamonov.lifeflow.controllers;

import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;

import dev.mamonov.lifeflow.modules.google.calendar.dto.NewEventDto;
import dev.mamonov.lifeflow.modules.google.calendar.exceptions.GoogleCalendarOAuthRedirectException;
import dev.mamonov.lifeflow.modules.google.calendar.services.GoogleCalendarService;
import jakarta.validation.Valid;

@Controller
public class MainController {
	@Autowired
	GoogleCalendarService googleCalendarService;
	
	@GetMapping("/")
	public String events() {
		return "events/index";
	}
	
	@GetMapping("/events/new")
	public String newEvent(Model model) {
		model.addAttribute("event", new NewEventDto());
		return "events/new";
	}
	
	@PatchMapping("/events/create")
	public String createNewEvent(@ModelAttribute("event") @Valid NewEventDto dto, BindingResult bindingResult) throws GoogleCalendarOAuthRedirectException, GeneralSecurityException {
		if (bindingResult.hasErrors()) {
			return "events/new";
		}
		googleCalendarService.createEvent(dto.getTitle(), dto.getStart(), dto.getEnd());
		
		System.out.println("title --> " + dto.getTitle());
		System.out.println("start --> " + dto.getStart());
		return "redirect:/";
	}
}
