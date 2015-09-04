### Frågor till oss
* Ska vi välja en arkitektur med JMS?
    Om vi ska köra JMS på vårt sida (Notis) så behöver vi en message broker, vilket ökar komplexiteten.
    Ett bättre förslag är isåfall att köra med vert.x hornetq connector, detta skulle troligtvis underlätta avsevärt för Cava server. Mer läsning här: http://planet.jboss.org/post/hornetq_vert_x_integration_available
    HTTP/REST är också ett alternativ, men antagligen mer komplext för Cava. Här beror det lite hur löst kopplade BiD vill att systemetn skall vara.

* Väljer vi Docker som paketeringssystem?
  DL - Ja!
* Vilken databas ska vi välja?
  DL - MongoDB för PoC. Det finns också en JDBC-klient som är asynkron, 
       http://vertx.io/docs/vertx-jdbc-client/java/ (antagligen någon "magi" eftersom JDBC är blockerande by design).
* Kan vert.x/Docker på något sätt hjälpa oss att hantera "zero downtime deployments"? Är det enklaste att köra en proxy framför,  sätta upp ett nytt kluster och sedan styra om trafiken?


### Frågor till kund (som behöver renskrivas om vi skall skicka över dem!) 
* Hur får vi tillgång till protofuf-specarna?
* Är vi bundna att köra någon särskilt utvecklingsmiljö eller är vi fria att välja själva?
* Gällande uppdateringar om omstarter, hur definieras "nämnvärt" i frågan om hur det kommer påverka leveransen av meddelanden?
* Vilken paketstruktur i Java ska vi använda? (T.ex. com.omegapoint.bid?)
* Är "garanterad leverans" av meddelanden ett affärskrav? Kräver isåfall återrapportering till Cava server i någon form. vert.x   kör "best effort delivery", dvs ej garanterad leverans. 
* Finns det några krav på att kunna mäta throughput i systemet? (Typ Metrix med dropwizard)
* Skall auditloggarna skrivas till fil eller databas?
