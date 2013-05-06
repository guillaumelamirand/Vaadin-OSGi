package org.bull.examples.vaadin.osgi.event;

import java.util.Locale;

public class LocaleChangedEvent
{

	private final Locale locale;

	public LocaleChangedEvent(final Locale pLocale)
	{
		locale = pLocale;

	}

	/**
	 * @return the locale
	 */
	public Locale getLocale()
	{
		return locale;
	}
}
