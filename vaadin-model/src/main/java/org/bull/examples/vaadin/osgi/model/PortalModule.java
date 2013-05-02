package org.bull.examples.vaadin.osgi.model;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.Component;

/**
 * This interface describes a module which can be added to portal
 * 
 * @author Guillaume Lamirand
 * 
 */
public interface PortalModule {

	/**
	 * Return a technical id which has to be unique in module register
	 * 
	 * @return technical id
	 */
	String getId();

	/**
	 * Return a name used to define this module
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * Return a new {@link Component} used to displayed this module
	 * 
	 * @return a new {@link Component}
	 */
	Component createComponent(final EventBus pEventBus);

}
