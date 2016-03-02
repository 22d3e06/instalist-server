# InstaList Server
This is the server-side for the plugin [Instalist Synch](https://github.com/InstaList/instalist-synch).
It holds a central database and allows clients organized in groups to access data.
 
## State
This project was migrated shortly from BitBucket, since open source projects are more easily to 
maintain on GitHub. Here and there may be a reference to BitBucket. Please have patience until the
migration gets completed.

Build and tests: 
[![Build Status](http://instalist.noorganization.org/jenkins/buildStatus/icon?job=Einkaufsliste-Server)](http://instalist.noorganization.org/jenkins/job/Einkaufsliste-Server/)

## Structure
The project is split into multiple modules (also visible in IntelliJ):
 
 - `instalist-server`: Meta-project containing all server side projects
 - `instalist-server-webservice`: The real server side project
 - `instalist-comm`: Classes for standardizing the communication with clients

## Setup
System requirements:
 
 - Maven 3
 - MySQL 5.6/MariaDB 10.0 or newer
 - JRE/JDK 8
 - an application server. We recommend [Apache Tomcat 8](http://tomcat.apache.org/download-80.cgi)

For building a connection to internet may be required for downloading dependencies. To start, clone
the project and submodules and start building with maven as usual.

    git clone https://github.com/InstaList/instalist-server.git
    cd instalist-server
    git submodule update --init
    mvn compile
    mvn package

For testing no database connection is needed since an in-memory database is used. For normal 
deployment the database must be prepared with connection parameters from 
`instalist-server-webservice/src/main/resources/META-INF/persistence.xml`. The tables can be created
using the SQL-script in `doc/database-model.sql`.