Nytt projekt, WOHO!

Bygg till docker:

I labb/
mvn clean package
cd notis-final
mvn docker:build

Kör sedan med 
docker run -t -i -p 8080:8080 omegapoint/notis