 
# Einkaufsliste Server

## Setup
1. Symfony2 installieren
2. Repository in src-Ordner pullen
3. Abhängigkeiten von dem Bundle Installieren:
   - cd src/InstalistBundle
   - composer install
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
                    
5. Starten des WebSocket-Servers:

    php app/console gos:websocket:server