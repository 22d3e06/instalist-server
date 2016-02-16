# Einkaufsliste Server
Die Serverseite für das Plugin [InstalistSync](https://bitbucket.org/fhnoorg/instalistsynch). Dient 
vor allem zur Synchronisation zwischen mehreren Geräten. 

## Zustand
[![Build Status](http://instalist.noorganization.org/jenkins/buildStatus/icon?job=Einkaufsliste-Server)](http://instalist.noorganization.org/jenkins/job/Einkaufsliste-Server/)
Zumindest gilt das für den Build und die Tests.

## Struktur
Das Projekt ist in mehrere Module unterteilt (ebenfalls in IntelliJ sichtbar):
 
 - `instalist-server-meta`: Das Projekt in dem alle serverseitigen Module enthalten sind.
 - `instalist-server-webservice`: Die Serverseite selbst.
 - `instalist-comm`: Klassen zur standardisierten Kommunikation mit dem Client. Dieses Projekt 
 wird ebenfalls im Client benutzt.

## Setup
Benötigt:
 
 - Maven 3
 - MySQL 5.6/MariaDB 10.0 oder neuer.
 - JRE/JDK 8
 - Applikationsserver. Empfohlen: [Apache Tomcat 8](http://tomcat.apache.org/download-80.cgi)
 - Internetverbindung für den ersten Build (zum Abhängigkeiten herunterladen)

Dies ist ein Maven-Projekt. Mit

    git clone ...
    git submodule update --init
    mvn compile
    mvn package
    
lässt es sich klonen, kompilieren bzw. in ein war-File verpacken.

Zum Testen wird keine externe Datenbank-Verbindung benötigt. Für den Einsatz nach dem deployen 
auf dem Applikationsserver wird die Datenbank benötigt und muss erstellt, sowie die Tabellen 
angelegt sein. Verwendete Zugangsdaten sind in 
`instalist-server-webservice/src/main/resources/META-INF/persistence.xml` hinterlegt. Die 
Datenbank-Tabellen können mit dem SQL-Skript in `doc/database-model.sql` initialisiert werden.

Dieses Projekt enthält außerdem ein Projekt für IntelliJ IDEA, der Applikations-Server muss jedoch 
selbst eingebunden werden.