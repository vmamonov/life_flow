package dev.mamonov.lifeflow.modules.google.calendar.exceptions;

import org.apache.http.client.RedirectException;

public class GoogleCalendarOAuthRedirectException extends RedirectException {
	private String redirectUrl;
	private static final long serialVersionUID = 1L;
	
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public GoogleCalendarOAuthRedirectException setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}
}
