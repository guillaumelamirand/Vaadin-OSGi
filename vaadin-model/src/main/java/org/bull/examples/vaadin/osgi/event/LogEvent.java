package org.bull.examples.vaadin.osgi.event;

public class LogEvent {

	private String message;

	public LogEvent(final String pMessage){
		this.setMessage(pMessage);
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
