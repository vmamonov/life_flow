package dev.mamonov.lifeflow.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.Credential;
import dev.mamonov.lifeflow.modules.google.calendar.utils.GoogleCalendarApi;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {
	
	@Autowired
	private GoogleCalendarApi api;
	
	@GetMapping("/googlecalendar/success")
	public String oauthRedirectHandler(HttpServletRequest request) throws IOException, GeneralSecurityException {
		String code = request.getParameter("code");
		System.out.println("code --> " + code);
		System.out.println("url --> " + request.getRequestURL().toString());
		
		Credential credential = api.requestRefreshToken(code);
		System.out.println("access token --> " + credential.getAccessToken());
		System.out.println("refresh token --> " + credential.getRefreshToken());
		
		System.out.println("oauthRedirectHandler --> OK");
		return "redirect:/";
	}
}