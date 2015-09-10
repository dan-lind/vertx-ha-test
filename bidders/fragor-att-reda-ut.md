### Frågor till kund (som behöver renskrivas om vi skall skicka över dem!) 

### Affärskrav
* Gällande uppdateringar om omstarter, hur definieras "nämnvärt" i frågan om hur det kommer påverka leveransen av meddelanden?
* 

* Syfte med att spara visitkort i DB hos Notis? Finns inte uppgifterna redan i Cava?

* Är "garanterad leverans" av meddelanden ett affärskrav? Kräver isåfall återrapportering till Cava server i någon form. vert.x   kör "best effort delivery", dvs ej garanterad leverans. 
- OK om allt krashar. Vi ska inte lägga tid på att försöka få 100% upptid.

### Teknikkrav
* Skall auditloggarna skrivas till fil eller databas?
  -Nej, logga till fil. Inte till DB. 

* Mer exakt VAD skall loggas? Typ meddelande mottaget från Cava, meddelande skrivet till DB, osv osv.

* "Enkelt" att ändra meddelande text. Hur definerar vi enkelt? Att ändra i en configfil och release om två veckor, är det tillräckligt enkelt? 
* Är vi bundna att köra någon särskilt utvecklingsmiljö eller är vi fria att välja själva?
* Finns det några krav på att kunna mäta throughput i systemet? (Typ Metrix med dropwizard)

* VIKTIGASTE FRÅGAN IDAG: Är det OK att vi går vidare med vert.x  ?
* Databas, några specifika krav? DB2 har nämnts. 
 
* Interface, REST API mer generellt. HornetQ connector möjligtvis enklare iom att Cava ej behöver bry sig om ifall vi är uppe eller ej. 
* Fail-over, HA, i vilken utsträckning?


### Frågor till oss
* Ska vi välja en arkitektur med JMS?
    Om vi ska köra JMS på vårt sida (Notis) så behöver vi en message broker, vilket ökar komplexiteten.
    Ett bättre förslag är isåfall att köra med vert.x hornetq connector, detta skulle troligtvis underlätta avsevärt för Cava server. Mer läsning här: http://planet.jboss.org/post/hornetq_vert_x_integration_available
    HTTP/REST är också ett alternativ, men antagligen mer komplext för Cava. Här beror det lite hur löst kopplade BiD vill att systemetn skall vara.

* Väljer vi Docker som paketeringssystem?
  * DL - Ja!
* Vilken databas ska vi välja?
  * DL - MongoDB för PoC. Det finns också en JDBC-klient som är asynkron, 
       http://vertx.io/docs/vertx-jdbc-client/java/ (antagligen någon "magi" eftersom JDBC är blockerande by design).
* Kan vert.x/Docker på något sätt hjälpa oss att hantera "zero downtime deployments"? Är det enklaste att köra en proxy framför,  sätta upp ett nytt kluster och sedan styra om trafiken?
  * ME - Vi kan titta på hur HAProxy löser detta problem. Den styr om trafiken till de noder som garanterat är uppe. 

* Vilken paketstruktur i Java ska vi använda? (T.ex. com.omegapoint.bid?)
DL - Kanske även här är bäst att kolla med våra kollegor först
* Hur får vi tillgång till protofuf-specarna?
DL - Jag GISSAR att det inte finns några specar, tror nog att vi förväntas ta fram dem?

* Appilationen bör logga throughput mm. 
