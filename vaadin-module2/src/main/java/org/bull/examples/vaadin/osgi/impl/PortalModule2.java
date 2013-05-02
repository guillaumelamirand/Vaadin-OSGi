package org.bull.examples.vaadin.osgi.impl;

import org.bull.examples.vaadin.osgi.event.LogEvent;
import org.bull.examples.vaadin.osgi.model.PortalModule;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class PortalModule2 implements PortalModule {

	@Override
	public String getId() {
		return "module2";
	}

	@Override
	public String getName() {
		return "Module 2";
	}


	@Override
	public Component createComponent(final EventBus pEventBus) {
		return new ModuleComponent(pEventBus);
	}

	public static class ModuleComponent extends CustomComponent {

		private EventBus pEventBus;

		public ModuleComponent(final EventBus pEventBus) {
			
			this.pEventBus = pEventBus;
			VerticalLayout vertical = new VerticalLayout();
			vertical.addComponent(new Label("Hello, this is Module 2."));
			final TextField textField = new TextField();
			Button button = new Button("Send event");
			button.addListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					pEventBus.post(new LogEvent(textField.getValue().toString()));
					
				}
			});
			vertical.addComponent(textField);
			vertical.addComponent(button);
			setCompositionRoot(vertical);
		}
	}
}