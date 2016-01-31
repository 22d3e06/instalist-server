# Einkaufsliste Server

Die Serverseite für das Plugin [InstalistSync](https://bitbucket.org/fhnoorg/instalistsynch). Dient vor allem zur 
Synchronisation zwischen mehreren Geräten. 

## Zustand
[![Build Status](http://instalist.noorganization.org/jenkins/buildStatus/icon?job=Einkaufsliste-Server)](http://instalist.noorganization.org/jenkins/job/Einkaufsliste-Server/)
Zumindest gilt das für den Build und die Tests.

## Setup
Benötigt:
 
 - Maven 3
 - MySQL 5.6/MariaDB 10.0 oder neuer.
 - JRE/JDK 8
 - Applikationsserver. Empfohlen: [Apache Tomcat 8](http://tomcat.apache.org/download-80.cgi)
 - Internetverbindung für den ersten Build (zum Abhängigkeiten herunterladen)

Dies ist ein Maven-Projekt. Mit

    mvn compile
    mvn package
    
lässt es sich kompilieren bzw. in ein war-File verpacken.

Vor dem deployen muss man die Datenbank mit den Zugangsdaten aus `src/main/resources/database/config.json` einrichten und dem Nutzer alle Rechte auf die Datenbank geben.

Dieses Projekt enthält außerdem ein Projekt für IntelliJ IDEA, der Applikations-Server muss jedoch selbst eingebunden werden.