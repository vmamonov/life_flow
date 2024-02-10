package dev.mamonov.lifeflow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mamonov.lifeflow.dto.NewUserDto;
import dev.mamonov.lifeflow.models.User;
import dev.mamonov.lifeflow.services.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/registration")
	public String newUser(Model model) {
		model.addAttribute("newUser", new User());
		return "users/new-user";
	}
	
	@PatchMapping("/createuser")
	public String createUser(@ModelAttribute NewUserDto newUserDto) {
		authService.register(newUserDto);		
		return "redirect:/";
	}
}