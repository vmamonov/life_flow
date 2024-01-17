package dev.mamonov.lifeflow.modules.google.calendar.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import dev.mamonov.lifeflow.modules.google.calendar.exceptions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GoogleCalendarApi {
	private static final String APPLICATION_NAME = "Google Calendar API";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	
	/**
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * @throws GoogleCalendarOAuthRedirectException
	 */
	private Calendar createCalendar() throws GeneralSecurityException, GoogleCalendarOAuthRedirectException {		
		Calendar calendar = null;
		try {
			calendar = new Calendar.Builder(getHttpTransport(), JSON_FACTORY, getCredentials())
				.setApplicationName(APPLICATION_NAME).build();
		} catch (IOException e) {
			throw new GoogleCalendarOAuthRedirectException().setRedirectUrl(buildOAuthUrl());
		}
		return calendar;
	}

	
	public Calendar getCalendar() throws IOException, GeneralSecurityException, GoogleCalendarOAuthRedirectException {
		Calendar calendar = null;
		if (calendar == null)
			calendar = this.createCalendar();
		return calendar;
	}

	
	public Credential requestRefreshToken(String code) throws IOException, GeneralSecurityException {
		TokenResponse response = getFlow()
				.newTokenRequest(code)
				.setRedirectUri("http://localhost:"+ 8080 +"/oauth2/googlecalendar/success")
				.execute();
		System.out.println("getRefreshToken -->  " + response.getRefreshToken());
		return getFlow().createAndStoreCredential(response, "userId");
	}
	
	
	public String buildOAuthUrl()  {
		String url = "";
		try {
			InputStream in = GoogleCalendarApi.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
			if (in == null) {
				throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
			}
			/** @todo replace the hardcode of port **/
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
			String redirectUri = "http://localhost:" + 8080 + "/oauth2/googlecalendar/success";
			url = new GoogleAuthorizationCodeRequestUrl(clientSecrets, redirectUri, SCOPES).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

	
	private Credential getCredentials() throws IOException, GeneralSecurityException {
		/** @todo  **/
		return getFlow().loadCredential("userId");
	}


	private NetHttpTransport getHttpTransport() throws GeneralSecurityException, IOException {
		NetHttpTransport httpTransport = null;
		if (httpTransport == null) {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		}
		return httpTransport;
	}


	private GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		GoogleAuthorizationCodeFlow flow = null;
		if (flow == null) {
			flow = new GoogleAuthorizationCodeFlow.Builder(getHttpTransport(), JSON_FACTORY, getClientSecret(), SCOPES)
			/*.setCredentialDataStore(new LocalFileStorage())*/
			.setDataStoreFactory(
				new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
			.setAccessType("offline")
			.setRefreshListeners(Arrays.asList(new CredentialRefreshListener() {
				@Override
				public void onTokenResponse(Credential credential, TokenResponse tokenResponse) throws IOException {
					System.out.println("onTokenResponse getAccessToken--> " + tokenResponse.getAccessToken());
					System.out.println("onTokenResponse getAccessToken --> " + tokenResponse.getRefreshToken());
				}
				
				@Override
				public void onTokenErrorResponse(Credential credential, TokenErrorResponse tokenErrorResponse) throws IOException {
					System.out.println("onTokenErrorResponse getErrorUri --> " + tokenErrorResponse.getErrorUri());
					System.out.println("onTokenErrorResponse getErrorDescription --> " + tokenErrorResponse.getErrorDescription());
					System.out.println("onTokenErrorResponse getError --> " + tokenErrorResponse.getError());
				}
			}))
			.build();
		}
		return flow;
	}


	private GoogleClientSecrets getClientSecret() throws IOException {
		GoogleClientSecrets clientSecret = null;
		InputStream in = GoogleCalendarApi.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (clientSecret == null) {
			clientSecret = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		}
		return clientSecret;
	}
}