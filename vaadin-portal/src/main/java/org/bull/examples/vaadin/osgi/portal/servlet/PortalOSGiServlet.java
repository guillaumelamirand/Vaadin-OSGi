/**
 * NovaForge(TM) is a web-based forge offering a Collaborative Development and 
 * Project Management Environment.
 *
 * Copyright (C) 2007-2012  BULL SAS
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package org.bull.examples.vaadin.osgi.portal.servlet;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.bull.examples.vaadin.osgi.model.PortalModuleService;
import org.bull.examples.vaadin.osgi.portal.PortalOSGiApplication;
import org.osgi.framework.BundleContext;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

/**
 * This servlet is defined in order to get {@link BundleContext} from OSGi
 * container.
 * <p>
 * This {@link BundleContext} will be used to contact forge service needed bu
 * our portal.
 * 
 * @author Guillaume Lamirand
 */
public class PortalOSGiServlet extends AbstractApplicationServlet {

	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = -1438295278785392219L;

	/**
	 * {@inheritDoc}
	 * 
	 * @see PortalOSGiApplication
	 */
	@Override
	protected Class<? extends Application> getApplicationClass() {
		return PortalOSGiApplication.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see PortalOSGiApplication#PortalOSGiApplication(BundleContext)
	 */
	@Override
	protected Application getNewApplication(final HttpServletRequest pRequest)
			throws ServletException {
		return new PortalOSGiApplication(getPortalModuleService());
	}
	/**
	 * This method allows to obtain a {@link PortalModuleService} service
	 * implementation available in the OSGi context.
	 * 
	 * @return implementation of the {@link PortalModuleService} service found
	 */
	public static PortalModuleService getPortalModuleService() {
		return getService(PortalModuleService.class);
	}

	/**
	 * This method allows to obtain a service implementation available in the
	 * OSGi context.
	 * 
	 * @param pClassService
	 *            represents the instance of the service you are looking for @
	 * @param <T>
	 *            represents the class of the service
	 * @return implementation of the service
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(final Class<T> pClassService) {
		String canonicalName = pClassService.getCanonicalName();
		T service = null;
		try {
			InitialContext initialContext = new InitialContext();
			service = (T) initialContext.lookup(String.format(
					"osgi:service/%s", canonicalName));

		} catch (NamingException e) {
			throw new IllegalArgumentException(String.format(
					"Unable to get OSGi service with [interface=%s]",
					canonicalName));
		}
		return service;
	}

}
