package dev.mamonov.lifeflow.modules.google.calendar.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.Calendar;
import dev.mamonov.lifeflow.modules.google.calendar.exceptions.GoogleCalendarOAuthRedirectException;
import dev.mamonov.lifeflow.modules.google.calendar.utils.GoogleCalendarApi;

@Service
public class GoogleCalendarService {
	@Autowired
	private GoogleCalendarApi api;
	private Calendar calendar;
	
	
	public Event createEvent(String titel, LocalDateTime startDateTime, LocalDateTime endDateTime) throws GoogleCalendarOAuthRedirectException, GeneralSecurityException {
		Event newEvent = null;
		DateTime start = new DateTime(Timestamp.valueOf(startDateTime));
		DateTime end = new DateTime(Timestamp.valueOf(endDateTime));
		try {
			calendar = api.getCalendar();
			newEvent = calendar.events().insert(
				"primary", 
				new Event().setSummary(titel)
					.setStart(new EventDateTime().setDateTime(start))
					.setEnd(new EventDateTime().setDateTime(end)))
				.execute();
		} catch (IOException e) {
			e.printStackTrace();
			throw new GoogleCalendarOAuthRedirectException().setRedirectUrl(api.buildOAuthUrl());
		}
		return newEvent;
	}
	
	
	public List<Event> getTodaysEvents() throws GoogleCalendarOAuthRedirectException, GeneralSecurityException {
		LocalDate now = LocalDate.now();
		LocalDateTime startDay = now.atStartOfDay();
		LocalDateTime endDay = now.atTime(23, 59);
		return getEvents(startDay, endDay);
	}
	
	
	/** @Link https://developers.google.com/calendar/api/v3/reference/events/insert?hl=ru **/
	public List<Event> getEvents(LocalDateTime startDateTimeParam, LocalDateTime endDateTimeParam) throws GeneralSecurityException, GoogleCalendarOAuthRedirectException {
	    DateTime startDateTime = new DateTime(Timestamp.valueOf(startDateTimeParam));
	    DateTime endDateTime = new DateTime(Timestamp.valueOf(endDateTimeParam));
	    Events events = null;
		try {
			calendar = api.getCalendar();
			events = calendar.events().list("primary")
			    //.setMaxResults(10)
			    .setTimeMin(startDateTime)
			    .setTimeMax(endDateTime)
			    .setOrderBy("startTime")
			    .setSingleEvents(true)
			    .execute();
		} catch (IOException e) {
			e.printStackTrace();
			throw new GoogleCalendarOAuthRedirectException().setRedirectUrl(api.buildOAuthUrl());
		}
	    return events.getItems();
	}
}
