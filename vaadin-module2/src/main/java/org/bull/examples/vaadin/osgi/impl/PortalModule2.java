package org.bull.examples.vaadin.osgi.impl;

import java.util.Locale;

import org.bull.examples.vaadin.osgi.event.LocaleChangedEvent;
import org.bull.examples.vaadin.osgi.event.LogEvent;
import org.bull.examples.vaadin.osgi.model.PortalModule;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PortalModule2 implements PortalModule
{

	@Override
	public String getId()
	{
		return "module2";
	}

	@Override
	public String getName()
	{
		return "Module 2";
	}

	@Override
	public Component createComponent(final EventBus pEventBus)
	{
		return new ModuleComponent(pEventBus);
	}

	public static class ModuleComponent extends CustomComponent
	{

		private final EventBus pEventBus;
		private final Label		title;
		private final Button	 button;

		public ModuleComponent(final EventBus pEventBus)
		{

			this.pEventBus = pEventBus;
			pEventBus.register(this);
			VerticalLayout vertical = new VerticalLayout();
			title = new Label();
			vertical.addComponent(title);
			final TextField textField = new TextField();
			button = new Button();
			button.addClickListener(new ClickListener()
			{

				@Override
				public void buttonClick(ClickEvent event)
				{
					pEventBus.post(new LogEvent(textField.getValue().toString()));

				}
			});
			vertical.addComponent(textField);
			vertical.addComponent(button);
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
			System.out.println("Module2 : Attach - Locale : " + locale);
			updateTxt(locale);
		}

		@Subscribe
		public void onLocaleChangedEvent(final LocaleChangedEvent pLocaleChangedEvent)
		{
			Locale locale = pLocaleChangedEvent.getLocale();
			System.out.println("Module2 : onLocaleChangeEvent - Locale : " + locale);
			updateTxt(locale);
		}

		private void updateTxt(Locale locale)
		{
			if (Locale.FRANCE.getDisplayLanguage().equals(locale.getDisplayLanguage()))
			{
				title.setCaption("Bonjour, c'est le Module 2.");
				button.setCaption("Envoyer evenement");
			}
			else if (Locale.US.getDisplayLanguage().equals(locale.getDisplayLanguage()))
			{
				title.setCaption("Hello, this is Module 2.");
				button.setCaption("Send event");

			}
			else
			{
				title.setCaption("D: Hello, this is Module 2.");
				button.setCaption("D: Send event");

			}
		}
	}
}