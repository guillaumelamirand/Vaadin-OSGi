package org.bull.examples.vaadin.osgi.impl;

import org.bull.examples.vaadin.osgi.event.LogEvent;
import org.bull.examples.vaadin.osgi.model.PortalModule;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PortalModule1 implements PortalModule {

	@Override
	public String getId() {
		return "module1";
	}

	@Override
	public String getName() {
		return "Module 1";
	}


	@Override
	public Component createComponent(final EventBus pEventBus) {
		return new ModuleComponent(pEventBus);
	}

	public static class ModuleComponent extends CustomComponent {

		private VerticalLayout vertical;

		public ModuleComponent(final EventBus pEventBus) {
			pEventBus.register(this);
			vertical = new VerticalLayout();
			vertical.addComponent(new Label("Hello, this is Module 1, and it will display log message."));
			setCompositionRoot(vertical);
		}
		@Subscribe
		public void onLogEvent(final LogEvent pLogEvent){
			vertical.addComponent(new Label(pLogEvent.getMessage()));
		}
	}
}