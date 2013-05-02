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
package org.bull.examples.vaadin.osgi.portal;

import java.util.Iterator;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.bull.examples.vaadin.osgi.model.PortalModule;
import org.bull.examples.vaadin.osgi.model.PortalModuleListener;
import org.bull.examples.vaadin.osgi.model.PortalModuleService;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;

public class PortalOSGiApplication extends UI implements
		PortalModuleListener {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = -2881139985491701737L;

	private TabSheet tabs;

	private Tree tree;
	
	private EventBus eventBus = new EventBus();

	@Override
	public void init(VaadinRequest request) {
		HorizontalSplitPanel split = new HorizontalSplitPanel();
		split.setSizeFull();
		split.setSplitPosition(250, Unit.PIXELS);
		tree = new Tree();
		for (PortalModule module : getPortalModuleService().getModules()) {
			tree.addItem(module.getId());
			tree.setItemCaption(module.getId(), module.getName());
		}
		tree.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				PortalModule module = getPortalModuleService()
						.getModule((String) event.getItemId());
				Iterator<Component> it = tabs.iterator();
				Component found = null;
				while (it.hasNext()) {
					Component c = it.next();
					if (tabs.getTab(c).getCaption().equals(module.getName())) {
						found = c;
						break;
					}
				}
				if (found == null) {
					Tab addTab = tabs.addTab(module.createComponent(eventBus),
							module.getName());
					addTab.setClosable(true);
				} else {
					tabs.setSelectedTab(found);
				}
			}
		});
		tabs = new TabSheet();
		tabs.setSizeFull();
		split.setFirstComponent(tree);
		split.setSecondComponent(tabs);
		setContent(split);
		getPortalModuleService().addListener(this);
	}

	@Override
	public void close() {
		getPortalModuleService().removeListener(this);
		super.close();
	}

	@Override
	public void moduleRegistered(PortalModule module) {
		tree.addItem(module.getId());
		tree.setItemCaption(module.getId(), module.getName());
	}

	@Override
	public void moduleUnregistered(PortalModule module) {
		tree.removeItem(module.getId());
		Iterator<Component> it = tabs.iterator();
		while (it.hasNext()) {
			Component c = it.next();
			if (tabs.getTab(c).getCaption().equals(module.getName())) {
				tabs.removeComponent(c);
				return;
			}
		}
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