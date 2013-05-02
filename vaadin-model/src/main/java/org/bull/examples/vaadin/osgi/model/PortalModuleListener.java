package org.bull.examples.vaadin.osgi.model;

public interface PortalModuleListener {

	public void moduleRegistered(final PortalModule module);
	
	public void moduleUnregistered(final PortalModule module);

}
