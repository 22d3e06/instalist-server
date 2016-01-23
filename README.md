# Einkaufsliste Server

Die Serverseite für das Plugin [InstalistSync](https://bitbucket.org/fhnoorg/instalistsynch). Dient vor allem zur 
Synchronisation zwischen mehreren Geräten. 

## Setup
Benötigt:
 
 - Maven 3
 - JRE/JDK 8
 - Applikationsserver. Empfohlen: [Apache Tomcat 8](http://tomcat.apache.org/download-80.cgi)
 - Internetverbindung für den ersten Build (zum Abhängigkeiten herunterladen)

Dies ist ein Maven-Projekt. Mit

    mvn compile
    mvn package
    
lässt es sich kompilieren bzw. in ein war-File verpacken.

Dieses Projekt enthält außerdem ein Projekt für IntelliJ IDEA, der Applikations-Server muss jedoch selbst eingebunden werden.


## Altes Setup
1. Symfony2 installieren
2. Repository in src-Ordner pullen:

   - git init im src Ordner
   - git add remote origin https://<your-nickname>@bitbucket.org/fhnoorg/einkaufsliste-server.git
   - git pull master

3. Abhängigkeiten von dem Bundle im Symfony project root installieren:

   - composer require gos/websocket-server

4. Konfiguration vornehmen, damit Symfony das Bundle nutzt:

        // app/AppKernel.php
        class AppKernel extends Kernel
        {
            public function registerBundles()
            {
                $bundles = array(
                    // [...]
                    new InstalistBundle\InstalistBundle(),
                    new Gos\Bundle\WebSocketBundle\GosWebSocketBundle(),
                    new Gos\Bundle\PubSubRouterBundle\GosPubSubRouterBundle(),
                );
                // [...]
            }
            // [...]
        }

        # app/config/config.yml
        imports:
            - { resource: parameters.yml }
            - { resource: security.yml }
            - { resource: services.yml }
            - { resource: "@InstalistBundle/Resources/config/services.yml" }
        # [...]
        gos_web_socket:
            server:
                port: 1337       #The port the socket server will listen on
                host: 127.0.0.1
                router:
                    resources:
                        - @InstalistBundle/Resources/config/pubsub/routing.yml

5. Verlinken der Resourcen:

        php app/console assets:install web --symlink

6. Starten des WebSocket-Servers:

        php app/console gos:websocket:server

## Alte Tests
Sind ebenfalls verfügbar. Nach der Installation des Bundles einfach `/bundles/instalist/tests/test.html` im Browser aufrufen (der Websocket-Server muss dafür natürlich laufen.