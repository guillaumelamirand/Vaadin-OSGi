Vaadin-OSGi
===========

This repository contains modules around Vaadin and OSGi 


Build
===========

mvn clean install

Install && run :

 - wget http://www.apache.org/dyn/closer.cgi/karaf/2.3.1/apache-karaf-2.3.1.tar.gz
 - tar xvf apache-karaf-2.3.1.tar.gz
 - cd apache-karaf-2.3.1
 - ./bin/karaf
 - features:addurl mvn:org.bull.examples.vaadin-osgi/vaadin-features/1.0.0-SNAPSHOT/xml
 - features:install vaadin-osgi

