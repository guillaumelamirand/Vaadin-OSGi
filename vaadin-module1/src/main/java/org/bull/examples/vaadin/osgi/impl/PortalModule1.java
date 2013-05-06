package org.bull.examples.vaadin.osgi.impl;

import java.util.Locale;

import org.bull.examples.vaadin.osgi.event.LocaleChangedEvent;
import org.bull.examples.vaadin.osgi.event.LogEvent;
import org.bull.examples.vaadin.osgi.model.PortalModule;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PortalModule1 implements PortalModule
{

	@Override
	public String getId()
	{
		return "module1";
	}

	@Override
	public String getName()
	{
		return "Module 1";
	}

	@Override
	public Component createComponent(final EventBus pEventBus)
	{
		return new ModuleComponent(pEventBus);
	}

	public static class ModuleComponent extends CustomComponent
	{

		private final VerticalLayout vertical;
		private final Label					title;

		public ModuleComponent(final EventBus pEventBus)
		{
			pEventBus.register(this);
			vertical = new VerticalLayout();
			title = new Label();
			vertical.addComponent(title);
			setCompositionRoot(vertical);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void attach()
		{
			super.attach();
			Locale locale = getLocale();
			System.out.println("Module1 : Attach - Locale : " + locale);
			updateTxt(locale);
		}

		@Subscribe
		public void onLogEvent(final LogEvent pLogEvent)
		{
			vertical.addComponent(new Label(pLogEvent.getMessage()));
		}

		@Subscribe
		public void onLocaleChangedEvent(final LocaleChangedEvent pLocaleChangedEvent)
		{
			Locale locale = pLocaleChangedEvent.getLocale();
			System.out.println("Module1 : onLocaleChangeEvent - Locale : " + locale);
			updateTxt(locale);
		}

		private void updateTxt(Locale locale)
		{
			if (Locale.FRANCE.getDisplayLanguage().equals(locale.getDisplayLanguage()))
			{
				title.setCaption("Bonjour, c'est le module 1, et je vais ecouter toutes les messages.");
			}
			else if (Locale.US.getDisplayLanguage().equals(locale.getDisplayLanguage()))
			{
				title.setCaption("Hello, this is Module 1, and it will display log message.");
			}
			else
			{
				title.setCaption("D: Hello, this is Module 1.");
			}
		}
	}
}